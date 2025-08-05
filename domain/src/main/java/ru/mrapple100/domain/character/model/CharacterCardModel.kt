package ru.mrapple100.domain.character.model

import android.graphics.Bitmap

//internal typealias CharacterCardModelList = List<CharacterCardModel>

data class CharacterCardModel(
    var id: Int,
    val name: String,
    val species: String,
    val imageStr: String,
    val imageBitmap: Bitmap?=null,
    val url: String,
    var sharedKey:String = ""
) {

}
