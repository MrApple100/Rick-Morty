package ru.mrapple100.rickmorty.ui.pages.characterdetails

import androidx.compose.ui.graphics.Color
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.rickmorty.ui.common.UiStatus

data class CharacterState(

    val status: UiStatus = UiStatus.Loading,
    val colorBackground: Color = Color.Gray,
    val detailsCharacter : CharacterModel = CharacterModel()
)