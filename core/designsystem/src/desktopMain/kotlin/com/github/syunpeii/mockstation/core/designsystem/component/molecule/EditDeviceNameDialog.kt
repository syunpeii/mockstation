package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.TextButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.input.TextField
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.HeadlineSmallText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun EditDeviceNameDialog(
    currentName: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    label: String,
    saveLabel: String,
    cancelLabel: String,
) {
    var name by remember { mutableStateOf(currentName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            HeadlineSmallText(text = title)
        },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = label,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        confirmButton = {
            PrimaryButton(
                text = saveLabel,
                onClick = { onSave(name) },
                enabled = name.isNotBlank(),
            )
        },
        dismissButton = {
            TextButton(
                text = cancelLabel,
                onClick = onDismiss,
            )
        },
        modifier = modifier,
        containerColor = MockStationTheme.colors.surface,
    )
}

@Preview
@Composable
private fun PreviewEditDeviceNameDialog() {
    MockStationTheme {
        PreviewBox {
            EditDeviceNameDialog(
                currentName = "Test Device",
                onDismiss = {},
                onSave = {},
                title = "Edit Device Name",
                label = "Device Name",
                saveLabel = "Save",
                cancelLabel = "Cancel",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewEditDeviceNameDialogEmpty() {
    MockStationTheme {
        PreviewBox {
            EditDeviceNameDialog(
                currentName = "",
                onDismiss = {},
                onSave = {},
                title = "Edit Device Name",
                label = "Device Name",
                saveLabel = "Save",
                cancelLabel = "Cancel",
            )
        }
    }
}
