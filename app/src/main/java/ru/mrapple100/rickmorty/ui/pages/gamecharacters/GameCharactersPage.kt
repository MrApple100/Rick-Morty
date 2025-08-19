package ru.mrapple100.rickmorty.ui.pages.gamecharacters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import ru.mrapple100.rickmorty.ui.components.organism.CharacterVerticalCard
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim.draggableStack
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim.moveTo
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim.rememberCardStackController
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim.visible

@Composable
fun GameCharactersPage(
    state: GameCharactersState,
    postChangeCharacter: () -> Unit,
    postShowWinStatus: () -> Unit,
    postShowLoseStatus: () -> Unit
){

    val cardStackController = rememberCardStackController()
    val cardStackController2 = rememberCardStackController()


    Scaffold(
        topBar = {},
        content = { it ->
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier

                    .fillMaxHeight(fraction = 0.5f)
                    .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    CharacterVerticalCard(
                        modifier = Modifier
                            .draggableStack(
                                controller = cardStackController,
                                velocityThreshold = 125.dp
                            )
                            .moveTo(
                                x = cardStackController.offsetX.value,
                                y = cardStackController.offsetY.value
                            )
                            .graphicsLayer(
                                rotationZ = cardStackController.rotation.value,
                                //scaleX = cardStackController.scale.value,
                                //scaleY = cardStackController.scale.value
                            ),
                        onClick = {},
                        characterModel = state.pair.first!!
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center

                ){
                    CharacterVerticalCard(
                        modifier = Modifier
                            .draggableStack(
                                controller = cardStackController2,
                                velocityThreshold = 125.dp
                            )
                            .moveTo(
                                x = cardStackController2.offsetX.value,
                                y = cardStackController2.offsetY.value
                            )
                            .graphicsLayer(
                                rotationZ = cardStackController2.rotation.value,
                                //scaleX = cardStackController2.scale.value,
                                //scaleY = cardStackController2.scale.value
                            ),
                        onClick = {},
                        characterModel = state.pair.second!!
                    )
                }
            }
        }
    )
}