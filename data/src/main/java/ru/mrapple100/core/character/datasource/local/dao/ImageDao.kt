package ru.mrapple100.core.character.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface ImageDao {
    @Insert
    suspend fun insert(image: ImageEntity)

    @Insert
    suspend fun insertAll(images: List<ImageEntity>)

    @Update
    suspend fun update(image: ImageEntity)

    @Delete
    suspend fun delete(image: ImageEntity)

    @Query("SELECT * FROM images where pokemonId = :pokemonId")
    suspend fun findImageByPokemonId(pokemonId: Int): ImageEntity
}