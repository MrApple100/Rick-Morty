package ru.mrapple100.core.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mrapple100.core.character.datasource.CharacterMemoryDataSource
import ru.mrapple100.core.character.datasource.local.CharacterLocalDataSource
import ru.mrapple100.core.character.datasource.remote.CharacterRemoteDataSource
import ru.mrapple100.core.character.repository.CharacterRepositoryImpl
import ru.mrapple100.domain.character.repository.CharacterRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

//    @Binds
//    internal abstract fun bindsCharacterRepository(
//        characterRepositoryImpl: CharacterRepositoryImpl
//    ): CharacterRepository

    @Singleton
    @Provides
    fun provideCharacterRepository(
        remote: CharacterRemoteDataSource,
        memory: CharacterMemoryDataSource,
        local: CharacterLocalDataSource
    ): CharacterRepository {
        return CharacterRepositoryImpl(remote,memory, local)
    }


}