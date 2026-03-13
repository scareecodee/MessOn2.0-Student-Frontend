package com.messon.student.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.repository.MessRepository
import com.messon.student.app.presentation.ui.state.PrefSetupState
import com.messon.student.app.utils.WeekType
import com.messon.student.app.utils.onError
import com.messon.student.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MealPlannerViewModel(
    private val secureStorage: AuthSecureStorage,
   private val messRepository: MessRepository
)
    : ViewModel() {

    private val _prefSetupState = MutableStateFlow<PrefSetupState>(PrefSetupState.Idle)
    val prefSetupState: StateFlow<PrefSetupState> =_prefSetupState.asStateFlow()

    fun fetchPrefSetupData(
    ) {
        if(_prefSetupState.value is PrefSetupState.Loading) return
        _prefSetupState.value = PrefSetupState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _prefSetupState.value =
                    PrefSetupState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.fetchPrefSetupData(
                token = token,
                weekType = WeekType.CURRENT_WEEK.value,
            )
                .onSuccess {
                    _prefSetupState.value = PrefSetupState.Success(it)
                }
                .onError {
                    _prefSetupState.value = PrefSetupState.Error(it.toString())
                }
        }
    }

    fun clearPrefSetupState(){
        _prefSetupState.value= PrefSetupState.Idle
    }


}

