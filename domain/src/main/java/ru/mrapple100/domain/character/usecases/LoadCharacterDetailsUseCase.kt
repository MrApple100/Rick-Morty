package ru.mrapple100.domain.character.usecases

import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository

class LoadCharacterDetailsUseCase(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(id:Int):CharacterModel{
        characterRepository.
    }
}