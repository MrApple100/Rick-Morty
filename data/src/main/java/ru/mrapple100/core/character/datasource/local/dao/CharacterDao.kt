package ru.mrapple100.core.character.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.mrapple100.core.character.datasource.local.entity.CharacterEntity
import ru.mrapple100.core.character.datasource.local.entity.pojo.CharacterWithImage

@Dao
interface CharacterDao {
    @Transaction
    @Query("SELECT * FROM characterentity WHERE id BETWEEN :from and :to")
    fun getCharactersFromTo(from:Int,to:Int):List<CharacterWithImage>

    @Transaction
    @Query("SELECT * FROM characterEntity")
    suspend fun getCharacters(): List<CharacterWithImage>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntities(entities: List<CharacterEntity>)

}