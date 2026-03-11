package com.messon.student.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.repository.CouponRepository
import com.messon.student.app.presentation.ui.state.CouponState
import com.messon.student.app.utils.onError
import com.messon.student.app.utils.onSuccess
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.Instant
import kotlin.time.Clock
import kotlinx.datetime.toLocalDateTime


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

            couponRepository.fetchCoupon(
                token = token, mealSlot = mealSlot,
                date = Instant.fromEpochMilliseconds(Clock.System.now().toEpochMilliseconds())
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date
            )
                .onSuccess {
                    _uiState.value = CouponState.Success(it)
                }
                .onError {
                    _uiState.value = CouponState.Error(it.toString())
                }
        }
    }

    fun clearError(){
        _uiState.value= CouponState.Idle
    }
}