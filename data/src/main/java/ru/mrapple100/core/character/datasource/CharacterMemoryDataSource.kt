package ru.mrapple100.core.character.datasource

import android.content.SharedPreferences
import androidx.core.content.edit

class CharacterMemoryDataSource(
    private val sharedPreferences: SharedPreferences
) {
    suspend fun getPage():Int{
        val sp = sharedPreferences
        return sp.getInt("page",1)

    }
    suspend fun setPage(page:Int){
        sharedPreferences.edit(commit = true) {
            putInt("page", page)
        }
    }
    suspend fun clearSharedPreference(){
        sharedPreferences.edit(commit = true) { clear() }
    }
    suspend fun getMaxLocalPage():Int{
        val sp = sharedPreferences
        return sp.getInt("maxLocalPage",0)
    }
    suspend fun setMaxLocalPage(maxPage:Int){
        sharedPreferences.edit(commit = true){
            putInt("maxLocalPage", maxPage)
        }
    }
    suspend fun getMaxRemotePage():Int{
        val sp = sharedPreferences
        return sp.getInt("maxRemotePage",0)
    }
    suspend fun setMaxRemotePage(maxPage:Int){
        sharedPreferences.edit(commit = true){
            putInt("maxRemotePage", maxPage)
        }
    }

    suspend fun getMaxRemoteCountCharacters(): Int {
        val sp = sharedPreferences
        return sp.getInt("maxRemoteCountCharacters",0)
    }
    suspend fun setMaxRemoteCountCharacters(maxCountCharacters:Int){
        sharedPreferences.edit(commit = true){
            putInt("maxRemoteCountCharacters", maxCountCharacters)
        }
    }

    suspend fun getMaxLocalCountCharacters(): Int {
        val sp = sharedPreferences
        return sp.getInt("maxLocalCountCharacters",0)
    }
    suspend fun setMaxLocalCountCharacters(maxCountCharacters:Int){
        sharedPreferences.edit(commit = true){
            putInt("maxLocalCountCharacters", maxCountCharacters)
        }
    }

    //onBoarding
    suspend fun getIsFirstTimeOnBoardingGame(): Boolean {
        val sp = sharedPreferences
        return sp.getBoolean("isFirstTimeOnBoardingGame",true)
    }
    suspend fun setIsFirstTimeOnBoardingGameEnd(){
        sharedPreferences.edit(commit = true){
            putBoolean("isFirstTimeOnBoardingGame", false)
        }
    }

    fun setIsFirstTimeOnBoardingGameStart() {
        sharedPreferences.edit(commit = true){
            putBoolean("isFirstTimeOnBoardingGame", true)
        }
    }
}