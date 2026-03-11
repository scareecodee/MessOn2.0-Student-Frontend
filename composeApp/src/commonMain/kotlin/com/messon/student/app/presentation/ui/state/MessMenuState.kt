package com.messon.student.app.presentation.ui.state

import com.messon.student.app.domain.dataModels.Menu


sealed class FetchMenuState {
    object Idle : FetchMenuState()
    object Loading :  FetchMenuState()
    data class Success(val menus: List<Menu>) : FetchMenuState()
    data class Error(val message: String) : FetchMenuState()
}