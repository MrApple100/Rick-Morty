package ru.mrapple100.core.character.repository

import android.util.Log
import androidx.room.concurrent.AtomicBoolean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mrapple100.core.character.datasource.CharacterMemoryDataSource
import ru.mrapple100.core.character.datasource.local.CharacterLocalDataSource
import ru.mrapple100.core.character.datasource.remote.CharacterRemoteDataSource
import ru.mrapple100.core.character.mapToCharacterCardModel
import ru.mrapple100.core.character.mapToCharacterCardModels
import ru.mrapple100.core.character.mapToCharacterEntities
import ru.mrapple100.core.character.mapToCharacterModel
import ru.mrapple100.core.character.mapToCharacterModels
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.roundToInt

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

    //old variant
    override suspend fun getCharacterById(id:Int): Flow<CharacterModel?> {
        var resultFlow = flow<CharacterModel?>{emit(null)}

        if(isOnline.get()) {
            val result = characterRemoteDataSource.getCharacter(id)
            result.handle(
                onSuccess = { character ->
                    isOnline.set(true)
                    resultFlow = flow {
                        emit(character.mapToCharacterModel())
                    }
                },
                onError = {
                    isOnline.set(false)

                    resultFlow = getLocalCharacterById(id)
                }
            )
        }else{
            resultFlow = getLocalCharacterById(id)
        }
        return resultFlow
    }

    override suspend fun getLocalCharacterById(id: Int): Flow<CharacterModel?> = flow{
        val result = characterLocalDataSource.getCharacterById(id)
        Log.d("CHECKCHECK",result.character.toString())
        emit(result.mapToCharacterModel())

    }
    override suspend fun getLocalCharacterCardById(id: Int): Flow<CharacterCardModel?> = flow{
        val result = characterLocalDataSource.getCharacterById(id)
        Log.d("CHECKCHECK",result.character.toString())
        emit(result.mapToCharacterCardModel())

    }
    override suspend fun getCharacterCardById(id:Int): Flow<CharacterCardModel?> {
        var resultFlow = flow<CharacterCardModel?>{emit(null)}

        if(isOnline.get()) {
            val result = characterRemoteDataSource.getCharacter(id)
            result.handle(
                onSuccess = { character ->
                    isOnline.set(true)
                    resultFlow = flow {
                        emit(character.mapToCharacterCardModel())
                    }
                },
                onError = {
                    isOnline.set(false)

                    resultFlow = getLocalCharacterCardById(id)
                }
            )
        }else{
            resultFlow = getLocalCharacterCardById(id)
        }
        return resultFlow
    }

    override suspend fun getLocalCharacters(): Flow<List<CharacterCardModel>?> = flow {
        val page = characterMemoryDataSource.getPage()

        var maxPage = characterMemoryDataSource.getMaxLocalPage()
        if(maxPage>=page) {
            emit(characterLocalDataSource.getCharacters(page).mapToCharacterCardModels())
        }else{
            emit(null)
        }

    }

    override suspend fun resetIsOnlineStatus(){
        isOnline.set(true)
    }

    override suspend fun getMaxCountCharacters(): Int {
        return if (isOnline.get()){
            getMaxRemoteCountCharacters()
        }else{
            getMaxLocalCountCharacters()
        }
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
                        val remotePage = resultAll.info.pages
                        characterMemoryDataSource.setMaxRemotePage(remotePage)

                        val remoteСount = resultAll.info.count
                        Log.d("REMOTECOUNT", remoteСount.toString())

                        characterMemoryDataSource.setMaxRemoteCountCharacters(remoteСount)


                        val chsModels = resultAll.results.mapToCharacterModels()
                        val chsEntities = chsModels.mapToCharacterEntities(page)
                        val chCardModels = resultAll.results.mapToCharacterCardModels()

                        characterLocalDataSource.insertCharacters(chsEntities)

                        var maxLocalPage = characterMemoryDataSource.getMaxLocalPage()
                        maxLocalPage = max(page, maxLocalPage)
                        val countOnPage = (remoteСount.toDouble()/(remotePage)).roundToInt()
                        if(remoteСount >= maxLocalPage * countOnPage){
                            characterMemoryDataSource.setMaxLocalCountCharacters(maxLocalPage * countOnPage)
                        }else{
                            characterMemoryDataSource.setMaxLocalCountCharacters(remoteСount)
                        }
                        characterMemoryDataSource.setMaxLocalPage(maxLocalPage)
                        emit(chCardModels)
                    }
                },
                onError = {
                    Log.d("REMOTECOUNT", "MEOWLOCALERROR")

                    isOnline.set(false)
                    resultFlow = getLocalCharacters()
                }
            )
        }else{
            Log.d("REMOTECOUNT", "MEOWLOCAL")

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

    override suspend fun getMaxRemoteCountCharacters():Int{
        return characterMemoryDataSource.getMaxRemoteCountCharacters()
    }
    override suspend fun getMaxLocalCountCharacters():Int{
        return characterMemoryDataSource.getMaxLocalCountCharacters()
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

    //OnBoarding
    override suspend fun getIsFirstTimeOnBoardingGame(): Boolean{
        return  characterMemoryDataSource.getIsFirstTimeOnBoardingGame()
    }
    override suspend fun setFirstTimeOnBoardingGameEnd(){
        characterMemoryDataSource.setIsFirstTimeOnBoardingGameEnd()
    }


}