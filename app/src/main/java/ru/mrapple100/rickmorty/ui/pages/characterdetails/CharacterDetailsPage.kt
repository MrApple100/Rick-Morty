package ru.mrapple100.rickmorty.ui.pages.characterdetails

import android.annotation.SuppressLint
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.model.Gender
import ru.mrapple100.domain.character.model.Status
import ru.mrapple100.rickmorty.LocalAnimatedVisibilityScope
import ru.mrapple100.rickmorty.LocalSharedTransitionScope
import ru.mrapple100.rickmorty.R
import ru.mrapple100.rickmorty.ui.components.firebase_analytics.BlockFirebaseAnalytics
import ru.mrapple100.rickmorty.ui.components.molecules.TopBar
import ru.mrapple100.rickmorty.ui.utils.calcDominantColor
import java.util.Locale

fun <T> spatialExpressiveSpring() = spring<T>(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessMedium,
)
fun <T> nonSpatialExpressiveSpring() = tween<T>(
    durationMillis = 200,
    delayMillis = 0,
    easing = LinearOutSlowInEasing

)

@OptIn(ExperimentalSharedTransitionApi::class)
val characterDetailBoundsTransform = BoundsTransform { _, _ ->

    nonSpatialExpressiveSpring()
}

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsPage(
    state: CharacterState,
    onBackNavigate: () -> Unit
) {
    BlockFirebaseAnalytics("CharacterDetailsPage ${state.detailsCharacter.id} - ${state.detailsCharacter.name}")


    val darkLightSheme = isSystemInDarkTheme()
    val backgroundDefault = MaterialTheme.colorScheme.background
    var backgroundColor by remember { mutableStateOf(backgroundDefault) }

    val sharedTransitionScope = LocalSharedTransitionScope.current!!
    val animatedContentScope = LocalAnimatedVisibilityScope.current!!

    with(sharedTransitionScope) {

        Scaffold(
            modifier = Modifier
                .sharedBounds(
                    sharedContentState = sharedTransitionScope.rememberSharedContentState(
                        key = "container + ${state.detailsCharacter.id}"
                    ),
                    animatedVisibilityScope = animatedContentScope,
                    exit = fadeOut(nonSpatialExpressiveSpring()),
                    enter = fadeIn(nonSpatialExpressiveSpring()),
                    boundsTransform = characterDetailBoundsTransform,

                ),
            topBar = {
                TopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            modifier = Modifier.clickable { onBackNavigate() },
                            contentDescription = ""
                        )
                    })
            },
            content = {
                Column(
                    modifier = Modifier
                        .background(backgroundColor)
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = state.detailsCharacter.name.replaceFirstChar { char ->
                            if (char.isLowerCase()) char.titlecase(Locale.ROOT) else char.toString()
                        },
                        textAlign = TextAlign.Center,

                        fontWeight = FontWeight.Medium,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.height(15.dp))


                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(top = 81.dp)
                                .fillMaxSize()
                                .zIndex(-1f),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.defaultrickmorty),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(top = 54.dp)
                                    .fillMaxWidth()
                                    .height(400.dp)
                                    .alpha(0.3f)

                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(top = 0.dp)
                                .fillMaxWidth()
                                .zIndex(3f),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            SubcomposeAsyncImage(
                                modifier = Modifier
                                    .size(200.dp)
                                    .sharedElement(
                                        sharedTransitionScope.rememberSharedContentState(key = "image-${state.detailsCharacter.id}"),
                                        animatedVisibilityScope = animatedContentScope,

                                        // placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize
                                    )
                                    .clip(shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(state.detailsCharacter.imageStr)
                                    .crossfade(true)
                                    .diskCacheKey("image-${state.detailsCharacter.id}")
                                    .memoryCacheKey("image-${state.detailsCharacter.id}")
                                    .placeholderMemoryCacheKey("image-${state.detailsCharacter.id}")
                                    .build(),
                                onLoading = {},
                                onSuccess = { success ->
                                    calcDominantColor(success.result.drawable) { lightC, darkC ->
                                        backgroundColor = if (darkLightSheme) {
                                            darkC
                                        } else {
                                            lightC
                                        }
                                    }
                                },
                                onError = {},
                                contentDescription = null,
                            )


                        }
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .padding(top = 182.dp)
                                .matchParentSize()
                                .background(
                                    MaterialTheme.colorScheme.secondary,
                                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                                )
                                .zIndex(0f)
                        ) {
                            CharacterDetailsContent(state.detailsCharacter)
                        }
                    }
                }

            }

        )
    }
}

