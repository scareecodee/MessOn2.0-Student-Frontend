package com.messon.app.presentation.viewmodel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.messon.app.data.remote.dto.GetCouponReqDto
import com.messon.app.data.remote.network.Coupon
import com.messon.app.domain.repository.CouponRepository
import com.messon.app.utils.CouponState
import com.messon.app.utils.onError
import com.messon.app.utils.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

import kotlinx.coroutines.*

class CouponViewModel(
    private val getCouponReqDto: GetCouponReqDto,
    private val couponRepository: CouponRepository,
    private val dispatcher: CoroutineDispatcher
): ViewModel()
{
    private val viewModelScope =
        CoroutineScope(SupervisorJob() + dispatcher)

    private val _uiState = mutableStateOf<CouponState>(CouponState.Idle)
    val uiState: State<CouponState> = _uiState



    fun onCouponClick() {
        _uiState.value = CouponState.Loading

        viewModelScope.launch {
           couponRepository.fetchCoupon(getCouponReqDto)
               .onSuccess {couponDetail ->
                    _uiState.value= CouponState.Success(couponDetail)
               }
               .onError {
                   _uiState.value= CouponState.Error(it.toString())

               }
        }
    }

    fun clear() {
        viewModelScope.cancel()
    }
}
