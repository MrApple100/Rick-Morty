package ru.mrapple100.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mrapple100.core.character.datasource.local.AppDatabase
import ru.mrapple100.core.character.datasource.local.CharacterLocalDataSource
import ru.mrapple100.core.character.datasource.local.dao.CharacterDao
import ru.mrapple100.core.character.datasource.remote.CharacterRemoteDataSource
import ru.mrapple100.core.character.datasource.remote.service.CharacterService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {


    @Singleton
    @Provides
    internal fun providesRetrofitService(): CharacterService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://rickandmortyapi.com/api/")
            .build()

        return retrofit.create(CharacterService::class.java);
    }

    @Singleton
    @Provides
   internal fun providesDatabase(@ApplicationContext context:Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration(true) //только для дебага
            .build();
    }

    @Singleton
    @Provides
    internal fun providesCharacterDao(appDatabase: AppDatabase):CharacterDao{
        return appDatabase.characterDao()
    }

    @Singleton
    @Provides
    internal fun providesCharacterRemoteDataSource(service: CharacterService):CharacterRemoteDataSource{
        return CharacterRemoteDataSource(service)
    }

    @Singleton
    @Provides
    internal fun providesCharacterLocalDataSource(characterDao: CharacterDao): CharacterLocalDataSource {
        return CharacterLocalDataSource(characterDao)
    }

}