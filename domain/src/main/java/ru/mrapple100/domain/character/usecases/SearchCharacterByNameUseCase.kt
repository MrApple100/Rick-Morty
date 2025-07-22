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
        val maxLocalPage = characterRepository.getMaxLocalPage()
        return if(characterRepository.getCurrentPage()<maxLocalPage){
            if (searchText.isEmpty()) {
                characterRepository.getLocalCharacters()
            } else {
                characterRepository.getLocalCharacters().map { it.filter{ ch -> ch.name.contains(searchText) }}
            }
        }else{
             if (searchText.isEmpty()) {
                characterRepository.fetchAndSaveCharacters()
            } else {
                characterRepository.fetchAndSaveCharacters().map { it.filter{ ch -> ch.name.contains(searchText) }}
            }
        }
    }
}