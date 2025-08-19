package ru.mrapple100.rickmorty.ui.components.organism

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.rickmorty.R
import ru.mrapple100.rickmorty.ui.pages.characterdetails.characterDetailBoundsTransform
import ru.mrapple100.rickmorty.ui.pages.characterdetails.nonSpatialExpressiveSpring

@Composable
fun CharacterVerticalCard(
    characterModel: CharacterCardModel,
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
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {

                AsyncImage(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                        .size(200.dp),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(characterModel.imageStr)
                        .crossfade(true)
                        .diskCacheKey("image-${characterModel.id}")
                        .memoryCacheKey("image-${characterModel.id}")
                        .placeholderMemoryCacheKey("image-${characterModel.id}")
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
                )

            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.5f),
                    textAlign = TextAlign.End,
                    text = characterModel.name,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis

                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = " | ",
                    style = MaterialTheme.typography.bodySmall,

                    )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = characterModel.species,
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis

                )
            }
        }
    }
}