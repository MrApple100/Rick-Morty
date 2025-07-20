package ru.mrapple100.core.character

import android.graphics.Bitmap
import ru.mrapple100.core.character.datasource.local.entity.CharacterEntity
import ru.mrapple100.core.character.response.CharacterResponse
import ru.mrapple100.core.character.response.CharacterResponseList
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.model.Gender
import ru.mrapple100.domain.character.model.Location
import ru.mrapple100.domain.character.model.Status
import java.io.ByteArrayOutputStream

fun CharacterResponse.mapToCharacterCardModel():CharacterCardModel{
    return CharacterCardModel(
        this.id,
        this.name,
        this.species,
        this.image,
        null,
        this.url
    )
}
fun CharacterResponse.mapToCharacterModel():CharacterModel{
    return CharacterModel(
        id = this.id,
        name = this.name,
        status = Status.safeValueOf(this.status.name),
        species = this.species,
        type = this.type,
        gender = Gender.safeValueOf(this.gender.name),
        origin = Location(this.origin.name,this.origin.url),
        location = Location(this.location.name,this.location.url),
        imageStr = this.image,
        imageBitmap = null,
        episode = this.episode,
        url = this.url,
        created = this.created

    )

}
fun CharacterModel.mapToCharacterEntity():CharacterEntity{
    return CharacterEntity(
        id = this.id,
        name = this.name,
        status = Status.safeValueOf(this.status.name),
        species = this.species,
        type = this.type,
        gender = Gender.safeValueOf(this.gender.name),
        origin = Location(this.origin.name,this.origin.url),
        location = Location(this.location.name,this.location.url),
        imageStr = this.imageStr,
        imageBitmap = this.imageBitmap?.toByteArray() ?: byteArrayOf(),
        episode = this.episode,
        url = this.url,
        created = this.created

    )

}
internal fun CharacterResponseList.mapToCharacterModels() =
    map { it.mapToCharacterModel() }

internal fun List<CharacterModel>.mapToCharacterEntities() =
    map { it.mapToCharacterEntity() }


internal fun CharacterResponseList.mapToCharacterCardModels() =
    map { it.mapToCharacterCardModel() }

internal fun Bitmap.toByteArray():ByteArray{
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 90, stream)
    return stream.toByteArray()
}