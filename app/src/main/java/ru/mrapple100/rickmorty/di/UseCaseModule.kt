package ru.mrapple100.rickmorty.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mrapple100.domain.character.repository.CharacterRepository
import ru.mrapple100.domain.character.usecases.LoadCharacterDetailsUseCase
import ru.mrapple100.domain.character.usecases.SearchCharacterByNameUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesSearchCharacterByNameUseCase(characterRepository: CharacterRepository):SearchCharacterByNameUseCase{
        return SearchCharacterByNameUseCase(characterRepository)
    }
    @Provides
    @Singleton
    fun providesLoadCharacterDetailsUseCase(characterRepository: CharacterRepository):LoadCharacterDetailsUseCase{
        return LoadCharacterDetailsUseCase(characterRepository)
    }
}