package ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mrapple100.rickmorty.ui.theme.Colors
@Composable
fun AnimTwoVerticalCardBlink(
    isBlinking: Boolean
) {
    val swipeOffset = remember { Animatable(0f) }
    var shouldBlink by remember { mutableStateOf(isBlinking) }


    val initialColor = Color.Transparent
    val targetLoseColor = Colors.redLose
    val targetWinColor = Colors.greenWin
    val durationMillis = 4500

    var currentStep by remember { mutableIntStateOf(0) }
    val colorSequence = listOf(
        initialColor, // 0: Начальный
        targetLoseColor,   // 1: Первый
        initialColor, // 2: Возврат к начальному
        targetWinColor,  // 3: Второй
        initialColor  // 4: Возврат к начальному
    )


    // Смена шага анимации
    LaunchedEffect(isBlinking) {
        if (isBlinking) {
            while (true) {
                delay(durationMillis / 5L) // Делим цикл на 5 частей
                currentStep = (currentStep + 1) % colorSequence.size
            }
        }

    }
    val animatedColor by animateColorAsState(
        targetValue = colorSequence[currentStep],
        animationSpec = tween(durationMillis / 5), // Плавный переход для каждого шага
        label = "colorAnimation"
    )



    Box(
        modifier = Modifier.size(150.dp, 250.dp)
    ) {
        Column(modifier = Modifier
                .size(150.dp, 250.dp)
                .clip(RoundedCornerShape(5))
                .zIndex(2f)
                .background(animatedColor) )
        {

        }
        Column(
            modifier = Modifier
                .size(150.dp, 250.dp)
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
                        .size(80.dp, 80.dp)

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
                Card(
                    modifier = Modifier
                        .size(80.dp, 80.dp)
                ) {
                    // Контент второй карточки
                }
            }
        }
    }
}