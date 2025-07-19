package ru.mrapple100.domain.character.repository

import ru.mrapple100.domain.character.model.CharacterModel

interface CharacterRepository {
    suspend fun getCharacters(from:Int,to:Int):List<CharacterModel>
}