package ru.mrapple100.domain.character.usecases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject


class SearchCharacterByNameUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(searchText: String): Flow<List<CharacterCardModel>?> {
        characterRepository.currentPageToOne()
        var maxPage = characterRepository.getMaxPage()
        var currentPage = characterRepository.getCurrentPage()
        var countCh = 0
        var isFirstTime = true
        return if (searchText.isEmpty()) {
                characterRepository.fetchAndSaveCharacters()
            } else {
            val sumFlow = flow {
                val sumList = emptyList<CharacterCardModel>().toMutableList()

                do {
                    if (!isFirstTime) {
                        characterRepository.upPage()
                        currentPage = characterRepository.getCurrentPage()
                        maxPage = characterRepository.getMaxPage()
                    }
                    val chs = characterRepository.fetchAndSaveCharacters().map { it?.filter { ch -> ch.name.contains(searchText) } }

                    chs.collect{ chList ->
                        kotlinx.coroutines.delay(100)
                        sumList += (chList!!)
                        countCh = sumList.size

                    }

                    isFirstTime = false

                } while (countCh < 60 && maxPage > currentPage)
                this.emit(sumList)

            }
            sumFlow
            }

    }
}