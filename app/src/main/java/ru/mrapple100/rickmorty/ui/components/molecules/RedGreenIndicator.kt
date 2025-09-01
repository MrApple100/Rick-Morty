package ru.mrapple100.rickmorty.ui.components.molecules

import android.widget.ProgressBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mrapple100.rickmorty.ui.theme.Colors

@Composable
fun RedGreenIndicator(
    modifier: Modifier = Modifier,
    leftColor: Color,
    rightColor: Color,
    proportion: Float
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxHeight()
    ) {
        val parentWidth = maxWidth

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .fillMaxHeight()
                .background(rightColor)
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp,0.dp,0.dp,5.dp))
                .fillMaxHeight()
                .fillMaxWidth(fraction = proportion.coerceIn(0f, 1f))
                .background(leftColor)
        )

        Box(
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight()
                .offset(x = (parentWidth * proportion.coerceIn(0f, 1f)) - 1.dp)
                .background(Colors.lightGray)
        )
    }
}

@Composable
@Preview
fun ProgressBarPreview() {
    RedGreenIndicator(
        leftColor = Color.Green,
        rightColor = Color.Red,
        proportion = 0.6f,
        modifier = Modifier
            .width(200.dp)
            .height(20.dp)
    )
}
