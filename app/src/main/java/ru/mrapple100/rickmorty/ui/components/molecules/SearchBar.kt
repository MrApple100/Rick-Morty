package ru.mrapple100.rickmorty.ui.components.molecules

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mrapple100.rickmorty.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onChangedSearchText: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    Box(modifier = modifier) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onChangedSearchText(it)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.outline,
                focusedIndicatorColor = MaterialTheme.colorScheme.outline
            ),
            placeholder = { Text(stringResource(R.string.search)) }
        )
    }
}

@Preview
@Composable
fun SearchBar_Preview() {
    SearchBar(
        //searchText = "SEARCH TEXT",
        onChangedSearchText = {},
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}