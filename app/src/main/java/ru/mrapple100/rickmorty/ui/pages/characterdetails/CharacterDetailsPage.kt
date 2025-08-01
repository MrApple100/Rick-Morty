package ru.mrapple100.rickmorty.ui.pages.characterdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.model.Status
import ru.mrapple100.rickmorty.R
import ru.mrapple100.rickmorty.ui.components.molecules.TopBar

@Composable
fun CharacterDetailsPage(
    state: CharacterState,
    onBackNavigate: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(shape = RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp)),

                    ) {

                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(state.detailsCharacter.imageStr)
                            .crossfade(true)
                            .memoryCacheKey(state.detailsCharacter.name + state.detailsCharacter.species)
                            .placeholderMemoryCacheKey(state.detailsCharacter.name + state.detailsCharacter.species)
                            .build(),
                        onLoading = {},
                        onSuccess = {},
                        onError = {},
                        contentDescription = null,
                    )


                }
                CharacterDetailsContent(state.detailsCharacter)
            }
        }
    )
}

@Composable
fun CharacterDetailsContent(
    characterDetails: CharacterModel
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CharacterTypesSection(species = characterDetails.species,lifeStatus = characterDetails.status)
        CharacterDetailsRow(characterDetails = characterDetails)
        CharacterEpisodsSection(episods = characterDetails.episode)
    }
}

@Composable
fun CharacterTypesSection(
    species: String,
    lifeStatus: Status
) {
    Row(
        modifier = Modifier
            .padding(start = 20.dp)
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
                    .width(96.dp)
                    .height(30.dp)
                   // .background(color = parseTypeToColor(lifeStatus), shape = CircleShape)
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {
                    Text(
                        text = lifeStatus.name.uppercase() +" - " + species.uppercase(),
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
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
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

    }
}

@Composable
fun CharacterEpisodsSection(
    episods: List<String>
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 19.dp, vertical = 16.dp)
    ) {
//        val description = flavorText.replace("\n", " ")
//
//        Text(
//            text = description,
//            color = Color(0xFF7E7E7E),
//            fontWeight = FontWeight.Normal,
//            fontSize = 16.sp
//        )

        Icon(
            imageVector = Icons.Outlined.KeyboardArrowDown,
            contentDescription = null,
            tint = Color(0xEEA5A5A5),
            modifier = Modifier
                .size(33.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

private fun addLineBreakToCategory(category: String): String {
    val words = category.split(" ")
    val wordCount = words.size
    return if (wordCount <= 2) {
        category.replaceFirst(" ", "\n")
    } else {
        val index = category.lastIndexOf(" ")
        category.substring(0, index) + "\n" + category.substring(index + 1)
    }
}
