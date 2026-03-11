package com.messon.student.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.repository.MessRepository
import com.messon.student.app.presentation.ui.state.FetchMenuState
import com.messon.student.app.utils.WeekType
import com.messon.student.app.utils.onError
import com.messon.student.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuFetchVM(
    private val  secureStorage: AuthSecureStorage,
    private val messRepository: MessRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow<FetchMenuState>(FetchMenuState.Idle)
    val uiState: StateFlow<FetchMenuState> =_uiState.asStateFlow()

    fun fetchMenu(
    ) {
        if(_uiState.value is FetchMenuState.Loading) return
        _uiState.value = FetchMenuState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value =
                    FetchMenuState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.fetchMessMenu(
                token = token,
                weekType = WeekType.CURRENT_WEEK.value,
            )
                .onSuccess {
                    _uiState.value = FetchMenuState.Success(it)
                }
                .onError {
                    _uiState.value = FetchMenuState.Error(it.toString())
                }
        }
    }

}