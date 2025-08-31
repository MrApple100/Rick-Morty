package ru.mrapple100.rickmorty.ui.components.organism

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mrapple100.rickmorty.ui.components.molecules.RedGreenIndicator

@Composable
fun RedGreenIndicatorWithText(
    modifier: Modifier = Modifier,
    leftColor: Color,
    rightColor: Color,
    proportion: Float,
    correctCount: Int,
    incorrectCount: Int
) {
    Box(
        modifier = modifier
    ) {
        RedGreenIndicator(
            modifier = Modifier.fillMaxSize(),
            leftColor = leftColor,
            rightColor = rightColor,
            proportion = proportion
        )
        Text(
            text = correctCount.toString(),
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 4.dp, y = (-4).dp) // Смещение вверх и вправо
        )
        Text(
            text = incorrectCount.toString(),
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-4).dp, y = (-4).dp) // Смещение вверх и влево
        )
    }
}
