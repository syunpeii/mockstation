package com.github.syunpeii.mockstation.app.ui.devicemanagement.model

enum class DeviceManagementTab {
    REGISTERED_DEVICES,
    SERVER_DEVICES,
    REQUEST_HISTORY,
    ;

    companion object {
        fun fromIndex(index: Int): DeviceManagementTab? = entries.getOrNull(index)
    }
}
