package ru.mrapple100.core.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mrapple100.core.character.datasource.CharacterMemoryDataSource
import ru.mrapple100.core.character.datasource.local.AppDatabase
import ru.mrapple100.core.character.datasource.local.CharacterLocalDataSource
import ru.mrapple100.core.character.datasource.local.dao.CharacterDao
import ru.mrapple100.core.character.datasource.local.dao.ImageDao
import ru.mrapple100.core.character.datasource.remote.CharacterRemoteDataSource
import ru.mrapple100.core.character.datasource.remote.service.CharacterService
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY  // 🔍 Logs HTTP requests & responses
        }
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    internal fun providesRetrofitService(okHttpClient: OkHttpClient): CharacterService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
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
    internal fun providesImageDao(appDatabase: AppDatabase): ImageDao {
        return appDatabase.imageDao()
    }

    @Singleton
    @Provides
    fun providesCharacterRemoteDataSource(service: CharacterService):CharacterRemoteDataSource{
        return CharacterRemoteDataSource(service)
    }

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("preferences_name", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providesCharacterMemoryDataSource(sharedPreferences: SharedPreferences): CharacterMemoryDataSource {
        return CharacterMemoryDataSource(sharedPreferences)
    }
    @Singleton
    @Provides
    fun providesCharacterLocalDataSource(characterDao: CharacterDao,
                                                  imageDao: ImageDao): CharacterLocalDataSource {
        return CharacterLocalDataSource(
            characterDao,
            imageDao
        )
    }

}