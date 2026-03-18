package com.messon.student.app.presentation.ui.state
import com.messon.student.app.domain.dataModels.PrefSetupData

sealed class PrefSetupState {
    object Idle : PrefSetupState()
    object Loading :PrefSetupState()
    data class Success(val prefSetupData: List<PrefSetupData>) : PrefSetupState()
    data class Error(val message: String) : PrefSetupState()
}


sealed class SavePrefState {
    object Idle : SavePrefState()
    object Loading : SavePrefState()
    data class Success(val res: String) :  SavePrefState()
    data class Error(val message: String) :  SavePrefState()
}