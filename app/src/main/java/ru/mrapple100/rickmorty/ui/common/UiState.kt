package ru.mrapple100.rickmorty.ui.common

sealed class UiStatus {
    object OnBoarding: UiStatus()
    object Loading : UiStatus()
    object ScrollLoading: UiStatus()
    object Success : UiStatus()
    data class Failed(val message: String = "") : UiStatus()
}