package ru.mrapple100.rickmorty.ui.pages.gamecharacters

import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.rickmorty.ui.common.UiStatus
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.ChooseUser
import ru.mrapple100.rickmorty.ui.pages.gamecharacters.common.GameStatus

data class GameCharactersState(
    val gameStatus: GameStatus = GameStatus.ChangeCharactersStatus,
    val queueCorrect: ArrayDeque<ChooseUser> = ArrayDeque(5),
    val currentCorrect: ChooseUser = ChooseUser.CardFirst,
    val status: UiStatus = UiStatus.Loading,
    val queuePair: ArrayDeque<Pair<CharacterCardModel?,CharacterCardModel?>> = ArrayDeque(5),
    val currentPair: Pair<CharacterCardModel?,CharacterCardModel?> = Pair(CharacterCardModel(),CharacterCardModel()),
    val countChanges: Int = 0,
    val countCorrect: Int = 0

)