package ru.mrapple100.domain.character.model

import android.graphics.Bitmap

internal typealias CharacterModelList = List<CharacterModel>

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: Location,
    val location: Location,
    val imageStr: String,
    val imageBitmap: Bitmap?=null,
    val episode: List<String>,
    val url: String,
    val created: String
)
