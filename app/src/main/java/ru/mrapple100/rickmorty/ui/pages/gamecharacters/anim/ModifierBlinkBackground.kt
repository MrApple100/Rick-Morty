package ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import kotlinx.coroutines.delay
import ru.mrapple100.rickmorty.R


fun Modifier.blinkBackground(
    shouldBlink: Boolean,
    originalColor: Color = Color.Transparent,
    blinkColor: Color,
    duration: Int = 300
): Modifier = composed {
    var isBlinking by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (isBlinking) blinkColor else originalColor,
        animationSpec = tween(durationMillis = duration),
        label = "blink background"
    )

    if (shouldBlink) {
        LaunchedEffect(shouldBlink) {
            if (shouldBlink) {
                isBlinking = true
                delay(duration.toLong())
                isBlinking = false
            }
        }
    }

    this.then(
        Modifier.background(backgroundColor)
    )
}