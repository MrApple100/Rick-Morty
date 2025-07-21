package ru.mrapple100.rickmorty.ui.components.organism

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.rickmorty.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCard(
    characterModel: CharacterModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(characterModel.imageStr)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(
                    if (isSystemInDarkTheme()) {
                        R.drawable.ic_launcher_background
                    } else {
                        R.drawable.ic_launcher_foreground
                    }
                ),
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = characterModel.name,
                    style = MaterialTheme.typography.bodySmall,

                )
                Text(
                    text = " | ",
                    style = MaterialTheme.typography.bodySmall,

                    )
                Text(
                    text = characterModel.species,
                    style = MaterialTheme.typography.bodySmall,

                )
            }
        }
    }
}

