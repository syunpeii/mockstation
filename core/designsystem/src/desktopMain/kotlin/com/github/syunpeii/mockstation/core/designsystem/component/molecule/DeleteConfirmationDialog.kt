package com.github.syunpeii.mockstation.core.designsystem.component.molecule

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.PrimaryButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.button.TextButton
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.BodyMediumText
import com.github.syunpeii.mockstation.core.designsystem.component.atom.text.HeadlineSmallText
import com.github.syunpeii.mockstation.core.designsystem.preview.PreviewBox
import com.github.syunpeii.mockstation.core.designsystem.theme.MockStationTheme

@Composable
fun DeleteConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    confirmLabel: String,
    cancelLabel: String,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            HeadlineSmallText(text = title)
        },
        text = {
            BodyMediumText(text = message)
        },
        confirmButton = {
            PrimaryButton(
                text = confirmLabel,
                onClick = onConfirm,
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
private fun PreviewDeleteConfirmationDialog() {
    MockStationTheme {
        PreviewBox {
            DeleteConfirmationDialog(
                onDismiss = {},
                onConfirm = {},
                title = "Delete Device",
                message = "Are you sure you want to delete this device?",
                confirmLabel = "Delete",
                cancelLabel = "Cancel",
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDeleteConfirmationDialogLongName() {
    MockStationTheme {
        PreviewBox {
            DeleteConfirmationDialog(
                onDismiss = {},
                onConfirm = {},
                title = "Delete Device",
                message = "Are you sure you want to delete this device?",
                confirmLabel = "Delete",
                cancelLabel = "Cancel",
            )
        }
    }
}
