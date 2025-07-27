package ru.mrapple100.core.character.repository

import android.util.Log
import androidx.room.concurrent.AtomicBoolean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mrapple100.core.character.datasource.CharacterMemoryDataSource
import ru.mrapple100.core.character.datasource.local.CharacterLocalDataSource
import ru.mrapple100.core.character.datasource.remote.CharacterRemoteDataSource
import ru.mrapple100.core.character.mapToCharacterCardModels
import ru.mrapple100.core.character.mapToCharacterEntities
import ru.mrapple100.core.character.mapToCharacterModel
import ru.mrapple100.core.character.mapToCharacterModels
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject
import kotlin.math.max

class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterMemoryDataSource: CharacterMemoryDataSource,
    private val characterLocalDataSource: CharacterLocalDataSource
) : CharacterRepository {
    private val isOnline: AtomicBoolean = AtomicBoolean(true)


    override suspend fun getCharacters(from: Int, to: Int): Flow<List<CharacterCardModel>?> = flow {
        val resultAll = characterRemoteDataSource.getCharacters(from,to)
        resultAll.handle(
            onSuccess = { resultAll ->
                val chCardModels = resultAll.mapToCharacterCardModels()

                emit(chCardModels)
            },
            onError = {
                emit(null)
            }
        )
    }

    override suspend fun getCharacters(): Flow<List<CharacterCardModel>?> = flow {
        val page = characterMemoryDataSource.getPage()
        val resultAll = characterRemoteDataSource.getCharacters(page)
        resultAll.handle(
            onSuccess = { resultAll ->
                characterMemoryDataSource.setMaxRemotePage(resultAll.info.pages)
                val chCardModels = resultAll.results.mapToCharacterCardModels()

                var maxPage = characterMemoryDataSource.getMaxLocalPage()
                maxPage = max(page,maxPage)
                characterMemoryDataSource.setMaxLocalPage(maxPage)
                emit(chCardModels)
            },
            onError = {
                emit(null)
            }
        )

    }

    override suspend fun getCharacterById(id:Int): Flow<CharacterModel?> = flow {
        characterRemoteDataSource.getCharacter(id).handle(
            onSuccess = { character ->
                emit(character.mapToCharacterModel())
            },
            onError = {
                emit(null)
            }
        )
    }

    override suspend fun getLocalCharacters(): Flow<List<CharacterCardModel>?> = flow {
        val page = characterMemoryDataSource.getPage()
        Log.d("LOCALLOCAL","$page")

        var maxPage = characterMemoryDataSource.getMaxLocalPage()
        Log.d("LOCALLOCAL","$maxPage $page")
        if(maxPage>=page) {
            emit(characterLocalDataSource.getCharacters(page).mapToCharacterCardModels())
        }else{
            emit(null)
        }

    }

    override suspend fun resetIsOnlineStatus(){
        isOnline.set(true)
    }


    override suspend fun fetchAndSaveCharacters(): Flow<List<CharacterCardModel>?>{
        var resultFlow = flow<List<CharacterCardModel>?>{emit(null)}

        if(isOnline.get()) {
            val page = characterMemoryDataSource.getPage()
            val resultAll = characterRemoteDataSource.getCharacters(page)
            resultAll.handle(
                onSuccess = { resultAll ->
                    resultFlow = flow {
                        isOnline.set(true)
                        characterMemoryDataSource.setMaxRemotePage(resultAll.info.pages)
                        val chsModels = resultAll.results.mapToCharacterModels()
                        val chsEntities = chsModels.mapToCharacterEntities(page)
                        val chCardModels = resultAll.results.mapToCharacterCardModels()
                        characterLocalDataSource.insertCharacters(chsEntities)

                        var maxPage = characterMemoryDataSource.getMaxLocalPage()
                        maxPage = max(page, maxPage)
                        characterMemoryDataSource.setMaxLocalPage(maxPage)
                        emit(chCardModels)
                    }
                },
                onError = {

                    isOnline.set(false)
                    resultFlow = getLocalCharacters()
                }
            )
        }else{
            resultFlow = getLocalCharacters()
        }
        return resultFlow
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
    override suspend fun currentPageToOne(){
        characterMemoryDataSource.setPage(1)

    }
    override suspend fun getMaxLocalPage():Int{
        return characterMemoryDataSource.getMaxLocalPage()
    }
    override suspend fun getMaxRemotePage():Int{
        return characterMemoryDataSource.getMaxRemotePage()
    }

    override suspend fun getMaxPage(): Int {
        return if(isOnline.get())
            getMaxRemotePage()
        else
            getMaxLocalPage()
    }
    override suspend fun getCurrentPage():Int{
        return characterMemoryDataSource.getPage()
    }


}