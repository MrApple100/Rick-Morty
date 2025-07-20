package ru.mrapple100.domain.character.usecases

import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository


class SearchCharacterByNameUseCase(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(searchText: String): List<CharacterModel> {
        return if (searchText.isEmpty()) {
            characterRepository.getCharacters()
        } else {
            characterRepository.getCharacters().filter { it.name.contains(searchText) }
        }
    }
}