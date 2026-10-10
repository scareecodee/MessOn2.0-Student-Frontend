package com.messon.student.app.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.messon.student.app.core.AppError
import com.messon.student.app.core.PushNotificationManager
import com.messon.student.app.domain.dataModels.DeviceInfo
import com.messon.student.app.domain.dataModels.NotificationState
import com.messon.student.app.domain.dataModels.PermissionStatus
import com.messon.student.app.domain.repository.DeviceRepository
import com.messon.student.app.domain.usecase.RegisterDeviceUseCase
import com.messon.student.app.domain.usecase.UnregisterDeviceUseCase
import com.messon.student.app.presentation.ui.state.NotificationUiState
import com.messon.student.app.utils.onFailure
import com.messon.student.app.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class NotificationViewModel(
    private val registerDeviceUseCase: RegisterDeviceUseCase,
    private val unregisterDeviceUseCase: UnregisterDeviceUseCase,
    private val deviceRepository: DeviceRepository,
    private val pushNotificationManager: PushNotificationManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    private var currentDeviceId: String? = null

    init {
        viewModelScope.launch(Dispatchers.Main) {
            pushNotificationManager.observeNotifications().collect { payload ->
                _uiState.update { state ->
                    state.copy(
                        notifications = state.notifications + payload,
                        unreadCount = state.unreadCount + 1
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            pushNotificationManager.observeTokenRefresh().collect { newToken ->
                val state = _uiState.value.notificationState
                if (state is NotificationState.Registered) {
                    currentDeviceId?.let { deviceId ->
                        deviceRepository.updateDeviceToken(deviceId, newToken).onSuccess {
                            _uiState.update { it.copy(notificationState = NotificationState.Registered(newToken)) }
                        }
                    }
                }
            }
        }
    }

    fun onLogin(deviceInfo: DeviceInfo) {
        currentDeviceId = deviceInfo.deviceId
        registerDevice(deviceInfo)
    }

    fun onLogout(deviceId: String) {
        _uiState.update { it.copy(notificationState = NotificationState.Unregistering) }
        viewModelScope.launch(Dispatchers.Main) {
            unregisterDeviceUseCase(deviceId)
                .onSuccess {
                    _uiState.update { it.copy(notificationState = NotificationState.Unregistered) }
                    pushNotificationManager.unregisterFromPushNotifications()
                    currentDeviceId = null
                }
                .onFailure { error ->
                    _uiState.update { it.copy(notificationState = NotificationState.Error(error)) }
                }
        }
    }

    fun requestPermission() {
        viewModelScope.launch(Dispatchers.Main) {
            val status = pushNotificationManager.requestPermission()
            _uiState.update { it.copy(permissionStatus = status) }
        }
    }

    fun retryRegistration(deviceInfo: DeviceInfo) {
        registerDevice(deviceInfo)
    }

    fun markNotificationsRead() {
        _uiState.update { it.copy(unreadCount = 0) }
    }


    private fun registerDevice(deviceInfo: DeviceInfo) {
        _uiState.update { it.copy(notificationState = NotificationState.Registering) }
        viewModelScope.launch(Dispatchers.Main) {
            val permission = pushNotificationManager.requestPermission()
            _uiState.update { it.copy(permissionStatus = permission) }

            if (permission != PermissionStatus.GRANTED) {
                _uiState.update { it.copy(notificationState = NotificationState.PermissionDenied) }
                return@launch
            }

            pushNotificationManager.registerForPushNotifications()

            registerDeviceUseCase(deviceInfo)
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(notificationState = NotificationState.Registered(response.token))
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(notificationState = NotificationState.Error(error))
                    }
                }
        }
    }

}
