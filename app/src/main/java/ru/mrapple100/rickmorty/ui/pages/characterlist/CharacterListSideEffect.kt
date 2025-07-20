package ru.mrapple100.rickmorty.ui.pages.characterlist

sealed class CharacterListSideEffect {
    data class ShowDetails(val id: Int) : CharacterListSideEffect()
}