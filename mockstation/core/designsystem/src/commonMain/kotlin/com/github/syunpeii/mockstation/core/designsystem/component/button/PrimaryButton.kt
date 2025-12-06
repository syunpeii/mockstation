package com.github.syunpeii.mockstation.core.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    CompositionLocalProvider(
        LocalRippleConfiguration provides MockStationTheme.ripple.forPrimaryBackground(),
    ) {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = MockStationTheme.colors.primary,
                contentColor = MockStationTheme.colors.onPrimary,
            ),
            contentPadding = PaddingValues(
                horizontal = MockStationTheme.spacing.medium,
                vertical = MockStationTheme.spacing.small,
            ),
        ) {
            Text(text = text)
        }
    }
}
