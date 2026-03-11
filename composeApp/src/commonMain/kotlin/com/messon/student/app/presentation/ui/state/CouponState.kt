package com.messon.student.app.presentation.ui.state

import com.messon.student.app.domain.dataModels.CouponDetail

sealed class CouponState {
    object Idle : CouponState()
    object Loading : CouponState()
    data class Success(val couponDetail: CouponDetail) : CouponState()
    data class Error(val message: String) : CouponState()
}