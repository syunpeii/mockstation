package com.github.syunpeii.mockstation.app.navigation

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute : TopLevelRoute

@Serializable
data object SettingsRoute : TopLevelRoute

@Serializable
data object DeviceManagementRoute : TopLevelRoute
