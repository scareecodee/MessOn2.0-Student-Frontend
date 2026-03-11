package com.messon.student.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.student.app.data.remote.dto.LoginRequestDto
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.auth.GoogleAuth
import com.messon.student.app.domain.dataModels.LoginRequest
import com.messon.student.app.domain.dataModels.User
import com.messon.student.app.domain.repository.AuthRepository
import com.messon.student.app.domain.repository.CollegeRepository
import com.messon.student.app.presentation.ui.state.AuthState
import com.messon.student.app.utils.UserRole
import com.messon.student.app.utils.onError
import com.messon.student.app.utils.onSuccess
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.cancel

class AuthViewModel(
    private val secureStorage: AuthSecureStorage,
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher,
    private val collegeRepository: CollegeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value =
                if (secureStorage.isSessionValid()) {
                    AuthState.Success(
                        user = User(
                            name = secureStorage.getName(),
                            mailId = secureStorage.getEmail() ?: "",
                            profileImg = secureStorage.getPicture() ?: "",
                            id = secureStorage.getId() ?: ""
                        )
                    )
                } else {
                    AuthState.Unauthenticated
                }
        }
    }

    fun onGoogleClick(googleAuth: GoogleAuth) {
        _uiState.value = AuthState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            googleAuth.getGoogleToken()
                .onSuccess { token ->
                    authRepository.login(loginRequest = LoginRequest(
                        idToken = token,
                        role = UserRole.STUDENT.value,
                        collegeId = collegeRepository.selectedCollege.value?.collegeId ?: ""
                    )
                    )
                        .onSuccess { loginResponse ->
                            secureStorage.saveLogin(response = loginResponse)
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

    fun logout(onDone: () -> Unit) {
        viewModelScope.launch {
            secureStorage.clear()
            _uiState.value = AuthState.Unauthenticated
            onDone()
        }
    }

    fun clear() {
        viewModelScope.cancel()
    }
}