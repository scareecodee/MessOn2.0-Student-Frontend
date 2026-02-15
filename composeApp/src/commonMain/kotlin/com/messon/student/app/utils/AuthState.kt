package com.messon.student.app.utils
import com.messon.student.app.data.remote.network.Auth
import com.messon.student.app.domain.dataModels.User


sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Unauthenticated: AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}
