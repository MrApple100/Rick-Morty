package ru.mrapple100.rickmorty

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import ru.mrapple100.rickmorty.navigation.Destination
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.GameCharactersSideEffect
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.GameCharactersState
import javax.inject.Inject

@HiltViewModel
class MainNavigationViewModel @Inject constructor(

): ContainerHost<MainNavigationState, MainNavigationSideEffect>, ViewModel() {


    override val container = container<MainNavigationState, MainNavigationSideEffect>(
        MainNavigationState()
    )

    init{

    }

    fun changeSelectedDestination(destination: Destination){
        intent {
            reduce {
                state.copy(
                    selectedDestinationBottom = destination
                )
            }
        }
    }


    fun postChangeSelectedDestinationSE(destination: Destination){
        intent {
            postSideEffect(MainNavigationSideEffect.ChangeBottomDestination(destination))
        }
    }
}