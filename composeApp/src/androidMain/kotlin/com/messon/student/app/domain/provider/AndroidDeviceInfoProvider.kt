package com.messon.student.app.domain.provider

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.messon.student.app.domain.dataModels.DeviceInfo
import com.messon.student.app.domain.dataModels.Platform
import java.util.Locale

class AndroidDeviceInfoProvider(
    private val context: Context
) : DeviceInfoProvider {

    override suspend fun getDeviceInfo(): DeviceInfo {
        return DeviceInfo(
            deviceId = getDeviceId(),
            platform = Platform.ANDROID,
            appVersion = getAppVersion(),
            osVersion = Build.VERSION.RELEASE ?: "unknown",
            locale = Locale.getDefault().toString()
        )
    }

    private fun getDeviceId(): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        ) ?: "unknown"
    }

    private fun getAppVersion(): String {
        return try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionName ?: "1.0"
        } catch (e: Exception) {
            "1.0"
        }
    }
}