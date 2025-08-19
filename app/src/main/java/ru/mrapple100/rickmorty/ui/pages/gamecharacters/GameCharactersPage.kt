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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.mrapple100.rickmorty.ui.components.organism.CharacterVerticalCard
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

    val cardStackController = rememberCardStackController()
    val cardStackController2 = rememberCardStackController()
    cardStackController.onSwipe = {
        Log.d("ChangeChange","change")
        postShowStatus(ChooseUser.CardFirst)
    }

    cardStackController2.onSwipe = {
        postShowStatus(ChooseUser.CardSecond)
    }

    Scaffold(
        topBar = {
            Text(
                "Кто появился раньше в сериале Rick And Morty?"
            )
        },
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
                        .width(220.dp)
                    ,

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
                        .width(220.dp)
                        ,
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

            when(val gameStatus = state.gameStatus){
                is GameStatus.ProccessStatus ->{}
                is GameStatus.ChangeCharactersStatus -> {}
                is GameStatus.ShowStatus -> {
                    when(gameStatus.winloseStatus){
                        WINLOSEStatus.WIN ->{
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center

                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .zIndex(5f)
                                        .background(Color.Green),
                                ) {
                                    Text("WIN")
                                }
                            }

                        }
                        WINLOSEStatus.LOSE -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center

                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .zIndex(5f)
                                        .background(Color.Red),

                                ) {
                                    Text("Lose")
                                }
                            }
                        }
                        WINLOSEStatus.NONE -> {}
                    }
                }
            }
        }
    )
}