package com.github.syunpeii.mockstation.core.designsystem.component.atom.badge

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import com.github.syunpeii.mockstation.core.model.HttpMethod

@Composable
fun MethodBadge(
    method: HttpMethod,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, textColor) = when (method) {
        HttpMethod.GET -> MockStationTheme.colors.primaryContainer to MockStationTheme.colors.onPrimaryContainer
        HttpMethod.POST -> MockStationTheme.colors.tertiaryContainer to MockStationTheme.colors.onTertiaryContainer
        HttpMethod.PUT -> MockStationTheme.colors.secondaryContainer to MockStationTheme.colors.onSecondaryContainer
        HttpMethod.DELETE -> MockStationTheme.colors.errorContainer to MockStationTheme.colors.onErrorContainer
        else -> MockStationTheme.colors.surfaceVariant to MockStationTheme.colors.onSurfaceVariant
    }

    Surface(
        modifier = modifier,
        shape = MockStationTheme.shapes.small,
        color = backgroundColor,
    ) {
        Text(
            text = method.toDisplayString(),
            style = MockStationTheme.typography.labelMedium,
            color = textColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                horizontal = MockStationTheme.spacing.small,
                vertical = MockStationTheme.spacing.extraSmall,
            ),
        )
    }
}

@Preview
@Composable
private fun PreviewMethodBadge() {
    MockStationTheme {
        PreviewBox {
            MethodBadge(method = HttpMethod.GET)
        }
    }
}

@Preview
@Composable
private fun PreviewMethodBadges() {
    MockStationTheme {
        PreviewBox {
            androidx.compose.foundation.layout.Row(
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(MockStationTheme.spacing.small),
            ) {
                MethodBadge(method = HttpMethod.GET)
                MethodBadge(method = HttpMethod.POST)
                MethodBadge(method = HttpMethod.PUT)
                MethodBadge(method = HttpMethod.DELETE)
            }
        }
    }
}
