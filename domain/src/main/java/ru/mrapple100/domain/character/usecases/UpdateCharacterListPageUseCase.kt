package ru.mrapple100.domain.character.usecases

import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject

class UpdateCharacterListPageUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(){
        characterRepository.currentPageToOne()
        characterRepository.resetIsOnlineStatus()

    }
}