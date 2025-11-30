package com.github.syunpeii.mockstation.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.syunpeii.mockstation.core.designsystem.component.PrimaryButton

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Mockstation Desktop App")
        Spacer(modifier = Modifier.height(16.dp))
        Text("KMP Desktop + Ktor Server")
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryButton(
            text = "Get Started",
            onClick = {
                println("Button clicked!")
            },
        )
    }
}
