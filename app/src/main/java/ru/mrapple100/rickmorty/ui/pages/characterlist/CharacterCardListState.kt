package ru.mrapple100.rickmorty.ui.pages.characterlist

import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.rickmorty.ui.common.UiStatus

data class CharacterCardListState(

    val status: UiStatus = UiStatus.Loading,
    val searchText: String = "",
    val detailsList: List<CharacterCardModel> = emptyList()
)