package com.github.syunpeii.mockstation.core.designsystem.component.atom.input

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        enabled = enabled,
        singleLine = singleLine,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MockStationTheme.colors.primary,
            unfocusedBorderColor = MockStationTheme.colors.outline,
            disabledBorderColor = MockStationTheme.colors.outlineVariant,
        ),
    )
}

@Preview
@Composable
private fun PreviewTextField() {
    MockStationTheme {
        PreviewColumn {
            var text1 by remember { mutableStateOf("") }
            TextField(
                value = text1,
                onValueChange = { text1 = it },
                label = "Empty Field",
            )

            var text2 by remember { mutableStateOf("Sample Text") }
            TextField(
                value = text2,
                onValueChange = { text2 = it },
                label = "Filled Field",
            )

            TextField(
                value = "Disabled Field",
                onValueChange = {},
                label = "Disabled",
                enabled = false,
            )

            var text3 by remember { mutableStateOf("") }
            TextField(
                value = text3,
                onValueChange = { text3 = it },
                placeholder = "Placeholder text",
            )
        }
    }
}
