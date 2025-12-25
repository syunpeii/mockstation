package com.github.syunpeii.mockstation.core.designsystem.component.atom.badge

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme
import com.github.syunpeii.mockstation.core.model.StatusCategory
import com.github.syunpeii.mockstation.core.model.getStatusCategory

@Composable
fun StatusBadge(
    statusCode: Int,
    modifier: Modifier = Modifier,
) {
    val category = statusCode.getStatusCategory()
    val (backgroundColor, textColor) = when (category) {
        StatusCategory.SUCCESS_2XX -> MockStationTheme.colors.tertiaryContainer to MockStationTheme.colors.onTertiaryContainer
        StatusCategory.REDIRECT_3XX -> MockStationTheme.colors.secondaryContainer to MockStationTheme.colors.onSecondaryContainer
        StatusCategory.CLIENT_ERROR_4XX -> MockStationTheme.colors.errorContainer to MockStationTheme.colors.onErrorContainer
        StatusCategory.SERVER_ERROR_5XX -> MockStationTheme.colors.error to MockStationTheme.colors.onError
        StatusCategory.OTHER -> MockStationTheme.colors.surfaceVariant to MockStationTheme.colors.onSurfaceVariant
    }

    Surface(
        modifier = modifier,
        shape = MockStationTheme.shapes.small,
        color = backgroundColor,
    ) {
        Text(
            text = statusCode.toString(),
            style = MockStationTheme.typography.labelSmall,
            color = textColor,
            modifier = Modifier.padding(
                horizontal = MockStationTheme.spacing.small,
                vertical = MockStationTheme.spacing.extraSmall,
            ),
        )
    }
}

@Preview
@Composable
private fun PreviewStatusBadge() {
    MockStationTheme {
        PreviewBox {
            StatusBadge(statusCode = 200)
        }
    }
}

@Preview
@Composable
private fun PreviewStatusBadges() {
    MockStationTheme {
        PreviewBox {
            androidx.compose.foundation.layout.Row(
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(MockStationTheme.spacing.small),
            ) {
                StatusBadge(statusCode = 200)
                StatusBadge(statusCode = 301)
                StatusBadge(statusCode = 404)
                StatusBadge(statusCode = 500)
            }
        }
    }
}
