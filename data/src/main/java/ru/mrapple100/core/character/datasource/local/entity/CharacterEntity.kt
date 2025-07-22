package ru.mrapple100.core.character.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.mrapple100.domain.character.model.Gender
import ru.mrapple100.domain.character.model.Location
import ru.mrapple100.domain.character.model.Status

@Entity
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    @Embedded(prefix = "origin_") val origin: Location,
    @Embedded(prefix = "location_") val location: Location,
    val imageUrl: String,
    val episode: List<String>,
    val url: String,
    val created: String,
    val page:Int
)