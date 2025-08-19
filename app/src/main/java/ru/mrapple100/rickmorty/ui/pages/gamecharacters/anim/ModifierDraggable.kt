package ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign


data class ThresholdConfig(
    val low: Float = 0.3f,  // 30% от размера элемента
    val medium: Float = 0.6f, // 60% от размера элемента
    val high: Float = 0.8f   // 80% от размера элемента
)

// Интерфейс для пороговых значений
sealed interface Threshold {
    data class Fixed(val value: Dp) : Threshold
    data class Fractional(val fraction: Float) : Threshold
}
// Перечисление состояний свайпа
enum class SwipeState {
    NONE,   // Свайпа нет
    LOW,    // Преодолен низкий порог
    MEDIUM, // Преодолен средний порог
    FULL    // Преодолен высокий порог
}

// Функция для вычисления порога на основе Density
fun Density.computeThreshold(threshold: Threshold, totalSize: Float): Float {
    return when (threshold) {
        is Threshold.Fixed -> threshold.value.toPx()
        is Threshold.Fractional -> totalSize * threshold.fraction
    }
}

// Функция для создания конфигурации порогов между состояниями
fun thresholdConfig(from: Any, to: Any): Threshold {
    // Здесь вы можете определить различные пороги в зависимости от состояний
    return when {
        // Пример: разные пороги для разных переходов между состояниями
        from == SwipeState.NONE && to == SwipeState.LOW ->
            Threshold.Fractional(0.2f) // 20% для перехода из NONE в LOW

        from == SwipeState.LOW && to == SwipeState.MEDIUM ->
            Threshold.Fractional(0.5f) // 50% для перехода из LOW в MEDIUM

        from == SwipeState.MEDIUM && to == SwipeState.FULL ->
            Threshold.Fractional(0.8f) // 80% для перехода из MEDIUM в FULL

        // Возврат к порогу по умолчанию для других случаев
        else -> Threshold.Fractional(0.5f)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
fun Modifier.draggableStack(
    controller: CardStackController,
    velocityThreshold: Dp = 125.dp
): Modifier = composed {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    val squareSize = 200.dp
    val squareSizePx = with(density) { squareSize.toPx() }

    val velocityThresholdPx = with(density) {
        velocityThreshold.toPx()
    }

    val thresholds = { a: Float, b: Float ->
        val threshold = thresholdConfig(a, b)
        with(density) {
            computeThreshold(threshold, squareSizePx)
        }
    }

    controller.threshold = thresholds(controller.center.x, controller.right.x)

    Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDragEnd = {
                if (controller.offsetX.value <= 0f) {
                    if (controller.offsetX.value > -controller.threshold) {
                        controller.returnCenter()
                    } else {
                        controller.swipeLeft()
                    }
                } else {
                    if (controller.offsetX.value < controller.threshold) {
                        controller.returnCenter()
                    } else {
                        controller.swipeRight()
                    }
                }
            },
            onDrag = { change, dragAmount ->
                controller.scope.apply {
                    launch {
                        controller.offsetX.snapTo(controller.offsetX.value + dragAmount.x)
                        controller.offsetY.snapTo(controller.offsetY.value + dragAmount.y)

                        val targetRotation = normalize(
                            controller.center.x,
                            controller.right.x,
                            abs(controller.offsetX.value),
                            0f,
                            10f
                        )

                        controller.rotation.snapTo(targetRotation * -controller.offsetX.value.sign)

                        controller.scale.snapTo(
                            normalize(
                                controller.center.x,
                                controller.right.x / 3,
                                abs(controller.offsetX.value),
                                0.8f
                            )
                        )
                    }
                }
                change.consume()
            }
        )


    }
}

fun Modifier.moveTo(
    x: Float,
    y: Float
) = this.then(Modifier.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x.roundToInt(), y.roundToInt())
    }
})

fun Modifier.visible(
    visible: Boolean = true
) = this.then(Modifier.layout{ measurable, constraints ->
    val placeable = measurable.measure(constraints)

    if (visible) {
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    } else {
        layout(0, 0) {}
    }
})

private fun normalize(
    min: Float,
    max: Float,
    v: Float,
    startRange: Float = 0f,
    endRange: Float = 1f
): Float {
    require(startRange < endRange) {
        "Start range is greater than end range"
    }

    val value = v.coerceIn(min, max)

    return (value - min) / (max - min) * (endRange + startRange) + startRange
}