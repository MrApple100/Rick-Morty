package ru.mrapple100.core.character.datasource.remote

sealed class ApiState<out T: Any> {
    data object Empty : ApiState<Nothing>()
    data object Requesting : ApiState<Nothing>()
    data class Success<out T : Any>(val ret:T) : ApiState<T>()
    data class Error(val responseCode: Int,val errorResponse:String) : ApiState<Nothing>()
    data class Exception(val e: Throwable) : ApiState<Nothing>(){
        val errorCode = 999
    }
}