package ru.mrapple100.rickmorty.ui.pages.gamecharacters

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.usecases.LoadRandomTwoCharacterUseCase
import ru.mrapple100.rickmorty.ui.common.UiStatus
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.ChooseUser
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.GameStatus
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.WINLOSEStatus
import javax.inject.Inject
import kotlin.math.max

@HiltViewModel
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

    fun showGameStatus(chooseUser: ChooseUser) {
        intent {
            reduce {
                state.copy(
                    gameStatus = GameStatus.ShowStatus(WINLOSEStatus.NONE),
                )
            }
            if(chooseUser == state.correct)
                showWinStatus()
            else
                showLoseStatus()
        }
    }

    private fun showWinStatus() {
        intent {
            reduce {
                state.copy(
                    gameStatus = GameStatus.ShowStatus(WINLOSEStatus.WIN),
                )
            }
            delay(1000)
            changeCharacters()
        }
    }

    private fun showLoseStatus() {
        intent {
            reduce {
                state.copy(
                    gameStatus = GameStatus.ShowStatus(WINLOSEStatus.LOSE)
                )
            }
            delay(1000)
            changeCharacters()
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
                    var correctCard: ChooseUser = ChooseUser.CardFirst
                    if(chs.first!!.firstOfEpisode>chs.second!!.firstOfEpisode)
                        correctCard = ChooseUser.CardSecond
                    reduce {
                        state.copy(
                            pair = chs,
                            correct = correctCard,
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


    fun postShowStatusSE(chooseUser: ChooseUser) {
        intent {
            postSideEffect(GameCharactersSideEffect.ShowStatus(chooseUser))
        }
    }

    fun postChangeCharactersSE() {
        intent {
            postSideEffect(GameCharactersSideEffect.ChangeCharacters())
        }
    }

}