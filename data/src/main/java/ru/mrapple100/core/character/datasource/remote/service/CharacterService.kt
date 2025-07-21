package ru.mrapple100.core.character.datasource.remote.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mrapple100.core.character.response.CharacterResponse
import ru.mrapple100.core.character.response.ResultAllResponse

interface CharacterService {
    @GET("character/{range}")
    suspend fun getCharacters(@Path("range") range:String): List<CharacterResponse>

    @GET("character/")
    suspend fun getCharacters(@Query("page") page:Int): ResultAllResponse

    @GET("character/{range}")
    suspend fun getCharacter(@Path("range") range:String): CharacterResponse


}