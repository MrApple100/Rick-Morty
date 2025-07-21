package ru.mrapple100.rickmorty.ui.components.molecules


import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingMessage(message: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(
            text = message,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun LoadingMessage_Preview() {
    LoadingMessage("Download Pokemon Data...")
}