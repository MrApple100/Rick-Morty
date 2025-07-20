package ru.mrapple100.domain.character.repository

import ru.mrapple100.domain.character.model.CharacterModel

interface CharacterRepository {
    suspend fun getCharacters(from:Int,to:Int):List<CharacterModel>
    suspend fun getCharacters():List<CharacterModel>
    suspend fun getCharacterById(id:Int):CharacterModel
    suspend fun setLocalCharacters(characterModels:List<CharacterModel>)
}