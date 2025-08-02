package ru.mrapple100.rickmorty.ui.components.organism

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.model.CharacterModel

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterTwoCard(
    one: CharacterCardModel? = null,
    onClickedOne: (() -> Unit)? = null,
    two: CharacterCardModel? = null,
    onClickedTwo: (() -> Unit)? = null,
    modifier: Modifier = Modifier,

) {
    Row(modifier = modifier) {
        if (one != null) {
                CharacterCard(
                    characterModel = one,
                    onClick = { onClickedOne?.invoke() },
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fraction = 0.5f)
                        .padding(end = 4.dp)

                )

        }

        if (two != null) {
                CharacterCard(
                    characterModel = two,
                    onClick = { onClickedTwo?.invoke() },
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(start = 4.dp),


                )
        }
    }
}

@Composable
fun ShimmeredCharacterTwoCard(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {

            ShimmeredCharacterCard(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = 0.5f)
                    .padding(end = 4.dp)
            )

            ShimmeredCharacterCard(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            )

    }
}


