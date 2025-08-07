package ru.mrapple100.rickmorty.ui.pages.gamecharacters

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.usecases.LoadRandomTwoCharacterUseCase
import ru.mrapple100.rickmorty.ui.common.UiStatus
import ru.mrapple100.rickmorty.ui.pages.characterdetails.CharacterSideEffect
import ru.mrapple100.rickmorty.ui.pages.characterdetails.CharacterState
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.GameStatus
import javax.inject.Inject
import kotlin.random.Random

class GameCharactersViewModel @Inject constructor(
    val loadRandomTwoCharacterUseCase: LoadRandomTwoCharacterUseCase

): ContainerHost<GameCharactersState, GameCharactersSideEffect>, ViewModel() {


    override val container = container<GameCharactersState, GameCharactersSideEffect>(
        GameCharactersState()
    )

    init {
        intent {
            changeCharacters()
        }
    }

    fun showWinStatus() {
        intent {
            reduce {
                state.copy(
                    gameStatus = GameStatus.WinStatus,
                )
            }
        }
    }

    fun showLoseStatus() {
        intent {
            reduce {
                state.copy(
                    gameStatus = GameStatus.LoseStatus
                )
            }
        }
    }

    fun changeCharacters() {
        intent {
            reduce {
                state.copy(
                    gameStatus = GameStatus.ChangeCharactersStatus
                )
            }
            loadRandomTwoCharacterUseCase().collect{ chs ->
                if(chs.first != null) {
                    reduce {
                        state.copy(
                            pair = chs,
                            gameStatus = GameStatus.ProccessStatus,
                            status = UiStatus.Success
                        )
                    }
                }else{
                    reduce {
                        state.copy(
                            pair = Pair(CharacterCardModel(), CharacterCardModel()),
                            gameStatus = GameStatus.ProccessStatus,
                            status = UiStatus.Failed("no no no mr fish")
                        )
                    }
                }

            }
        }
    }


    fun postShowWinStatusSE() {
        intent {
            postSideEffect(GameCharactersSideEffect.ShowWinStatus())
        }
    }
    fun postShowLoseStatusSE() {
        intent {
            postSideEffect(GameCharactersSideEffect.ShowLoseStatus())
        }
    }
    fun postChangeCharactersSE() {
        intent {
            postSideEffect(GameCharactersSideEffect.ChangeCharacters())
        }
    }

}