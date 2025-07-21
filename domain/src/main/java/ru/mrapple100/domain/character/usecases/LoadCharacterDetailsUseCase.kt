package ru.mrapple100.domain.character.usecases

import kotlinx.coroutines.flow.Flow
import ru.mrapple100.domain.character.model.CharacterModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject

class LoadCharacterDetailsUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(id:Int):Flow<CharacterModel>{
        return characterRepository.getCharacterById(id)
    }
}