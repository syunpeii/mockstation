package com.github.syunpeii.mockstation.core.designsystem.component.navigation

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.text.NavigationItemText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun NavigationRailWrapper(
    items: List<NavigationItem>,
    selectedIndex: Int,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = MockStationTheme.colors.surfaceVariant,
        contentColor = MockStationTheme.colors.onSurfaceVariant,
    ) {
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedIndex == index,
                onClick = { onItemClick(index) },
                icon = {
                    Icon(
                        imageVector = if (selectedIndex == index) {
                            item.selectedIcon ?: item.icon
                        } else {
                            item.icon
                        },
                        contentDescription = item.label,
                    )
                },
                label = {
                    NavigationItemText(
                        text = item.label,
                        modifier = Modifier.padding(MockStationTheme.spacing.small),
                    )
                },
                colors = NavigationRailItemDefaults.colors(
                    selectedIconColor = MockStationTheme.colors.onPrimaryContainer,
                    selectedTextColor = MockStationTheme.colors.onSurface,
                    indicatorColor = MockStationTheme.colors.primaryContainer,
                    unselectedIconColor = MockStationTheme.colors.onSurfaceVariant,
                    unselectedTextColor = MockStationTheme.colors.onSurfaceVariant,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewNavigationRailWrapper() {
    MockStationTheme {
        val sampleNavigationItems = listOf(
            NavigationItem(
                label = "Home",
                icon = Icons.Filled.Home,
                selectedIcon = Icons.Outlined.Home,
            ),
            NavigationItem(
                label = "Settings",
                icon = Icons.Filled.Settings,
                selectedIcon = Icons.Outlined.Settings,
            ),
        )

        PreviewBox {
            NavigationRailWrapper(
                items = sampleNavigationItems,
                selectedIndex = 1,
                onItemClick = {},
            )
        }
    }
}
