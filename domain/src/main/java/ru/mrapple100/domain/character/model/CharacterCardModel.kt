package ru.mrapple100.domain.character.model

import android.graphics.Bitmap

//internal typealias CharacterCardModelList = List<CharacterCardModel>

data class CharacterCardModel(
    var id: Int = 0,
    val name: String = "no info",
    val species: String = "no info",
    val imageStr: String = "",
    val imageBitmap: Bitmap?=null,
    val url: String = "",
    var sharedKey:String = ""
) {

}
