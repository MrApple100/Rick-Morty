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
            reduce {
                state.copy(
                    gameStatus = GameStatus.ChangeCharactersStatus,
                    status = UiStatus.Loading
                )
            }
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
            if(chooseUser == state.currentCorrect)
                showWinStatus()
            else
                showLoseStatus()

            delay(400)
            changeCharacters()
        }
    }

    private fun showWinStatus() {
        intent {
            reduce {
                state.copy(
                    gameStatus = GameStatus.ShowStatus(WINLOSEStatus.WIN),
                )
            }
        }
    }

    private fun showLoseStatus() {
        intent {
            reduce {
                state.copy(
                    gameStatus = GameStatus.ShowStatus(WINLOSEStatus.LOSE)
                )
            }

        }
    }

    fun changeCharacters() {
        intent {
            reduce {
                state.copy(
                    gameStatus = GameStatus.ChangeCharactersStatus,
                )
            }
            while (state.queuePair.size < 5) {
                loadRandomTwoCharacterUseCase().collect { chs ->
                    var correctCard: ChooseUser = ChooseUser.CardFirst
                    if (chs.first!!.firstOfEpisode > chs.second!!.firstOfEpisode)
                        correctCard = ChooseUser.CardSecond
                    state.queuePair.addLast(chs)
                    state.queueCorrect.addLast(correctCard)
                }
            }
            reduce {
                val currentPair = state.queuePair.first()
                val currentCorrect = state.queueCorrect.first()
                val newQueuePair = state.queuePair
                    newQueuePair.removeFirst()
                val newQueueCorrect = state.queueCorrect
                    newQueueCorrect.removeFirst()

                state.copy(
                    currentPair = currentPair,
                    currentCorrect = currentCorrect,
                    queuePair = newQueuePair,
                    queueCorrect = newQueueCorrect,
                    gameStatus = GameStatus.ProccessStatus,
                    status = UiStatus.Success
                )
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