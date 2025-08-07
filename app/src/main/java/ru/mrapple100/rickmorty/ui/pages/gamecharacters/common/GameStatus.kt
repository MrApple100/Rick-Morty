package ru.mrapple100.rickmorty.ui.pages.gamecharacters.common

sealed class GameStatus {
    object WinStatus: GameStatus()
    object LoseStatus: GameStatus()
    object ProccessStatus: GameStatus()
    object ChangeCharactersStatus: GameStatus()
}