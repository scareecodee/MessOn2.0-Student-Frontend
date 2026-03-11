package com.messon.student.app.presentation.ui.state

import com.messon.student.app.domain.dataModels.College

sealed class FetchCollegesState {
    object Idle : FetchCollegesState()
    object Loading : FetchCollegesState()
    data class Success(val colleges: List<College>) : FetchCollegesState()
    data class Error(val message: String) : FetchCollegesState()
}