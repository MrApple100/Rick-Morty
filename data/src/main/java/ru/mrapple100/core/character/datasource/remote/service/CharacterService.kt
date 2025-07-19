package ru.mrapple100.core.character.datasource.remote.service

import retrofit2.http.GET
import ru.mrapple100.core.character.response.CharacterResponseModel

interface CharacterService {
    @GET("/character")
    suspend fun getCharacters(): List<CharacterResponseModel>
}