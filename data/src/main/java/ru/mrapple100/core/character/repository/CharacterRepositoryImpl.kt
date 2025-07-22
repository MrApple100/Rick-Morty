package ru.mrapple100.core.character.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mrapple100.core.character.datasource.CharacterMemoryDataSource
import ru.mrapple100.core.character.datasource.local.CharacterLocalDataSource
import ru.mrapple100.core.character.datasource.remote.CharacterRemoteDataSource
import ru.mrapple100.core.character.mapToCharacterEntities
import ru.mrapple100.core.character.mapToCharacterModel
import ru.mrapple100.core.character.mapToCharacterModels
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject
import kotlin.math.max

class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterMemoryDataSource: CharacterMemoryDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource
) : CharacterRepository {


    override suspend fun getCharacters(from: Int, to: Int): Flow<List<CharacterModel>> = flow {
        emit(characterRemoteDataSource.getCharacters(from,to).mapToCharacterModels())
    }

    override suspend fun getCharacters(): Flow<List<CharacterModel>> = flow {
        val page = characterMemoryDataSource.getPage()
        val resultAll = characterRemoteDataSource.getCharacters(page)
        emit(resultAll.results.mapToCharacterModels())

    }

    override suspend fun getCharacterById(id:Int): Flow<CharacterModel>  = flow {
        emit(characterRemoteDataSource.getCharacter(id).mapToCharacterModel())
    }

    override suspend fun getLocalCharacters(): Flow<List<CharacterModel>> = flow {
        val page = characterMemoryDataSource.getPage()
        emit(characterLocalDataSource.getCharacters(page).mapToCharacterModels())

    }


    override suspend fun fetchAndSaveCharacters(): Flow<List<CharacterModel>> = flow {
        val page = characterMemoryDataSource.getPage()
        val resultAll = characterRemoteDataSource.getCharacters(page)
        characterMemoryDataSource.setMaxRemotePage(resultAll.info.pages)
        val chsModels = resultAll.results.mapToCharacterModels()
        val chsEntities = chsModels.mapToCharacterEntities(page)
        characterLocalDataSource.insertCharacters(chsEntities)

        var maxPage = characterMemoryDataSource.getMaxLocalPage()
        maxPage = max(page,maxPage)
        characterMemoryDataSource.setMaxLocalPage(maxPage)
        emit(chsModels)

    }

    override suspend fun upPage():Int{
        val currentPage = characterMemoryDataSource.getPage()
        characterMemoryDataSource.setPage(currentPage+1)
        return currentPage+1
    }
    override suspend fun downPage():Int{
        val currentPage = characterMemoryDataSource.getPage()
        characterMemoryDataSource.setPage(currentPage-1)
        return currentPage-1
    }
    override suspend fun getMaxLocalPage():Int{
        return characterMemoryDataSource.getMaxLocalPage()
    }
    override suspend fun getMaxRemotePage():Int{
        return characterMemoryDataSource.getMaxRemotePage()
    }
    override suspend fun getCurrentPage():Int{
        return characterMemoryDataSource.getPage()
    }


}