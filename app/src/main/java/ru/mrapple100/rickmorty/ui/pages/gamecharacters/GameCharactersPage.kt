package ru.mrapple100.rickmorty.ui.pages.gamecharacters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.mrapple100.rickmorty.ui.components.organism.CharacterVerticalCard

@Composable
fun GameCharactersPage(
    state: GameCharactersState,
    postChangeCharacter: () -> Unit,
    postShowWinStatus: () -> Unit,
    postShowLoseStatus: () -> Unit
){
    Scaffold(
        topBar = {},
        content = { it ->
            Column(
                modifier = Modifier
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                    .fillMaxHeight(fraction = 0.5f)
                    .fillMaxWidth()
                ){
                    CharacterVerticalCard(
                        modifier = Modifier,
                        onClick = {},
                        characterModel = state.pair.first!!
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ){
                    CharacterVerticalCard(
                        modifier = Modifier,
                        onClick = {},
                        characterModel = state.pair.first!!
                    )
                }
            }
        }
    )
}