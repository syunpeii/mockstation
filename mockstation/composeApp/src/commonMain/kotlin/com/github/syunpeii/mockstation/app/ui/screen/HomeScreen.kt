package com.github.syunpeii.mockstation.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MockStationTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Mock Station Desktop App",
            style = MockStationTheme.typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(MockStationTheme.spacing.medium))
        Text(
            text = "KMP Desktop + Ktor Server",
            style = MockStationTheme.typography.bodyLarge,
        )
        Spacer(modifier = Modifier.height(MockStationTheme.spacing.extraLarge))
        PrimaryButton(
            text = "Get Started",
            onClick = {
                println("Button clicked!")
            },
        )
    }
}
