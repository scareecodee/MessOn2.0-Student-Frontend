package com.messon.student.app.domain.auth.provider

import com.messon.student.app.domain.provider.DeviceInfoProvider

import com.messon.student.app.domain.dataModels.DeviceInfo
import com.messon.student.app.domain.dataModels.Platform
import platform.Foundation.NSBundle
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.localeIdentifier
import platform.UIKit.UIDevice

class IosDeviceInfoProvider : DeviceInfoProvider {

    override suspend fun getDeviceInfo(): DeviceInfo {
        val device = UIDevice.currentDevice
        return DeviceInfo(
            deviceId = getDeviceId(),
            platform = Platform.IOS,
            appVersion = getAppVersion(),
            osVersion = device.systemVersion,
            locale = NSLocale.currentLocale.localeIdentifier
        )
    }

    private fun getDeviceId(): String {
        return UIDevice.currentDevice.identifierForVendor?.UUIDString ?: "unknown"
    }

    private fun getAppVersion(): String {
        val info = NSBundle.mainBundle.infoDictionary
        return info?.get("CFBundleShortVersionString") as? String ?: "1.0"
    }
}