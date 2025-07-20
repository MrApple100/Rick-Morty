package ru.mrapple100.core.character.datasource.remote.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import ru.mrapple100.core.character.response.CharacterResponseModel

interface CharacterService {
    @GET("/character/{range}")
    suspend fun getCharacters(@Path("range") range:String): List<CharacterResponseModel>

}