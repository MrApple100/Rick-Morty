package ru.mrapple100.core.character.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import ru.mrapple100.core.character.datasource.local.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characterentity WHERE id BETWEEN :from and :to")
    fun getCharactersFromTo(from:Int,to:Int):List<CharacterEntity>

}