package ru.mrapple100.core.character.datasource.local.entity.pojo

import androidx.room.Embedded
import androidx.room.Relation
import ru.mrapple100.core.character.datasource.local.entity.CharacterEntity
import ru.mrapple100.core.character.datasource.local.entity.ImageEntity

data class CharacterWithImage(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "characterId"
    )
    val image: ImageEntity? // Optional if image might be missing
)