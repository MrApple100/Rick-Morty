package ru.mrapple100.domain.character.repository

import kotlinx.coroutines.flow.Flow
import ru.mrapple100.domain.character.model.CharacterModel

interface CharacterRepository {
    suspend fun getCharacters(from:Int,to:Int): Flow<List<CharacterModel>>
    suspend fun getCharacters():Flow<List<CharacterModel>>
    suspend fun getCharacterById(id:Int):Flow<CharacterModel>
    suspend fun fetchAndSaveCharacters(): Flow<List<CharacterModel>>

    suspend fun upPage():Int
    suspend fun downPage():Int

    suspend fun getMaxLocalPage(): Int
    suspend fun getMaxRemotePage(): Int
    suspend fun getCurrentPage(): Int
    suspend fun getLocalCharacters(): Flow<List<CharacterModel>>
}