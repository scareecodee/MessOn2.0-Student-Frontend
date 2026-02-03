package com.messon.app.presentation.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.messon.app.domain.auth.AuthSecureStorage
import com.messon.app.domain.repository.CouponRepository
import com.messon.app.utils.CouponState
import com.messon.app.utils.onError
import com.messon.app.utils.onSuccess
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CouponViewModel(
    private val secureStorage: AuthSecureStorage,
    private val couponRepository: CouponRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val log = Logger.withTag("CMP_LOG")

    private val _uiState = MutableStateFlow<CouponState>(CouponState.Idle)
    val uiState: StateFlow<CouponState> = _uiState.asStateFlow()

    fun onCouponClick(mealSlot: String) {
        _uiState.value = CouponState.Loading

        viewModelScope.launch(dispatcher) {

            val token = secureStorage.getAccessToken()
            log.d(tag = "token from disk", message = { token.toString() })

            if (token.isNullOrBlank()) {
                _uiState.value =
                    CouponState.Error("Session expired. Please login again.")
                return@launch
            }

            couponRepository.fetchCoupon(token, mealSlot)
                .onSuccess {
                    _uiState.value = CouponState.Success(it)
                }
                .onError {
                    _uiState.value = CouponState.Error(it.toString())
                }
        }
    }
}
