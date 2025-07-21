package ru.mrapple100.rickmorty.ui.pages.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.mrapple100.domain.character.repository.CharacterRepository
import ru.mrapple100.domain.character.usecases.SearchCharacterByNameUseCase
import ru.mrapple100.rickmorty.ui.common.UiStatus
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val searchCharacterByNameUseCase: SearchCharacterByNameUseCase
):ContainerHost<CharacterListState,CharacterListSideEffect>,ViewModel() {


    private var searchJob: Job? = null

    override val container = container<CharacterListState, CharacterListSideEffect>(
        CharacterListState()
    )


    init {
        intent {
            searchPokemon(state.searchText)
        }
    }

    fun searchPokemon(searchText: String) {
        intent {
            searchJob?.cancel()
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                reduce {
                    state.copy(
                        status = UiStatus.Loading,
                        searchText = searchText,
                        detailsList = emptyList()
                    )
                }

                searchCharacterByNameUseCase(state.searchText).collect { details ->
                    delay(1000)

                    if (details.isNotEmpty()) {
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
                                detailsList = details
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