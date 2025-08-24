package ru.mrapple100.domain.character.usecases

import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject

class EndFirstOnBoardingGameUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke():Boolean {
        return characterRepository.getIsFirstTimeOnBoardingGame()
    }
    suspend fun endOnBoarding() {
        characterRepository.setFirstTimeOnBoardingGameEnd()
    }
}