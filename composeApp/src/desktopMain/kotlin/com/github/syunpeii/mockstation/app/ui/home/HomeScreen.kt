package com.github.syunpeii.mockstation.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyLargeText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.HeadlineMediumText
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
        HeadlineMediumText(
            text = "Mock Station Desktop App",
        )
        Spacer(modifier = Modifier.height(MockStationTheme.spacing.medium))
        BodyLargeText(
            text = "KMP Desktop + Ktor Server",
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
