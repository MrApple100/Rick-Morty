package ru.mrapple100.core.character.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
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
    @Embedded("origin")
    val origin: Location,
    @Embedded("location")
    val location: Location,
    val imageStr: String,
    val imageBitmap: ByteArray,
    val episode: List<String>,
    val url: String,
    val created: String
)