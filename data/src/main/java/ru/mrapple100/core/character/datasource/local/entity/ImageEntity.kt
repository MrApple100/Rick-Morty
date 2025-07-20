package ru.mrapple100.core.character.datasource.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "images",
    foreignKeys = [
        ForeignKey(
            entity = CharacterEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("characterId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val characterId: Int,
    val imageBitmap: ByteArray,

)