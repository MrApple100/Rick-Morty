package ru.mrapple100.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mrapple100.core.character.datasource.local.CharacterLocalDataSource
import ru.mrapple100.core.character.datasource.remote.CharacterRemoteDataSource
import ru.mrapple100.core.character.repository.CharacterRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    internal fun providesCharacterRepository(characterRemoteDataSource: CharacterRemoteDataSource,
                                    characterLocalDataSource: CharacterLocalDataSource
    ):CharacterRepositoryImpl{
        return CharacterRepositoryImpl(characterRemoteDataSource,
                                        characterLocalDataSource)
    }
}