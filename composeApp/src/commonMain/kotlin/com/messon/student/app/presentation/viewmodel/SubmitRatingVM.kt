package com.messon.student.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.dataModels.SubmitRatingReq
import com.messon.student.app.domain.repository.MessRepository
import com.messon.student.app.presentation.ui.state.SubmitRatingState
import com.messon.student.app.utils.MealSlot
import com.messon.student.app.utils.onError
import com.messon.student.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SubmitRatingVM(
    private val secureStorage: AuthSecureStorage,
    private val messRepository: MessRepository
): ViewModel(){

    private val _breakfastRating=MutableStateFlow<Int>(0)
    private val _lunchRating=MutableStateFlow<Int>(0)
    private val _dinnerRating=MutableStateFlow<Int>(0)

    fun setBreakfastRating(rating: Int){
        _breakfastRating.value=rating
    }

    fun setLunchRating(rating: Int){
        _lunchRating.value=rating
    }

    fun setDinnerRating(rating: Int){
        _dinnerRating.value=rating
    }

    private val _uiState = MutableStateFlow<SubmitRatingState>(SubmitRatingState.Idle)
    val uiState: StateFlow<SubmitRatingState> = _uiState.asStateFlow()
    fun submitRating(
        mealSlot: String,
    ){
        if(_uiState.value is SubmitRatingState.Loading) return
        _uiState.value= SubmitRatingState.Loading
        viewModelScope.launch (Dispatchers.IO){
            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _uiState.value =
                    SubmitRatingState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.submitRating(
                token = token,
                submitRatingReq = SubmitRatingReq(
                    mealSlot = mealSlot,
                    rating = when (mealSlot) {
                        MealSlot.BREAKFAST.value -> _breakfastRating.value
                        MealSlot.LUNCH.value-> _lunchRating.value
                        MealSlot.DINNER.value -> _dinnerRating.value
                        else -> 0
                    }
                ),
            )
                .onSuccess {
                    _uiState.value= SubmitRatingState.Success(resp=it)
                }
                .onError {
                    _uiState.value= SubmitRatingState.Error(message = it.toString())
                }
        }
    }


    fun resetRatings() {
        _breakfastRating.value = 0
        _lunchRating.value = 0
        _dinnerRating.value = 0
        _uiState.value = SubmitRatingState.Idle
    }

}