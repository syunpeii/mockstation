package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.AppIconButton
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun SearchTagInput(
    currentInput: String,
    onInputChange: (String) -> Unit,
    onAddTag: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
) {
    OutlinedTextField(
        value = currentInput,
        onValueChange = onInputChange,
        modifier = modifier,
        placeholder = {
            Text(
                style = MockStationTheme.typography.bodyMedium,
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
            AppIconButton(
                imageVector = Icons.Default.Add,
                contentDescription = "Add tag",
                onClick = onAddTag,
                enabled = currentInput.isNotEmpty(),
            )
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MockStationTheme.colors.primary,
            unfocusedBorderColor = MockStationTheme.colors.outline,
        ),
    )
}

@Preview
@Composable
private fun PreviewSearchTagInput() {
    MockStationTheme {
        PreviewColumn {
            var input by remember { mutableStateOf("") }
            SearchTagInput(
                currentInput = input,
                onInputChange = { input = it },
                onAddTag = { println("Enter pressed with: $input") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Enter tags (Space/Enter to add)...",
            )

            var input2 by remember { mutableStateOf("test") }
            SearchTagInput(
                currentInput = input2,
                onInputChange = { input2 = it },
                onAddTag = { println("Enter pressed") },
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Search...",
            )
        }
    }
}
