package ru.mrapple100.rickmorty.ui.pages.characterlist

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.rickmorty.LocalAnimatedVisibilityScope
import ru.mrapple100.rickmorty.LocalSharedTransitionScope
import ru.mrapple100.rickmorty.ui.common.UiStatus
import ru.mrapple100.rickmorty.ui.components.molecules.ErrorMessage
import ru.mrapple100.rickmorty.ui.components.molecules.LoadingIndicator
import ru.mrapple100.rickmorty.ui.components.molecules.SearchBar
import ru.mrapple100.rickmorty.ui.components.molecules.TopBar
import ru.mrapple100.rickmorty.ui.components.organism.CharacterTwoCard
import ru.mrapple100.rickmorty.ui.components.organism.ShimmeredCharacterTwoCard


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterListPage(
    state: CharacterCardListState,
    onShowDetail: (id: Int) -> Unit,
    onSearchCharacter: (keyword: String) -> Unit,
    onScrollDown: () -> Unit,
    onRefresh:() -> Unit

) {
    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: throw IllegalStateException("No Scope found")
    val animatedContentScope = LocalAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No Scope found")
    Scaffold(
        topBar = {
            with(sharedTransitionScope) {
                TopBar(
                    content = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .sharedElement(
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(key = "topBar"),
                            animatedVisibilityScope = animatedContentScope,
                            zIndexInOverlay = 5f
                        )
                        .sharedBounds(
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(key = "topBar"),
                            animatedVisibilityScope = animatedContentScope,
                            zIndexInOverlay = 5f

                        )
                )
            }
        },
        content = { innerPading ->
            with(sharedTransitionScope) {

                Box(
                    modifier = Modifier
                        .padding(innerPading)
                        .fillMaxSize()

                ) {
                    val listState = rememberLazyListState()
                    val isFocused by listState.interactionSource.interactions
                        .filterIsInstance<DragInteraction>()
                        .map { dragInteraction ->
                            dragInteraction is DragInteraction.Stop
                        }
                        .collectAsState(false)
                    var isScrolling by remember { mutableStateOf(false) }
                    var scrollSharedKey by remember { mutableStateOf<String>( "")}
                    LaunchedEffect(listState.isScrollInProgress) {
                        isScrolling = listState.isScrollInProgress
                        scrollSharedKey = if(isScrolling){
                           "cancel"
                        }else{
                            ""
                        }
                    }
                    LaunchedEffect(isFocused) {
                        if (state.status == UiStatus.Success && listState.isScrolledToTheEnd()) {
                            onScrollDown()
                        }
                    }
                    PullToRefreshBox(
                        isRefreshing = state.status == UiStatus.Loading,
                        onRefresh = {
                            onRefresh()
                        },
                        modifier = Modifier
                            .fillMaxSize()
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

                            when (state.status) {
                                is UiStatus.Success -> {
                                    setupTwoGrid(state.detailsList) { one, two ->
                                        CharacterTwoCard(

                                            one = one?.apply { sharedKey =  scrollSharedKey },
                                            onClickedOne = { one?.let { onShowDetail(it.id) } },
                                            two = two?.apply { sharedKey =  scrollSharedKey },
                                            onClickedTwo = { two?.let { onShowDetail(it.id) } },
                                            modifier = Modifier
                                                .height(150.dp)
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp)
                                                .padding(bottom = 8.dp)
                                        )
                                    }
                                }
                                is UiStatus.ScrollLoading -> {
                                    setupTwoGrid(state.detailsList) { one, two ->
                                        CharacterTwoCard(

                                            one = one?.apply { sharedKey =  scrollSharedKey },
                                            onClickedOne = { one?.let { onShowDetail(it.id) } },
                                            two = two?.apply { sharedKey =  scrollSharedKey },
                                            onClickedTwo = { two?.let { onShowDetail(it.id) } },
                                            modifier = Modifier
                                                .height(150.dp)
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp)
                                                .padding(bottom = 8.dp)
                                        )
                                    }
                                    item{
                                        LoadingIndicator(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(50.dp)
                                        )
                                    }
                                }

                                is UiStatus.Loading -> {
                                    items(
                                        count = state.detailsList.size.coerceIn(5, 10)
                                    ) {
                                        ShimmeredCharacterTwoCard(
                                            modifier = Modifier
                                                .height(150.dp)
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp)
                                                .padding(bottom = 8.dp)
                                        )
                                    }
                                }

                                is UiStatus.Failed -> {}
                            }
                        }

                        when (val status = state.status) {
                            is UiStatus.Loading,
                            is UiStatus.ScrollLoading -> {}

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
    if (layoutInfo.totalItemsCount < 20)
        true
    else {
        val visibleSize = layoutInfo.visibleItemsInfo.size
        layoutInfo.visibleItemsInfo.find { it -> it.index == layoutInfo.totalItemsCount - visibleSize } != null
    }

