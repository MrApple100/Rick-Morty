package ru.mrapple100.rickmorty.ui.pages.characterlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.rickmorty.ui.common.UiStatus
import ru.mrapple100.rickmorty.ui.components.molecules.ErrorMessage
import ru.mrapple100.rickmorty.ui.components.molecules.LoadingIndicator
import ru.mrapple100.rickmorty.ui.components.molecules.SearchBar
import ru.mrapple100.rickmorty.ui.components.molecules.TopBar
import ru.mrapple100.rickmorty.ui.components.organism.CharacterTwoCard


@Composable
fun CharacterListPage(
    state: CharacterListState,
    onShowDetail: (id: Int) -> Unit,
    onSearchPokemon: (keyword: String) -> Unit
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        SearchBar(
                            searchText = state.searchText,
                            onChangedSearchText = { onSearchPokemon(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }

                    if (state.status == UiStatus.Success) {
                        setupTwoGrid(state.detailsList) { one, two ->
                            CharacterTwoCard(
                                one = one,
                                onClickedOne = { one?.let { onShowDetail(it.id) } },
                                two = two,
                                onClickedTwo = { two?.let { onShowDetail(it.id) } },
                                modifier = Modifier
                                    .height(150.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .padding(bottom = 8.dp)
                            )
                        }
                    }
                }

                when (val status = state.status) {
                    UiStatus.Loading -> {
                        LoadingIndicator(modifier = Modifier.fillMaxSize())
                    }
                    is UiStatus.Failed -> {
                        ErrorMessage(
                            message = status.message,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    else -> Unit
                }
            }
        }
    )
}

private fun LazyListScope.setupTwoGrid(
    entities: List<CharacterModel>,
    row: @Composable (one: CharacterModel?, two: CharacterModel?) -> Unit
) {
    val rowData = if (entities.count() <= 2) {
        listOf(entities)
    } else {
        entities.windowed(size = 2, step = 2)
    }

    rowData.forEach { column ->
        item { row(column.getOrNull(0), column.getOrNull(1)) }
    }
}