@Composable
fun CharacterDetailsContent(
    characterDetails: CharacterModel
) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CharacterTypesSection(
                species = characterDetails.species,
                lifeStatus = characterDetails.status
            )
            CharacterDetailsRow(characterDetails = characterDetails)
        }
}

@Composable
fun CharacterTypesSection(
    species: String,
    lifeStatus: Status
) {
    Row(
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
            .height(32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 11.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .background(color = statusColor(lifeStatus), shape = CircleShape)
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp,0.dp),
                        text = lifeStatus.name.uppercase() +" - " + species.uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis
                    )
            }
    }
}

@Composable
private fun CharacterDetailsRow(
    characterDetails: CharacterModel
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 20.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                SectionTitle("General Info")
                GenderSection(characterDetails.gender)
                Spacer(modifier = Modifier.height(5.dp))
                InfoCard(characterDetails)
            }
            item {
                SectionTitle("Location")
                LocationCard(characterDetails)
            }
            item {
                SectionTitle("Episodes (${characterDetails.episode.size})")
                CharacterEpisodsSection(characterDetails.episode)
            }
        }

    }
}

@Composable
fun GenderSection(gender: Gender, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = genderColor(gender).copy(alpha = 0.15f)
        ),
        border = BorderStroke(1.dp, genderColor(gender))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(genderColor(gender).copy(alpha = 0.2f))
                        .border(2.dp, genderColor(gender), CircleShape),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        painter = genderIcon(gender),
                        contentDescription = null,
                        tint = genderColor(gender),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "Gender",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = gender.toString().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = genderColor(gender)
                    )
                }
            }

            Text(
                text = genderSymbol(gender),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = genderColor(gender).copy(alpha = 0.1f),
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun genderColor(gender: Gender): Color {
    return when (gender) {
        Gender.MALE -> Color(0xFF2196F3)
        Gender.FEMALE -> Color(0xFFE91E63)
        Gender.GENDERLESS -> Color(0xFF9C27B0)
        Gender.UNKNOWN -> Color(0xFF9E9E9E)
    }
}

@Composable
fun genderIcon(gender: Gender): Painter {
    return when (gender) {
        Gender.MALE -> painterResource(id =  R.drawable.outline_male_24)
        Gender.FEMALE -> painterResource(id = R.drawable.baseline_female_24)
        Gender.GENDERLESS ->  painterResource(id = R.drawable.defaultrickmorty)
        Gender.UNKNOWN -> painterResource(id = R.drawable.defaultrickmorty)
    }
}

fun genderSymbol(gender: Gender): String {
    return when (gender) {
        Gender.MALE -> "♂"
        Gender.FEMALE -> "♀"
        Gender.GENDERLESS -> "?"
        Gender.UNKNOWN -> "?"
    }
}
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    )
}

@Composable
fun InfoCard(character: CharacterModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoRow(Icons.Default.Info, "Type", character.type.takeIf { it != "" } ?: "Unknown")
        }
    }
}
@Composable
fun LocationCard(character: CharacterModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoRow(Icons.Default.Home, "Origin", character.origin.name.takeIf { it != "no home location" } ?: "Unknown")
            InfoRow(Icons.Default.Place, "Location", character.location.name.takeIf { it != "no location" } ?: "Unknown")
        }
    }
}

@Composable
fun CharacterEpisodsSection(
    episodes: List<String>
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 19.dp, vertical = 16.dp)
    ) {
        EpisodeGrid(episodes)
    }
}
@Composable
fun EpisodeGrid(episodes: List<String>, modifier: Modifier = Modifier) {
    val extractedNumbers = remember(episodes) {
        episodes.map { url ->
            try {
                url.substringAfterLast("/").toInt()
            } catch (e: Exception) {
                -1
            }
        }
    }

    Column(modifier = modifier) {

        if (extractedNumbers.isEmpty() || extractedNumbers.all { it == -1 }) {
            Text(
                text = "No episodes available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        } else {
            BoxWithConstraints (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                val spacing = 8.dp
                val chipMinWidth = 80.dp

                val columns = maxOf(1, (maxWidth / (chipMinWidth + spacing)).toInt())

                val rows = extractedNumbers.chunked(columns)

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    rows.forEach { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(spacing),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = spacing)
                        ) {
                            for (i in 0 until columns) {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(40.dp)
                                ) {
                                    if (i < rowItems.size) {
                                        val number = rowItems[i]
                                        if (number != -1) {
                                            EpisodeChip(number = number)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodeChip(number: Int) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = "#$number",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.8f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}



@Composable
fun statusColor(status: Status): Color {
    return when (status) {
        Status.ALIVE -> Color(0xFF55CC44)
        Status.DEAD -> Color(0xFFD63D2E)
        Status.UNKNOWN -> Color(0xFF9E9E9E)
    }
}