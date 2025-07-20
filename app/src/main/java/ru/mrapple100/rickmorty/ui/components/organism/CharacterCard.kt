package ru.mrapple100.rickmorty.ui.components.organism

import android.graphics.Color
import org.w3c.dom.Text
import ru.mrapple100.rickmorty.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CharacterCard(
    pokemonDetails: PokemonDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
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
                    .data(pokemonDetails.image.localUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(
                    if (isSystemInDarkTheme()) {
                        R.drawable.ic_question_white
                    } else {
                        R.drawable.ic_question_black
                    }
                ),
                error = painterResource(R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = pokemonDetails.pokemon.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
private fun Preview_PokemonCard() {
    PokemonCard(
        pokemonDetails = SAMPLE_POKEMON_DETAILS,
        onClick = {},
        modifier = Modifier
            .size(150.dp)
            .background(Color.Black)
    )
}