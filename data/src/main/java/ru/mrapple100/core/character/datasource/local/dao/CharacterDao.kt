package ru.mrapple100.core.character.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.mrapple100.core.character.datasource.local.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characterentity WHERE id BETWEEN :from and :to")
    fun getCharactersFromTo(from:Int,to:Int):List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntities(entities: List<CharacterEntity>)

}