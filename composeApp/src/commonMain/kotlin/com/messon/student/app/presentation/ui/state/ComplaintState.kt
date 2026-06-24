package com.messon.student.app.presentation.ui.state

import com.messon.student.app.domain.dataModels.Complaint
import com.messon.student.app.domain.dataModels.ComplaintVoteRes


sealed class CreateComplaintState {
    object Idle : CreateComplaintState()
    object Loading : CreateComplaintState()
    data class Success(val resp: String) : CreateComplaintState()
    data class Error(val message: String) : CreateComplaintState()
}


data class FetchComplaintState(
    val complaints: List<Complaint> = emptyList(),
    val isLoadingMore: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false
)

sealed class VoteComplaintState{
    object Idle : VoteComplaintState()
    object Loading : VoteComplaintState()
    data class Success(val resp: ComplaintVoteRes) : VoteComplaintState()
    data class Error(val message: String) : VoteComplaintState()
}

