package ru.mrapple100.domain.character.model

import android.graphics.Bitmap

internal typealias CharacterModelList = List<CharacterModel>

data class CharacterModel(
    val id: Int=0,
    val name: String="no info",
    val status: Status=Status.UNKNOWN,
    val species: String="no info",
    val type: String="no type",
    val gender: Gender=Gender.UNKNOWN,
    val origin: Location=Location("no home location",""),
    val location: Location=Location("no location",""),
    val imageStr: String = "",
    val imageBitmap: Bitmap?=null,
    val episode: List<String> = emptyList(),
    val url: String ="",
    val created: String = ""
){
    override fun equals(other: Any?): Boolean {
        return other is CharacterModel && other.id == id
    }
}
