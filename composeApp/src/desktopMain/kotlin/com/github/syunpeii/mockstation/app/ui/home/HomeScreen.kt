package com.github.syunpeii.mockstation.app.ui.home

import androidx.compose.foundation.background
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
import mockstation.composeapp.generated.resources.Res
import mockstation.composeapp.generated.resources.home_button_get_started
import mockstation.composeapp.generated.resources.home_subtitle
import mockstation.composeapp.generated.resources.home_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .background(MockStationTheme.colors.background)
            .fillMaxSize()
            .padding(MockStationTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        HeadlineMediumText(
            text = stringResource(Res.string.home_title),
        )
        Spacer(modifier = Modifier.height(MockStationTheme.spacing.medium))
        BodyLargeText(
            text = stringResource(Res.string.home_subtitle),
        )
        Spacer(modifier = Modifier.height(MockStationTheme.spacing.extraLarge))
        PrimaryButton(
            text = stringResource(Res.string.home_button_get_started),
            onClick = {
                println("Button clicked!")
            },
        )
    }
}
