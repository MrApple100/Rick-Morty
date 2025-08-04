package ru.mrapple100.domain.character.repository

import kotlinx.coroutines.flow.Flow
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.model.CharacterModel

interface CharacterRepository {
    suspend fun getCharacters(from:Int,to:Int): Flow<List<CharacterCardModel>?>
    suspend fun getCharacters():Flow<List<CharacterCardModel>?>
    suspend fun getCharacterById(id:Int):Flow<CharacterModel?>
    suspend fun getLocalCharacterById(id:Int):Flow<CharacterModel?>
    suspend fun fetchAndSaveCharacters(): Flow<List<CharacterCardModel>?>

    suspend fun upPage():Int
    suspend fun downPage():Int

    suspend fun getMaxLocalPage(): Int
    suspend fun getMaxRemotePage(): Int
    suspend fun getMaxPage(): Int
    suspend fun getCurrentPage(): Int
    suspend fun getLocalCharacters(): Flow<List<CharacterCardModel>?>
    suspend fun currentPageToOne()
    suspend fun resetIsOnlineStatus()
}