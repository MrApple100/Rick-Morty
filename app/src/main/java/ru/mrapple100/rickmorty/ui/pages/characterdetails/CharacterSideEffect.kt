package ru.mrapple100.rickmorty.ui.pages.characterdetails

import ru.mrapple100.rickmorty.ui.pages.characterlist.CharacterListSideEffect

sealed class CharacterSideEffect {
    class backToStack : CharacterSideEffect()
}