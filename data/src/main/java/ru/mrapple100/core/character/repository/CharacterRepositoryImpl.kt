package ru.mrapple100.core.character.repository

import ru.mrapple100.core.character.datasource.CharacterMemoryDataSource
import ru.mrapple100.core.character.datasource.local.CharacterLocalDataSource
import ru.mrapple100.core.character.datasource.remote.CharacterRemoteDataSource
import ru.mrapple100.core.character.mapToCharacterEntities
import ru.mrapple100.core.character.mapToCharacterModel
import ru.mrapple100.core.character.mapToCharacterModels
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
//    private val characterMemoryDataSource: CharacterMemoryDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource
) : CharacterRepository {


    override suspend fun getCharacters(from: Int, to: Int): List<CharacterModel> {
        return characterRemoteDataSource.getCharacters(from,to).mapToCharacterModels()
    }
    override suspend fun getCharacterById(id:Int): CharacterModel {
        return characterRemoteDataSource.getCharacter(id).mapToCharacterModel()
    }

    override suspend fun setLocalCharacters(characterModels: List<CharacterModel>){
        val characterEntities = characterModels.mapToCharacterEntities()
        characterLocalDataSource.insertCharacters(characterEntities)
    }


}