package ru.mrapple100.core.character.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mrapple100.core.character.datasource.local.CharacterLocalDataSource
import ru.mrapple100.core.character.datasource.remote.CharacterRemoteDataSource
import ru.mrapple100.core.character.mapToCharacterEntities
import ru.mrapple100.core.character.mapToCharacterModel
import ru.mrapple100.core.character.mapToCharacterModels
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
//    private val characterMemoryDataSource: CharacterMemoryDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource
) : CharacterRepository {


    override suspend fun getCharacters(from: Int, to: Int): Flow<List<CharacterModel>> = flow {
        emit(characterRemoteDataSource.getCharacters(from,to).mapToCharacterModels())
    }

    override suspend fun getCharacters(): Flow<List<CharacterModel>> = flow {
        val page = 1
        emit(characterRemoteDataSource.getCharacters(page).results.mapToCharacterModels())
    }

    override suspend fun getCharacterById(id:Int): Flow<CharacterModel>  = flow {
        emit(characterRemoteDataSource.getCharacter(id).mapToCharacterModel())
    }

    override suspend fun setLocalCharacters(characterModels: List<CharacterModel>){
        val characterEntities = characterModels.mapToCharacterEntities()
        characterLocalDataSource.insertCharacters(characterEntities)
    }


}