package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun TabBar(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    SecondaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier.padding(horizontal = MockStationTheme.spacing.medium),
        containerColor = MockStationTheme.colors.surface,
        contentColor = MockStationTheme.colors.primary,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = false),
                color = MockStationTheme.colors.primary,
            )
        },
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = selectedTabIndex == index
            Tab(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        style = MockStationTheme.typography.labelLarge,
                        text = title,
                        color = if (isSelected) {
                            MockStationTheme.colors.primary
                        } else {
                            MockStationTheme.colors.onSurfaceVariant
                        },
                    )
                },
                selectedContentColor = MockStationTheme.colors.primary,
                unselectedContentColor = MockStationTheme.colors.onSurfaceVariant,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTabBar() {
    MockStationTheme {
        PreviewBox {
            var selectedTab by remember { mutableIntStateOf(0) }
            TabBar(
                tabs = listOf("Tab 1", "Tab 2", "Tab 3"),
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTabBarWithLongTitles() {
    MockStationTheme {
        PreviewBox {
            var selectedTab by remember { mutableIntStateOf(1) }
            TabBar(
                tabs = listOf(
                    "Registered Devices",
                    "Server Device List",
                    "API Request History",
                ),
                selectedTabIndex = selectedTab,
                onTabSelected = { selectedTab = it },
            )
        }
    }
}
