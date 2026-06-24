package com.messon.student.app.presentation.ui.state

sealed class SubmitRatingState {
    object Idle : SubmitRatingState()
    object Loading :  SubmitRatingState()
    data class Success(val resp: String) :  SubmitRatingState()
    data class Error(val message: String) : SubmitRatingState()
}