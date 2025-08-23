package ru.mrapple100.core.character

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import ru.mrapple100.core.character.datasource.local.entity.CharacterEntity
import ru.mrapple100.core.character.datasource.local.entity.ImageEntity
import ru.mrapple100.core.character.datasource.local.entity.pojo.CharacterWithImage
import ru.mrapple100.core.character.response.CharacterResponse
import ru.mrapple100.core.character.response.CharacterResponseList
import ru.mrapple100.core.di.DataSourceModule
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
        this.image.APIbaseurlswap(),
        null,
        this.url.APIbaseurlswap(),
        this.episode[0].split("/").last().toInt()
        )
}
fun CharacterResponse.mapToCharacterModel():CharacterModel{
    return CharacterModel(
        id = this.id,
        name = this.name,
        status = Status.safeValueOf(this.status),
        species = this.species,
        type = this.type,
        gender = Gender.safeValueOf(this.gender),
        origin = Location(this.origin.name,this.origin.url),
        location = Location(this.location.name,this.location.url),
        imageStr = this.image.APIbaseurlswap(),
        imageBitmap = null,
        episode = this.episode,
        url = this.url.APIbaseurlswap(),
        created = this.created

    )

}
fun CharacterModel.mapToCharacterEntity(page:Int):CharacterEntity{
    return CharacterEntity(
        id = this.id,
        name = this.name,
        status = Status.safeValueOf(this.status.name),
        species = this.species,
        type = this.type,
        gender = Gender.safeValueOf(this.gender.name),
        origin = Location(this.origin.name,this.origin.url.APIbaseurlswap()),
        location = Location(this.location.name,this.location.url.APIbaseurlswap()),
        imageUrl = this.imageStr.APIbaseurlswap(),
        episode = this.episode,
        url = this.url.APIbaseurlswap(),
        created = this.created,
        page = page
    )

}
fun CharacterModel.mapToImageEntity():ImageEntity{
    return ImageEntity(
        0,
        this.id,
        this.imageBitmap?.toByteArray() ?: byteArrayOf()
    )
}
fun CharacterWithImage.mapToCharacterModel():CharacterModel{
    return CharacterModel(
        id = this.character.id,
        name = this.character.name,
        status = Status.safeValueOf(this.character.status.name),
        species = this.character.species,
        type = this.character.type,
        gender = Gender.safeValueOf(this.character.gender.name),
        origin = Location(this.character.origin.name,this.character.origin.url.APIbaseurlswap()),
        location = Location(this.character.location.name,this.character.location.url.APIbaseurlswap()),
        imageStr = this.character.imageUrl.APIbaseurlswap(),
        imageBitmap = this.image?.imageBitmap?.toBitmap(),
        episode = this.character.episode,
        url = this.character.url.APIbaseurlswap(),
        created = this.character.created

    )
}

fun CharacterWithImage.mapToCharacterCardModel():CharacterCardModel{
    return CharacterCardModel(
        id = this.character.id,
        name = this.character.name,
        species = this.character.species,
        imageStr = this.character.imageUrl.APIbaseurlswap(),
        imageBitmap = this.image?.imageBitmap?.toBitmap(),
        url = this.character.url.APIbaseurlswap(),
        firstOfEpisode = this.character.episode[0].split("/").last().toInt()

    )
}
@JvmName("mapToCharacterModelsFromResponses")
internal fun CharacterResponseList.mapToCharacterModels() =
    map { it.mapToCharacterModel() }
@JvmName("mapToCharacterEntitiesFromCharModels")
internal fun List<CharacterModel>.mapToCharacterEntities(page:Int) =
    map { it.mapToCharacterEntity(page) }
@JvmName("mapToCharacterCardModelsFromCharWithImages")
internal fun List<CharacterWithImage>.mapToCharacterCardModels() =
    map { it.mapToCharacterCardModel() }
@JvmName("mapToCharacterModelsFromEntities")
internal fun List<CharacterWithImage>.mapToCharacterModels() =
    map { it.mapToCharacterModel() }

internal fun CharacterResponseList.mapToCharacterCardModels() =
    map { it.mapToCharacterCardModel() }

internal fun Bitmap.toByteArray():ByteArray{
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 90, stream)
    return stream.toByteArray()
}

internal fun ByteArray.toBitmap():Bitmap{
    return BitmapFactory.decodeByteArray(this,0,this.size)
}

internal fun String.APIbaseurlswap():String{
    val twopart = this.split("api/")
    return if(twopart.size==2) {
        val res = DataSourceModule.provideBaseUrl + this.split("api/")[1]
        res
    }
    else
        this

}