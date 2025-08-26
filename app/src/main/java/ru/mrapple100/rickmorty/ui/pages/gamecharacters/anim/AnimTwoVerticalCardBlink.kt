package ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import ru.mrapple100.rickmorty.ui.theme.Colors
@Composable
fun AnimTwoVerticalCardBlink() {
    Box(
        modifier = Modifier.size(150.dp, 250.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().blinkBackground(true, blinkColor = Colors.redLose)) {

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