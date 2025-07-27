package ru.mrapple100.rickmorty.ui.pages.characterlist

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.rickmorty.ui.common.UiStatus
import ru.mrapple100.rickmorty.ui.components.molecules.ErrorMessage
import ru.mrapple100.rickmorty.ui.components.molecules.LoadingIndicator
import ru.mrapple100.rickmorty.ui.components.molecules.SearchBar
import ru.mrapple100.rickmorty.ui.components.molecules.TopBar
import ru.mrapple100.rickmorty.ui.components.organism.CharacterTwoCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListPage(
    state: CharacterCardListState,
    onShowDetail: (id: Int) -> Unit,
    onSearchCharacter: (keyword: String) -> Unit,
    onScrollDown: () -> Unit,
    onRefresh:() -> Unit

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
                val listState = rememberLazyListState()
                val isFocused by listState.interactionSource.interactions
                    .filterIsInstance<DragInteraction>()
                    .map { dragInteraction ->
                        dragInteraction is DragInteraction.Stop
                    }
                    .collectAsState(false)
                LaunchedEffect(isFocused) {
                    if (state.status == UiStatus.Success && listState.isScrolledToTheEnd()) {
                        onScrollDown()
                        Log.d("SCROLLDOWN", "SCROLLDOWN")
                    }
                }
                PullToRefreshBox (
                    isRefreshing = state.status==UiStatus.Loading,
                    onRefresh = {
                        onRefresh()
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                    indicator = {}
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = listState
                    ) {
                        item {
                            SearchBar(
                                searchText = state.searchText,
                                onChangedSearchText = { onSearchCharacter(it) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }


                        if (state.status == UiStatus.Success || state.status == UiStatus.ScrollLoading) {
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

                        is UiStatus.ScrollLoading -> {
                            LoadingIndicator(modifier = Modifier.fillMaxWidth())
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
        }
    )
}

private fun LazyListScope.setupTwoGrid(
    entities: List<CharacterCardModel>,
    row: @Composable (one: CharacterCardModel?, two: CharacterCardModel?) -> Unit
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
fun LazyListState.isScrolledToTheEnd() =
    if(layoutInfo.totalItemsCount<20)
        true
    else layoutInfo.visibleItemsInfo.find { it -> it.index == layoutInfo.totalItemsCount - 20}!=null
