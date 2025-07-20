package ru.mrapple100.core.character

import ru.mrapple100.core.character.response.CharacterResponseList
import ru.mrapple100.core.character.response.CharacterResponseModel
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.model.Gender
import ru.mrapple100.domain.character.model.Location
import ru.mrapple100.domain.character.model.Status

fun CharacterResponseModel.mapToCharacterCardModel():CharacterCardModel{
    return CharacterCardModel(
        this.id,
        this.name,
        this.species,
        this.image,
        this.url
    )
}
fun CharacterResponseModel.mapToCharacterModel():CharacterModel{
    return CharacterModel(
        id = this.id,
        name = this.name,
        status = Status.safeValueOf(this.status.name),
        species = this.species,
        type = this.type,
        gender = Gender.safeValueOf(this.gender.name),
        origin = Location(this.origin.name,this.origin.url),
        location = Location(this.location.name,this.location.url),
        image = this.image,
        episode = this.episode,
        url = this.url,
        created = this.created

    )

}
internal fun CharacterResponseList.mapToCharacterModels() =
    map { it.mapToCharacterModel() }
internal fun CharacterResponseList.mapToCharacterCardModels() =
    map { it.mapToCharacterCardModel() }