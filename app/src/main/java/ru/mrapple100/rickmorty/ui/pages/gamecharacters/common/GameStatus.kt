package ru.mrapple100.rickmorty.ui.pages.gamecharacters.common

import ru.mrapple100.rickmorty.ui.common.UiStatus

sealed class GameStatus {
    data class ShowStatus(val winloseStatus: WINLOSEStatus) : GameStatus()
    object ProccessStatus: GameStatus()
    object ChangeCharactersStatus: GameStatus()
}

enum class WINLOSEStatus{
    WIN,LOSE,NONE
}
enum class ChooseUser{
    CardFirst,CardSecond
}