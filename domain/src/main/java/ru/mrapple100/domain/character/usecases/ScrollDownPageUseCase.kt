package ru.mrapple100.domain.character.usecases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject

class ScrollDownPageUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(): Flow<List<CharacterCardModel>?> {
        val maxPage = characterRepository.getMaxPage()
        val currentPage = characterRepository.getCurrentPage()

        return if (maxPage > currentPage) {
            characterRepository.upPage()
            characterRepository.fetchAndSaveCharacters()
        } else {
            flow { emit(null) }
        }
    }
}