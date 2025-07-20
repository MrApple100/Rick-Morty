package ru.mrapple100.core.character.repository

import ru.mrapple100.core.character.datasource.CharacterMemoryDataSource
import ru.mrapple100.core.character.datasource.remote.CharacterRemoteDataSource
import ru.mrapple100.core.character.mapToCharacterModels
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterMemoryDataSource: CharacterMemoryDataSource,
) : CharacterRepository {


    override suspend fun getCharacters(from: Int, to: Int): List<CharacterModel> {
        val arrayFromTo = Array(to-from) { index -> index + from }
        val range:String = arrayFromTo.joinToString(separator = ",")
        return characterRemoteDataSource.getCharacters(range).mapToCharacterModels()
    }


}