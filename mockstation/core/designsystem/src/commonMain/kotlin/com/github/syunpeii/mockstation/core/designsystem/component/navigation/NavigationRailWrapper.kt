package com.github.syunpeii.mockstation.core.designsystem.component.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.text.NavigationItemText
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
