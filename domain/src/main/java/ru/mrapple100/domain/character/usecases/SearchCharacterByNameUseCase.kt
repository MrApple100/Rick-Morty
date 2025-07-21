package ru.mrapple100.domain.character.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject


class SearchCharacterByNameUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(searchText: String): Flow<List<CharacterModel>> {
        return if (searchText.isEmpty()) {
            characterRepository.getCharacters()
        } else {
            characterRepository.getCharacters().map { it.filter{ ch -> ch.name.contains(searchText) }}
        }
    }
}