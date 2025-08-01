package ru.mrapple100.core.character.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.mrapple100.core.character.datasource.local.entity.ImageEntity

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: ImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(image: ImageEntity)

    @Delete
    suspend fun delete(image: ImageEntity)

    @Query("SELECT * FROM images where characterId = :characterId")
    suspend fun findImageByCharacterId(characterId: Int): ImageEntity
}