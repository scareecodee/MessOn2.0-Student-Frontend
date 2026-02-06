package com.messon.student.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.repository.MessMenuRepository
import com.messon.student.app.utils.MessMenuState
import com.messon.student.app.utils.onError
import com.messon.student.app.utils.onSuccess
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class MessMenuViewModel(
    private val secureStorage: AuthSecureStorage,
    private val messMenuRepository: MessMenuRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val log = Logger.withTag("CMP_LOG")

    private val _uiState = MutableSharedFlow<MessMenuState>(replay = 1)
    val uiState: SharedFlow<MessMenuState> = _uiState

    fun onMenuCardClick(day: String) {
        viewModelScope.launch(dispatcher) {

            // Emit loading state
            _uiState.emit(MessMenuState.Loading)

            val token = secureStorage.getAccessToken()
            log.d(tag = "token from disk") { token.toString() }

            if (token.isNullOrBlank()) {
                _uiState.emit(MessMenuState.Error("Session expired. Please login again."))
                return@launch
            }

            // Fetch menu from repository
            messMenuRepository.fetchMenuByDay(
                token = token,
                day = day
            )
                .onSuccess {
                    // Emit success every time, even if same menu
                    _uiState.emit(MessMenuState.Success(eachDayMenu = it))
                }
                .onError {
                    _uiState.emit(MessMenuState.Error(it.toString()))
                }
        }
    }

    // Optional: clear errors
    fun clearError() {
        viewModelScope.launch {
            _uiState.emit(MessMenuState.Idle)
        }
    }
}
