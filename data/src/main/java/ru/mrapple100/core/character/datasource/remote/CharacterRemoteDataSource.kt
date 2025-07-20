package ru.mrapple100.core.character.datasource.remote

import ru.mrapple100.core.character.datasource.remote.service.CharacterService
import ru.mrapple100.core.character.response.CharacterResponse
import java.util.Arrays
import javax.inject.Inject

internal class CharacterRemoteDataSource @Inject constructor(
    private val characterService: CharacterService
){
    suspend fun getCharacters(from: Int, to: Int): List<CharacterResponse> {
        val arrayFromTo = Array(to-from) { index -> index + from }
        val range:String = arrayFromTo.joinToString(separator = ",")
       return characterService.getCharacters(range)
    }
}