package ru.mrapple100.rickmorty.ui.pages.characterlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CharacterListViewModel(
    private var searchJob: Job? = null

):ContainerHost<CharacterListState,CharacterListSideEffect>,ViewModel() {
    override val container = container<CharacterListState, CharacterListSideEffect>(
        CharacterListState()
    )

}