package com.messon.student.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.dataModels.ComplaintVoteReq
import com.messon.student.app.domain.dataModels.CreateComplaintReq
import com.messon.student.app.domain.repository.MessRepository
import com.messon.student.app.presentation.ui.state.CreateComplaintState
import com.messon.student.app.presentation.ui.state.VoteComplaintState
import com.messon.student.app.utils.onError
import com.messon.student.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ComplaintVM(
    private val secureStorage: AuthSecureStorage,
    private val messRepository: MessRepository
): ViewModel(){

    private val _uiState = MutableStateFlow<CreateComplaintState>(CreateComplaintState.Idle)
    val uiState: StateFlow<CreateComplaintState> = _uiState.asStateFlow()
    fun createComplaint(
        subject: String,
        description: String
    ){
        if(_uiState.value is CreateComplaintState.Loading) return
        _uiState.value= CreateComplaintState.Loading
        viewModelScope.launch (Dispatchers.IO){
            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value =
                    CreateComplaintState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.createComplaint(
                token = token,
                createComplaint =CreateComplaintReq(
                    description = description,
                    title = subject
                )
            )
                .onSuccess {
                    _uiState.value= CreateComplaintState.Success(resp=it)
                }
                .onError {
                    _uiState.value= CreateComplaintState.Error(message = it.toString())
                }
        }
    }

    fun clearSaveState(){
        _uiState.value= CreateComplaintState.Idle
    }

    private val _voteState = MutableStateFlow<VoteComplaintState>(VoteComplaintState.Idle)
    val voteState: StateFlow<VoteComplaintState> = _voteState.asStateFlow()

    fun voteComplaint(
        complaintId: String,
        voteType: String
    ){
        if(_voteState.value is VoteComplaintState.Loading) return
        _voteState.value= VoteComplaintState.Loading
        viewModelScope.launch (Dispatchers.IO){
            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _voteState.value =
                    VoteComplaintState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.voteComplaint(
                token = token,
                complaintVoteReq = ComplaintVoteReq(
                    complaintId = complaintId,
                    voteType = voteType
                ),
            )
                .onSuccess {
                    _voteState.value= VoteComplaintState.Success(resp=it)
                }
                .onError {
                    _voteState.value= VoteComplaintState.Error(message = it.toString())
                }
        }
    }

}