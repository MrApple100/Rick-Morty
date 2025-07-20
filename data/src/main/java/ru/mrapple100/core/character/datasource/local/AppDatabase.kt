package ru.mrapple100.core.character.datasource.local

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.mrapple100.core.character.datasource.local.dao.CharacterDao
import ru.mrapple100.core.character.datasource.local.dao.ImageDao
import ru.mrapple100.core.character.datasource.local.entity.CharacterEntity
import ru.mrapple100.core.character.datasource.local.entity.ImageEntity
import ru.mrapple100.core.character.datasource.local.entity.ListConverter

@TypeConverters(ListConverter::class)
@Database(entities =
    [CharacterEntity::class,
        ImageEntity::class
    ], version = 1)
abstract class AppDatabase() : RoomDatabase(){
    abstract fun characterDao():CharacterDao
    abstract fun imageDao():ImageDao

}