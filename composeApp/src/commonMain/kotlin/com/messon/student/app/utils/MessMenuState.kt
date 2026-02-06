package com.messon.student.app.utils

import com.messon.student.app.domain.dataModels.EachDayMenu


sealed class MessMenuState {
    object Idle : MessMenuState()
    object Loading : MessMenuState()
    data class Success(val eachDayMenu: EachDayMenu) : MessMenuState()
    data class Error(val message: String) : MessMenuState()
}