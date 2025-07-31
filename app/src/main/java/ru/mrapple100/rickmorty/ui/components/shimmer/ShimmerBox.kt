package ru.mrapple100.rickmorty.ui.components.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    color: Color = Color.LightGray,
    shimmer: Shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.Window,
        theme = ShimmerThemes.defaultShimmerTheme
    )
) {
    Box(
        modifier = modifier
            .clip(shape)
            .shimmer(shimmer)
            .background(color)
    )
}
