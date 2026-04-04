package com.github.syunpeii.mockstation.core.designsystem.component.atom.input

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewColumn
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun NumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        modifier = modifier,
        label = label?.let { { Text(text = it) } },
        placeholder = placeholder?.let { { Text(text = it) } },
        enabled = enabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MockStationTheme.colors.primary,
            unfocusedBorderColor = MockStationTheme.colors.outline,
            disabledBorderColor = MockStationTheme.colors.outlineVariant,
        ),
    )
}

@Preview
@Composable
private fun PreviewNumberField() {
    MockStationTheme {
        PreviewColumn {
            var number1 by remember { mutableStateOf("") }
            NumberField(
                value = number1,
                onValueChange = { number1 = it },
                label = "Empty Number Field",
            )

            var number2 by remember { mutableStateOf("5000") }
            NumberField(
                value = number2,
                onValueChange = { number2 = it },
                label = "Delay (ms)",
            )

            NumberField(
                value = "1000",
                onValueChange = {},
                label = "Disabled",
                enabled = false,
            )

            var number3 by remember { mutableStateOf("") }
            NumberField(
                value = number3,
                onValueChange = { number3 = it },
                placeholder = "Enter number",
            )
        }
    }
}
