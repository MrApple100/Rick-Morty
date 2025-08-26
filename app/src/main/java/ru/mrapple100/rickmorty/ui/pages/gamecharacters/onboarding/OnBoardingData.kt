package ru.mrapple100.rickmorty.ui.pages.gamecharacters.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString

data class OnBoardingData(
    val image: Int,
    val animContent: @Composable() (() -> Unit)? = null,
    val title: String, val desc: AnnotatedString)

