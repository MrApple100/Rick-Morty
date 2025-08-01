package ru.mrapple100.domain.character.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject

class ScrollUpPageUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(): Flow<List<CharacterCardModel>?> {

        return if(1<characterRepository.getCurrentPage()) {
            characterRepository.downPage()
            characterRepository.fetchAndSaveCharacters()
        }else{
            flow{emit(null)}
        }
    }
}