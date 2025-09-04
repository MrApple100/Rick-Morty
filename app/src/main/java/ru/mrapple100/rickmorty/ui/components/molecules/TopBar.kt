package ru.mrapple100.rickmorty.ui.components.molecules

import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import ru.mrapple100.rickmorty.R
import ru.mrapple100.rickmorty.ui.theme.RickAndMortyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    content: ((@Composable () -> Unit)?),
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                style = MaterialTheme.typography.headlineMedium,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            content?.invoke()
        },

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TopBar_Preview(){
    RickAndMortyTheme {
        TopBar(content = null)
    }
}
