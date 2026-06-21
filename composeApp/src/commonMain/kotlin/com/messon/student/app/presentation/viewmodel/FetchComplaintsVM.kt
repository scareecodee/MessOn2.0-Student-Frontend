package com.messon.student.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.student.app.core.Paginator
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.dataModels.ComplaintVoteReq
import com.messon.student.app.domain.dataModels.FetchComplaintsRes
import com.messon.student.app.domain.repository.MessRepository
import com.messon.student.app.presentation.ui.state.FetchComplaintState
import com.messon.student.app.utils.NetworkError
import com.messon.student.app.utils.Result
import com.messon.student.app.utils.UserInteraction
import com.messon.student.app.utils.onError
import com.messon.student.app.utils.onSuccess
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class FetchComplaintsVM(
    private val messRepository: MessRepository,
    private val secureStorage: AuthSecureStorage,
) : ViewModel() {

    private val _messId = MutableStateFlow<String?>(null)
    private val _search = MutableStateFlow<String?>(null)
    private val _batch = MutableStateFlow<Int?>(null)

    init {
        viewModelScope.launch {
            combine(_search, _batch) { search, batch ->
                search to batch
            }
                .drop(1)
                .debounce(500)
                .distinctUntilChanged()
                .collect {
                    refreshSilently()
                }
        }
    }

    fun setSearch(search: String?) {
        _search.value = search
    }

    fun setMessId(id: String) {
        _messId.value = id
    }

    private val _state = MutableStateFlow(FetchComplaintState())
    val state = _state.asStateFlow()

    // FetchComplaintsVM.kt - Update onSuccess
    private val paginator = Paginator<String?, FetchComplaintsRes, NetworkError>(
        initialKey = null,

        onLoadUpdated = { isLoading ->
            _state.update {
                it.copy(
                    isLoadingMore = if (!it.isRefreshing) isLoading else false
                )
            }
        },

        onRequest = { cursor ->
            val token = secureStorage.getAccessToken()
            if (token.isNullOrBlank()) {
                return@Paginator Result.Error(NetworkError.UNAUTHORIZED)
            }
            messRepository.fetchComplaints(
                token = token,
                cursor = cursor,
                search = _search.value
            )
        },

        getNextKey = { _, response ->
            response.nextCursor
        },

        onError = { error ->
            _state.update {
                it.copy(
                    error = error.toString(),
                    isRefreshing = false,
                    endReached = true
                )
            }
        },

        onSuccess = { response, _ ->
            println("✅ Success: ${response.complaints.size} complaints loaded")
            // IMPORTANT: Append new complaints to existing ones
            _state.update { currentState ->
                val existingComplaints = currentState.complaints
                val newComplaints = response.complaints

                // Remove duplicates based on ID
                val existingIds = existingComplaints.map { it.id }.toSet()
                val uniqueNewComplaints = newComplaints.filter { it.id !in existingIds }

                println("📊 Existing: ${existingComplaints.size}, New: ${newComplaints.size}, Unique: ${uniqueNewComplaints.size}")

                currentState.copy(
                    complaints = existingComplaints + uniqueNewComplaints,
                    error = null,
                    endReached = response.nextCursor == null,
                    isRefreshing = false
                )
            }
        },

        endReached = { _, response ->
            response.nextCursor == null
        }
    )

    fun loadFirstPage() {
        _state.update {
            it.copy(
                error = null,
                endReached = false,
                votingInProgress = emptySet()
            )
        }

        paginator.reset()

        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun loadNextItems() {
        if (state.value.endReached) return

        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun manualRefresh() {
        paginator.reset()

        _state.update {
            it.copy(
                error = null,
                endReached = false,
                isRefreshing = true,
                votingInProgress = emptySet()
            )
        }

        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun refreshSilently() {
        paginator.reset()

        _state.update {
            it.copy(
                error = null,
                endReached = false,
                isRefreshing = false,
                votingInProgress = emptySet()
            )
        }

        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun voteComplaint(complaintId: String, voteType: String) {
        viewModelScope.launch {
            val token = secureStorage.getAccessToken()
            if (token.isNullOrBlank()) {
                _state.update {
                    it.copy(error = "Session expired. Please login again.")
                }
                return@launch
            }

            val currentComplaint = _state.value.complaints.find { it.id == complaintId }
            if (currentComplaint == null) {
                _state.update {
                    it.copy(error = "Complaint not found")
                }
                return@launch
            }

            _state.update {
                it.copy(votingInProgress = it.votingInProgress + complaintId)
            }

            val (newInteraction, likeDelta, dislikeDelta) = calculateVoteUpdate(
                currentUserInteraction = currentComplaint.userInteraction,
                voteType = voteType
            )

            updateComplaintVote(complaintId, newInteraction, likeDelta, dislikeDelta)

            messRepository.voteComplaint(
                token = token,
                complaintVoteReq = ComplaintVoteReq(
                    complaintId = complaintId,
                    voteType = voteType
                )
            ).onSuccess { response ->
                _state.update { currentState ->
                    val updatedComplaints = currentState.complaints.map { complaint ->
                        if (complaint.id == complaintId) {
                            complaint.copy(
                                likes = response.likeCount.toIntOrNull() ?: complaint.likes,
                                dislikes = response.dislikeCount.toIntOrNull() ?: complaint.dislikes,
                                userInteraction = newInteraction
                            )
                        } else {
                            complaint
                        }
                    }
                    currentState.copy(
                        complaints = updatedComplaints,
                        error = null,
                        votingInProgress = currentState.votingInProgress - complaintId
                    )
                }
            }.onError { error ->
                _state.update { currentState ->
                    val revertedComplaints = currentState.complaints.map { complaint ->
                        if (complaint.id == complaintId) {
                            currentComplaint
                        } else {
                            complaint
                        }
                    }
                    currentState.copy(
                        complaints = revertedComplaints,
                        error = "Failed to vote: ${error.toString()}",
                        votingInProgress = currentState.votingInProgress - complaintId
                    )
                }
            }
        }
    }

    private fun calculateVoteUpdate(
        currentUserInteraction: UserInteraction?,
        voteType: String
    ): Triple<UserInteraction?, Int, Int> {
        val isLike = voteType.equals("like", ignoreCase = true)
        val currentInteraction = currentUserInteraction

        return when {
            (isLike && currentInteraction == UserInteraction.LIKE) ->
                Triple(null, -1, 0)
            (!isLike && currentInteraction == UserInteraction.DISLIKE) ->
                Triple(null, 0, -1)
            (isLike && currentInteraction == UserInteraction.DISLIKE) ->
                Triple(UserInteraction.LIKE, 1, -1)
            (!isLike && currentInteraction == UserInteraction.LIKE) ->
                Triple(UserInteraction.DISLIKE, -1, 1)
            (isLike && currentInteraction == null) ->
                Triple(UserInteraction.LIKE, 1, 0)
            (!isLike && currentInteraction == null) ->
                Triple(UserInteraction.DISLIKE, 0, 1)
            else -> Triple(null, 0, 0)
        }
    }

    private fun updateComplaintVote(
        complaintId: String,
        newInteraction: UserInteraction?,
        likeDelta: Int,
        dislikeDelta: Int
    ) {
        _state.update { currentState ->
            val updatedComplaints = currentState.complaints.map { complaint ->
                if (complaint.id == complaintId) {
                    complaint.copy(
                        likes = complaint.likes + likeDelta,
                        dislikes = complaint.dislikes + dislikeDelta,
                        userInteraction = newInteraction
                    )
                } else {
                    complaint
                }
            }
            currentState.copy(complaints = updatedComplaints)
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    fun getVoteState(complaintId: String): UserInteraction? {
        return _state.value.complaints.find { it.id == complaintId }?.userInteraction
    }
}