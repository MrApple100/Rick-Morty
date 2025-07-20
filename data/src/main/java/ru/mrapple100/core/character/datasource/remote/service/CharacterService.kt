package ru.mrapple100.core.character.datasource.remote.service

import retrofit2.http.GET
import retrofit2.http.Path
import ru.mrapple100.core.character.response.CharacterResponse

interface CharacterService {
    @GET("/character/{range}")
    suspend fun getCharacters(@Path("range") range:String): List<CharacterResponse>

    @GET("/character/{range}")
    suspend fun getCharacter(@Path("range") range:String): CharacterResponse


}