package ru.mrapple100.domain.character.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import ru.mrapple100.domain.character.model.CharacterCardModel
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Inject
import kotlin.math.max
import kotlin.random.Random

class LoadRandomTwoCharacterUseCase  @Inject constructor(
    val characterRepository: CharacterRepository
){
    suspend operator fun invoke(): Flow<Pair<CharacterCardModel?, CharacterCardModel?>> {
        val maxCount = characterRepository.getMaxCountCharacters()
        val localRange = Pair<Int,Int>(0, maxCount)
        val random = Random(System.currentTimeMillis())
        val firstId = random.nextInt(localRange.first,localRange.second)
        var secondId:Int = 0
        do {
            secondId = random.nextInt(localRange.first,localRange.second)
        }while (firstId==secondId)

        val firstChFlow = characterRepository.getCharacterCardById(firstId)
        val secondChFlow = characterRepository.getCharacterCardById(secondId)

        return flow {
            var firstCh: CharacterCardModel? = null

            firstChFlow.collect{ ch ->
                firstCh = ch!!
            }
            var secondCh: CharacterCardModel? = null
             secondChFlow.collect{ ch ->
                secondCh = ch!!
            }
            emit(Pair(firstCh,secondCh))

        }
    }
}