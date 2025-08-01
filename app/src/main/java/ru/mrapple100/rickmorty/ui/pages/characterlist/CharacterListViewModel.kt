package ru.mrapple100.rickmorty.ui.pages.characterlist

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import ru.mrapple100.domain.character.usecases.ScrollDownPageUseCase
import ru.mrapple100.domain.character.usecases.ScrollUpPageUseCase
import ru.mrapple100.domain.character.usecases.SearchCharacterByNameUseCase
import ru.mrapple100.domain.character.usecases.UpdateCharacterListPageUseCase
import ru.mrapple100.rickmorty.ui.common.UiStatus
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val searchCharacterByNameUseCase: SearchCharacterByNameUseCase,
    private val updateCharacterListPageUseCase: UpdateCharacterListPageUseCase,
    private val scrollDownPageUseCase: ScrollDownPageUseCase,
    val savedStateHandle: SavedStateHandle

    ):ContainerHost<CharacterCardListState,CharacterListSideEffect>,ViewModel() {


    private var searchJob: Job? = null
    private var scrollJob: Job? = null
    private var refreshJob: Job? = null

    override val container = container<CharacterCardListState, CharacterListSideEffect>(
        CharacterCardListState()
    )


    init {
        intent {
            searchCharacter(state.searchText)
        }
    }

    fun refreshCharacterPage(){
        intent {
            refreshJob?.cancel()
            refreshJob = viewModelScope.launch(Dispatchers.IO) {
                reduce {
                    state.copy(
                        status = UiStatus.Loading,
                        detailsList = emptyList()
                    )
                }
                updateCharacterListPageUseCase()
                searchCharacter(state.searchText)
            }
        }
    }

    fun searchCharacter(searchText: String) {
        intent {
            searchJob?.cancel()
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                reduce {
                    state.copy(
                        status = UiStatus.Loading,
                        searchText = searchText,
                        detailsList = state.detailsList
                    )
                }

                searchCharacterByNameUseCase(state.searchText).collect { details ->
                    delay(1000)

                    if (!details.isNullOrEmpty()) {
                        reduce {
                            state.copy(
                                status = UiStatus.Success,
                                detailsList = details
                            )
                        }
                    } else {
                        reduce {
                            state.copy(
                                status = UiStatus.Failed("Not Found"),
                                detailsList = emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

    fun scrollDown(){
        intent {
            scrollJob = viewModelScope.launch(Dispatchers.IO) {
                reduce {
                    state.copy(
                        status = UiStatus.ScrollLoading,
                        detailsList = state.detailsList
                    )
                }
                scrollDownPageUseCase().collect{ details ->
                    delay(1000)
                    if (!details.isNullOrEmpty()) {
                        val detailsFilt = details.filter{ ch -> ch.name.contains(state.searchText)}
                        reduce {
                            state.copy(
                                status = UiStatus.Success,
                                detailsList = state.detailsList + detailsFilt
                            )
                        }
                    }else{
                        reduce {
                            state.copy(
                                status = UiStatus.Success,
                                detailsList = state.detailsList
                            )
                        }
                    }
                }
            }
        }
    }

    fun showDetails(id: Int) {
        intent {
            postSideEffect(CharacterListSideEffect.ShowDetails(id))
        }
    }
}