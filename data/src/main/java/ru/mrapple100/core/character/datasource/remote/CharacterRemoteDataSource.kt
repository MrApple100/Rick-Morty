package ru.mrapple100.core.character.datasource.remote

import ru.mrapple100.core.character.datasource.remote.service.CharacterService
import ru.mrapple100.core.character.response.CharacterResponseModel
import java.util.Arrays
import javax.inject.Inject

internal class CharacterRemoteDataSource @Inject constructor(
    private val characterService: CharacterService
){
    suspend fun getCharacters(range:String): List<CharacterResponseModel> {
       return characterService.getCharacters(range)
    }
}