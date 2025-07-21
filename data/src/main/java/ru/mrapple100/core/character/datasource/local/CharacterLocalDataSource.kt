package ru.mrapple100.core.character.datasource.local

import ru.mrapple100.core.character.datasource.local.dao.CharacterDao
import ru.mrapple100.core.character.datasource.local.dao.ImageDao
import ru.mrapple100.core.character.datasource.local.entity.CharacterEntity
import ru.mrapple100.core.character.datasource.local.entity.pojo.CharacterWithImage
import javax.inject.Inject

class CharacterLocalDataSource @Inject constructor(
    private val characterDao: CharacterDao,
    private val imageDao: ImageDao
) {
    suspend fun getCharacters(from: Int, to:Int): List<CharacterWithImage> {
        return characterDao.getCharactersFromTo(from, to)
    }
    suspend fun getCharacters(): List<CharacterWithImage> {
        return characterDao.getCharacters()
    }
    suspend fun insertCharacters(entities: List<CharacterEntity>){
        characterDao.insertEntities(entities)
    }
}