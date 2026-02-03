package com.messon.app.presentation.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.app.data.remote.dto.LoginRequestDto
import com.messon.app.data.remote.dto.LoginResponseDto
import com.messon.app.data.remote.network.Auth
import com.messon.app.domain.auth.AuthSecureStorage
import com.messon.app.domain.auth.GoogleAuth
import com.messon.app.domain.dataModels.LoginResponse
import com.messon.app.domain.repository.AuthRepository
import com.messon.app.presentation.mapper.toDto
import com.messon.app.utils.AuthState
import com.messon.app.utils.onError
import com.messon.app.utils.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

import kotlinx.coroutines.*

class AuthViewModel(
    private val secureStorage: AuthSecureStorage,
    private val googleAuth: GoogleAuth,
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel()
{

    private val _uiState = mutableStateOf<AuthState>(AuthState.Idle)
    val uiState: State<AuthState> = _uiState

    fun onGoogleClick() {
        _uiState.value = AuthState.Loading

        viewModelScope.launch(dispatcher) {
            googleAuth.getGoogleToken()
                .onSuccess { token ->
                    authRepository.login(LoginRequestDto(idToken = token))
                        .onSuccess { loginResponse ->
                            secureStorage.saveLogin(
                                response = loginResponse
                            )
                            _uiState.value = AuthState.Success(loginResponse.user)
                        }
                        .onError {
                            _uiState.value = AuthState.Error(it.toString())
                        }
                }
                .onError {
                    _uiState.value = AuthState.Error(it.toString())
                }
        }
    }

    fun clear() {
        viewModelScope.cancel()
    }
}
