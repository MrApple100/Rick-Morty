package ru.mrapple100.rickmorty.ui.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette

fun calcDominantColor(drawable: Drawable, onFinish: (Color, Color) -> Unit) {
    val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

    Palette.from(bmp).generate { palette ->
        palette?.dominantSwatch?.rgb?.let { colorValue ->
            val darkerColor = ColorUtils.blendARGB(colorValue, Color.Black.toArgb(), 0.3f)
            onFinish(Color(colorValue), Color(darkerColor))
        }
    }
}