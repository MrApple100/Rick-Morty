package ru.mrapple100.domain.character.repository

import kotlinx.coroutines.flow.Flow
import ru.mrapple100.domain.character.model.CharacterModel

interface CharacterRepository {
    suspend fun getCharacters(from:Int,to:Int): Flow<List<CharacterModel>>
    suspend fun getCharacters():Flow<List<CharacterModel>>
    suspend fun getCharacterById(id:Int):Flow<CharacterModel>
    suspend fun setLocalCharacters(characterModels:List<CharacterModel>)
}