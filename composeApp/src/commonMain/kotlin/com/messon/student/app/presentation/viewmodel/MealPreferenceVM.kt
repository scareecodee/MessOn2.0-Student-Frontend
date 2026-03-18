package com.messon.student.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.dataModels.DaySelection
import com.messon.student.app.domain.dataModels.MealPref
import com.messon.student.app.domain.dataModels.PrefSetupData
import com.messon.student.app.domain.repository.MessRepository
import com.messon.student.app.presentation.ui.screens.mess.WEEK_DATA
import com.messon.student.app.presentation.ui.state.PrefSetupState
import com.messon.student.app.presentation.ui.state.SavePrefState
import com.messon.student.app.utils.MealSlot
import com.messon.student.app.utils.MealType
import com.messon.student.app.utils.WeekType
import com.messon.student.app.utils.onError
import com.messon.student.app.utils.onSuccess
import com.messon.student.app.utils.toApiDay
import com.messon.student.app.utils.toApiValue
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

    private val _preferences =
        MutableStateFlow<Map<String, DaySelection>>(emptyMap())

    val preferences: StateFlow<Map<String, DaySelection>> =
        _preferences.asStateFlow()


    fun initPreferences(prefSetupData: List<PrefSetupData>) {
        val map = mutableMapOf<String, DaySelection>()

        WEEK_DATA.forEach { day ->

            val dayMeals = prefSetupData.filter {
                it.day.lowercase().startsWith(day.id.lowercase())
            }

            val breakfast = dayMeals.find { it.mealSlot == MealSlot.BREAKFAST.value }
            val lunch = dayMeals.find { it.mealSlot == MealSlot.LUNCH.value }
            val dinner = dayMeals.find { it.mealSlot == MealSlot.DINNER.value }

            map[day.id] = DaySelection(
                breakfast = if (breakfast?.isCompulsory == true) MealType.VEG else MealType.NONE,
                lunch = if (lunch?.isCompulsory == true) MealType.VEG else MealType.NONE,
                dinner = if (dinner?.isCompulsory == true) MealType.VEG else MealType.NONE
            )
        }

        _preferences.value = map
    }

    fun updateMeal(
        dayId: String,
        mealSlot: MealSlot,
        type: MealType,
        isCompulsory: Boolean
    ) {
        val current = _preferences.value.toMutableMap()
        val day = current[dayId] ?: DaySelection()

        val updatedDay = when (mealSlot) {
            MealSlot.BREAKFAST -> day.copy(
                breakfast = if (isCompulsory) type
                else if (day.breakfast == type) MealType.NONE else type
            )

            MealSlot.LUNCH -> day.copy(
                lunch = if (isCompulsory) type
                else if (day.lunch == type) MealType.NONE else type
            )

            MealSlot.DINNER -> day.copy(
                dinner = if (isCompulsory) type
                else if (day.dinner == type) MealType.NONE else type
            )
        }

        current[dayId] = updatedDay
        _preferences.value = current
    }
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




    private val _savePrefState= MutableStateFlow<SavePrefState>(SavePrefState.Idle)
    val savePrefState: StateFlow<SavePrefState> =_savePrefState.asStateFlow()


    private fun buildMealPrefList(): List<MealPref> {
        val result = mutableListOf<MealPref>()

        _preferences.value.forEach { (day, selection) ->

            result.add(
                MealPref(
                    day = day.toApiDay(),
                    mealSlot = MealSlot.BREAKFAST.value,
                    mealType = selection.breakfast.toApiValue()
                )
            )

            result.add(
                MealPref(
                    day = day.toApiDay(),
                    mealSlot = MealSlot.LUNCH.value,
                    mealType = selection.lunch.toApiValue()
                )
            )

            result.add(
                MealPref(
                    day = day.toApiDay(),
                    mealSlot = MealSlot.DINNER.value,
                    mealType = selection.dinner.toApiValue()
                )
            )
        }

        return result
    }

    fun saveMealPref() {
        if(_savePrefState.value is SavePrefState.Loading) return
        _savePrefState.value = SavePrefState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            val token = secureStorage.getAccessToken()

            if (token.isNullOrBlank()) {
                _savePrefState.value =
                    SavePrefState.Error("Session expired. Please login again.")
                return@launch
            }

            messRepository.saveMealPref(
                token = token,
                weekType = WeekType.CURRENT_WEEK.value,
                mealPref =buildMealPrefList(),
            )
                .onSuccess {
                    _savePrefState.value = SavePrefState.Success(it)
                }
                .onError {
                    _savePrefState.value = SavePrefState.Error(it.toString())
                }
        }
    }


    fun clearSavePrefState(){
        _savePrefState.value= SavePrefState.Idle
    }


}

