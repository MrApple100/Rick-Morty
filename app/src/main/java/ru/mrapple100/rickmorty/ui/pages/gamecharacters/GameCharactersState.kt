package ru.mrapple100.rickmorty.ui.pages.gamecharacters

import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.rickmorty.ui.common.UiStatus
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.GameStatus

data class GameCharactersState(
    val gameStatus: GameStatus = GameStatus.ChangeCharactersStatus,
    val status: UiStatus = UiStatus.Loading,
    val pair: Pair<CharacterCardModel?,CharacterCardModel?> = Pair(CharacterCardModel(),CharacterCardModel())

) {
}