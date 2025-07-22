package ru.mrapple100.domain.character.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject

class ScrollDownPageUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(): Flow<List<CharacterModel>?> {
        val maxLocalPage = characterRepository.getMaxLocalPage()

        return if(maxLocalPage>characterRepository.getCurrentPage()) {
            characterRepository.upPage()
            characterRepository.getCharacters()
        }else{
            flow{emit(null)}
       }
    }
}