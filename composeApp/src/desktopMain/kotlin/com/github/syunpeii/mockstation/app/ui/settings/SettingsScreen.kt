package com.github.syunpeii.mockstation.app.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun SettingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MockStationTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Settings Screen",
            style = MockStationTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(MockStationTheme.spacing.medium))
        Text(
            text = "This is a dummy settings screen",
            style = MockStationTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(MockStationTheme.spacing.extraLarge))
        PrimaryButton(
            text = "Save Settings",
            onClick = {
                println("Settings saved!")
            },
        )
    }
}
