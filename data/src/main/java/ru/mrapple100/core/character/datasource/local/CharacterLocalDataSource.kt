package ru.mrapple100.core.character.datasource.local

import ru.mrapple100.core.character.datasource.local.dao.CharacterDao
import ru.mrapple100.core.character.datasource.local.entity.CharacterEntity
import javax.inject.Inject

class CharacterLocalDataSource @Inject constructor(
    private val characterDao: CharacterDao
) {
    suspend fun getCharacters(from: Int, to:Int): List<CharacterEntity> {
        return characterDao.getCharactersFromTo(from,to)
    }
    suspend fun insertCharacters(entities: List<CharacterEntity>){
        characterDao.insertEntities(entities)
    }
}