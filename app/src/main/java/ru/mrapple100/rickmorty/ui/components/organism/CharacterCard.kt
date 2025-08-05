package ru.mrapple100.rickmorty.ui.components.organism

import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.rickmorty.LocalAnimatedVisibilityScope
import ru.mrapple100.rickmorty.LocalSharedTransitionScope
import ru.mrapple100.rickmorty.R
import ru.mrapple100.rickmorty.ui.components.shimmer.ShimmerBox
import ru.mrapple100.rickmorty.ui.components.shimmer.ShimmerThemes
import ru.mrapple100.rickmorty.ui.pages.characterdetails.characterDetailBoundsTransform
import ru.mrapple100.rickmorty.ui.pages.characterdetails.nonSpatialExpressiveSpring
import ru.mrapple100.rickmorty.ui.pages.characterdetails.spatialExpressiveSpring


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@ExperimentalAnimationApi
@Composable
fun CharacterCard(
    characterModel: CharacterCardModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
    ) {
    val sharedTransitionScope =
        LocalSharedTransitionScope.current
            ?: throw IllegalStateException("No Scope found")
    val animatedContentScope = LocalAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No Scope found")

    with(sharedTransitionScope) {
        val roundedCornerAnimation by animatedContentScope.transition
            .animateDp(label = "rounded corner") { enterExit: EnterExitState ->
                when (enterExit) {
                    EnterExitState.PreEnter -> 0.dp
                    EnterExitState.Visible -> 10.dp
                    EnterExitState.PostExit -> 10.dp
                }
            }

        Card(
            modifier = modifier
                .sharedBounds(
                    rememberSharedContentState(key = "container + ${characterModel.id}${characterModel.sharedKey}"),
                    animatedVisibilityScope = animatedContentScope,
                    exit = fadeOut(nonSpatialExpressiveSpring()),
                    enter = fadeIn(nonSpatialExpressiveSpring()),
                    boundsTransform = characterDetailBoundsTransform,

                )
            ,
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

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {

                    AsyncImage(
                        modifier = Modifier
                            .sharedElement(
                                sharedContentState = sharedTransitionScope.rememberSharedContentState(
                                    key = "image-${characterModel.id}${characterModel.sharedKey}",

                                ),
                                animatedVisibilityScope = animatedContentScope,
                                boundsTransform = characterDetailBoundsTransform,


                            )
                            .clip(shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                            .size(100.dp)
                            ,
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
}

@Composable
fun ShimmeredCharacterCard(
    modifier: Modifier = Modifier
) {
    val defaultShimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.Window,
        theme = ShimmerThemes.defaultShimmerTheme
    )
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .padding(8.dp)
        ) {
            ShimmerBox(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally),
                shimmer = defaultShimmer

            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .height(20.dp),
                    shimmer = defaultShimmer
                )
                Spacer(modifier = Modifier.width(8.dp))
                ShimmerBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp),
                    shimmer = defaultShimmer
                )
            }
        }
    }
}


@Preview
@Composable
fun ShimmeredCharacterCardPreview(){
    ShimmeredCharacterCard(
        modifier = Modifier
    )
}
