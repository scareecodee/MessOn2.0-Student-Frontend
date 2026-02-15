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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class MessMenuViewModel(
    private val secureStorage: AuthSecureStorage,
    private val messMenuRepository: MessMenuRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val log = Logger.withTag("CMP_LOG")

    private val _currentDayMenu=MutableStateFlow<MessMenuState>(MessMenuState.Idle)
    val currentDayMenu=_currentDayMenu
    private val _uiState = MutableStateFlow<MessMenuState>(MessMenuState.Idle)
    val uiState = _uiState

    fun onMenuCardClick(day: String,currentDay: Boolean=false) {
        if (_uiState.value == MessMenuState.Loading) return


        viewModelScope.launch(dispatcher) {
            _uiState.value = MessMenuState.Loading
            if(currentDay)
            {
                _currentDayMenu.value= MessMenuState.Loading
            }
            val token = secureStorage.getAccessToken()
            log.d { "token from disk: $token" }

            if (token.isNullOrBlank()) {
                _uiState.value = MessMenuState.Error("Session expired. Please login again.")
                return@launch
            }

            messMenuRepository.fetchMenuByDay(day, token)
                .onSuccess {
                    _uiState.value = MessMenuState.Success(it)
                    if(currentDay){
                        _currentDayMenu.value= MessMenuState.Success(it)
                    }
                }
                .onError {
                    _uiState.value = MessMenuState.Error(it.toString())
                    if(currentDay){
                        _currentDayMenu.value= MessMenuState.Error(it.toString())
                    }
                }
        }
    }

    fun clearMenuError() {
        _uiState.value = MessMenuState.Idle
    }

    fun clearCurrentDayMenuError() {
        _currentDayMenu.value = MessMenuState.Idle
    }
}
