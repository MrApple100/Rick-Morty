package ru.mrapple100.core.character.datasource.remote

import android.util.Log
import ru.mrapple100.common.ResponseHandler
import ru.mrapple100.core.character.datasource.remote.service.CharacterService
import ru.mrapple100.core.character.response.CharacterResponse
import ru.mrapple100.core.character.response.ResultAllResponse
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val characterService: CharacterService,
    private val handler: ResponseHandler
){
    suspend fun getCharacters(from: Int, to: Int) = handler {
        val arrayFromTo = Array(to-from) { index -> index + from }
        val range:String = arrayFromTo.joinToString(separator = ",")
        val result = characterService.getCharacters(range)
        Log.d("FIRSTFIRST",result.get(0).toString())

        result
    }
    suspend fun getCharacters(page:Int) = handler {

        val result = characterService.getCharacters(page)
        result
    }
    suspend fun getCharacter(id:Int)= handler {

        characterService.getCharacter(id.toString())
    }
}