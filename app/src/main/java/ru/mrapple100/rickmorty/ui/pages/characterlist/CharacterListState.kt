package ru.mrapple100.rickmorty.ui.pages.characterlist

import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.rickmorty.ui.common.UiStatus

data class CharacterListState(
    val status: UiStatus = UiStatus.Loading,
    val searchText: String = "",
    val detailsList: List<CharacterModel> = emptyList()
)