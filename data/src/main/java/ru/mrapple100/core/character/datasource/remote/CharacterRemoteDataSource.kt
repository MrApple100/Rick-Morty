package ru.mrapple100.core.character.datasource.remote

import android.util.Log
import ru.mrapple100.core.character.datasource.remote.service.CharacterService
import ru.mrapple100.core.character.response.CharacterResponse
import ru.mrapple100.core.character.response.ResultAllResponse
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val characterService: CharacterService
){
    suspend fun getCharacters(from: Int, to: Int): List<CharacterResponse> {
        val arrayFromTo = Array(to-from) { index -> index + from }
        val range:String = arrayFromTo.joinToString(separator = ",")
        val result = characterService.getCharacters(range)
        Log.d("FIRSTFIRST",result.get(0).toString())

        return result
    }
    suspend fun getCharacters(page:Int): ResultAllResponse {
        Log.d("FIRSTFIRST","result.toString()")

        val result = characterService.getCharacters(page)
        Log.d("FIRSTFIRST",result.toString())
        return result
    }
    suspend fun getCharacter(id:Int): CharacterResponse {

        return characterService.getCharacter(id.toString())
    }
}