package ru.mrapple100.rickmorty.ui.components.molecules

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SmallCard(
    text: String,
    modifier: Modifier = Modifier,
    backGroundColor: Color = MaterialTheme.colorScheme.surface,
) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(backGroundColor),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(vertical = 2.dp)
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview
@Composable
private fun SmallCard_Preview() {
    SmallCard(text = "HEIGHT: TEXT", modifier = Modifier.wrapContentSize())
}