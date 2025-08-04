package ru.mrapple100.rickmorty.ui.pages.characterdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.usecases.LoadCharacterDetailsUseCase
import ru.mrapple100.domain.character.usecases.ScrollDownPageUseCase
import ru.mrapple100.domain.character.usecases.SearchCharacterByNameUseCase
import ru.mrapple100.domain.character.usecases.UpdateCharacterListPageUseCase
import ru.mrapple100.rickmorty.ui.common.UiStatus
import ru.mrapple100.rickmorty.ui.pages.characterlist.CharacterCardListState
import ru.mrapple100.rickmorty.ui.pages.characterlist.CharacterListSideEffect
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(

    private val loadCharacterDetailsUseCase: LoadCharacterDetailsUseCase,
    val savedStateHandle: SavedStateHandle

): ContainerHost<CharacterState, CharacterSideEffect>, ViewModel() {


    override val container = container<CharacterState, CharacterSideEffect>(
        CharacterState()
    )

    private var loadJob : Job? = null

    init {
        intent {
            savedStateHandle.get<Int>("characterId")?.let { characterId ->
                fetchCharacterById(characterId)
            }
        }
    }


    fun fetchCharacterById(id: Int) {
        intent {
            //loadJob?.cancel()
            loadJob = viewModelScope.launch(Dispatchers.IO) {

                loadCharacterDetailsUseCase(id).collect{details ->
                        if (details != null) {
                            reduce {
                                state.copy(
                                    status = UiStatus.Success,
                                    detailsCharacter = details
                                )
                            }
                        }else{
                            delay(2000)
                            reduce {
                                state.copy(
                                    status = UiStatus.Failed("no no no mr fish"),
                                    detailsCharacter = CharacterModel()
                                )
                            }
                        }

                }

            }
        }
    }
    fun backNavigate() {
        intent {
            postSideEffect(CharacterSideEffect.backToStack())
        }
    }

}