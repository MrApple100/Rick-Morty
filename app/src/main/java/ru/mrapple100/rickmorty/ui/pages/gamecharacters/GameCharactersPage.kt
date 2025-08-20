package ru.mrapple100.rickmorty.ui.pages.gamecharacters

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import ru.mrapple100.rickmorty.R
import ru.mrapple100.rickmorty.ui.common.UiStatus
import ru.mrapple100.rickmorty.ui.components.organism.CharacterVerticalCard
import ru.mrapple100.rickmorty.ui.components.organism.ShimmeredCharacterVerticalCard
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim.blinkBackground
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim.draggableStack
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim.moveTo
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim.rememberCardStackController
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.anim.visible
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.ChooseUser
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.GameStatus
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.WINLOSEStatus

@Composable
fun GameCharactersPage(
    state: GameCharactersState,
    postChangeCharacter: () -> Unit,
    postShowStatus: (ChooseUser) -> Unit,
){
    var shouldBlink by remember { mutableStateOf(false) }
    var visibleCardState by remember { mutableStateOf(true) }
    var visibleCardState2 by remember { mutableStateOf(true) }



    val cardStackController = rememberCardStackController()
    val cardStackController2 = rememberCardStackController()
    cardStackController.onSwipe = {
        visibleCardState = false
        postShowStatus(ChooseUser.CardFirst)
    }

    cardStackController2.onSwipe = {
        visibleCardState2 = false
        postShowStatus(ChooseUser.CardSecond)
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Кто появился раньше?",
                    textAlign = TextAlign.Center
                )
            }
        },
        content = { it ->
            when(state.status) {
                is UiStatus.Success -> {
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
                                .width(220.dp),

                            contentAlignment = Alignment.Center
                        ) {
                            CharacterVerticalCard(
                                modifier = Modifier
                                    .draggableStack(
                                        controller = cardStackController,
                                        velocityThreshold = 80.dp
                                    )
                                    .moveTo(
                                        x = cardStackController.offsetX.value,
                                        y = cardStackController.offsetY.value
                                    )
                                    .visible(visibleCardState)
                                    .graphicsLayer(
                                        rotationZ = cardStackController.rotation.value,
                                        //scaleX = cardStackController.scale.value,
                                        //scaleY = cardStackController.scale.value
                                    ),
                                onClick = {},
                                characterModel = state.currentPair.first!!
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(220.dp),
                            contentAlignment = Alignment.Center

                        ) {
                            CharacterVerticalCard(
                                modifier = Modifier
                                    .draggableStack(
                                        controller = cardStackController2,
                                        velocityThreshold = 80.dp
                                    )
                                    .visible(visibleCardState2)
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
                                characterModel = state.currentPair.second!!
                            )
                        }
                    }
                }
                is UiStatus.Loading ->{
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
                                .width(220.dp),

                            contentAlignment = Alignment.Center
                        ) {
                            ShimmeredCharacterVerticalCard(
                                modifier = Modifier
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(220.dp),
                            contentAlignment = Alignment.Center

                        ) {
                            ShimmeredCharacterVerticalCard(
                                modifier = Modifier

                            )
                        }
                    }
                }

                is UiStatus.Failed -> TODO()
                UiStatus.ScrollLoading -> TODO()
            }

            when(val gameStatus = state.gameStatus){
                is GameStatus.ProccessStatus ->{
                    visibleCardState = true
                    visibleCardState2 = true

                }
                is GameStatus.ChangeCharactersStatus -> {
                    shouldBlink = false
                }
                is GameStatus.ShowStatus -> {
                    when(gameStatus.winloseStatus){
                        WINLOSEStatus.WIN ->{
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .blinkBackground(shouldBlink, blinkColor = colorResource(R.color.greenWin)),

                            )
                            shouldBlink = true
                        }
                        WINLOSEStatus.LOSE -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .blinkBackground(shouldBlink, blinkColor = colorResource(R.color.redLose)),

                            )
                            shouldBlink = true

                        }
                        WINLOSEStatus.NONE -> {}
                    }
                }
            }
        }
    )
}