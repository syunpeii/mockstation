package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.AppIconButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            placeholder = {
                BodyMediumText(
                    text = placeholder,
                    color = MockStationTheme.colors.onSurfaceVariant,
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MockStationTheme.colors.onSurfaceVariant,
                )
            },
            trailingIcon = {
                if (value.isNotEmpty()) {
                    AppIconButton(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear",
                        onClick = { onValueChange("") },
                    )
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MockStationTheme.colors.primary,
                unfocusedBorderColor = MockStationTheme.colors.outline,
            ),
        )
    }
}

@Preview
@Composable
private fun PreviewSearchBar() {
    MockStationTheme {
        PreviewColumn {
            var searchText1 by remember { mutableStateOf("") }
            SearchBar(
                value = searchText1,
                onValueChange = { searchText1 = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Filter by device ID...",
            )

            var searchText2 by remember { mutableStateOf("device-001") }
            SearchBar(
                value = searchText2,
                onValueChange = { searchText2 = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Search...",
            )

            SearchBar(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Search devices...",
            )
        }
    }
}
