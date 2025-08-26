package ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.mrapple100.rickmorty.ui.theme.Colors

@Composable
fun AnimTwoVerticalCards(
    isAnimating: Boolean = true // Управление анимацией
) {
    val swipeOffset = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    var shouldAnimate by remember { mutableStateOf(isAnimating) }

    LaunchedEffect(shouldAnimate) {
        if (shouldAnimate) {
            launch {
                rotation.animateTo(
                    targetValue = 15f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1500),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
            launch {
                swipeOffset.animateTo(
                    targetValue = 160f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1500),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }

        } else {
            swipeOffset.stop()
            rotation.stop()
            swipeOffset.animateTo(0f, animationSpec = tween(200))
            rotation.animateTo(0f, animationSpec = tween(200))
        }
    }

    Column(
        modifier = Modifier
            .size(150.dp,250.dp)
            .clip(RoundedCornerShape(5))
            .background(Colors.lightGray),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(fraction = 0.5f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .size(80.dp,80.dp)
                    .graphicsLayer(
                        translationX = swipeOffset.value,
                        rotationZ = rotation.value,
                )

            ) {

            }
        }
        Box(
            modifier = Modifier
                .background(Colors.redLose)
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(modifier = Modifier
                .size(80.dp,80.dp)
            ) {
                // Контент второй карточки
            }
        }
    }
}
