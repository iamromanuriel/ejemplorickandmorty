package com.romanuriel.coroutines.di

import android.content.Context
import androidx.room.Room
import com.romanuriel.coroutines.data.api.ApiService
import com.romanuriel.coroutines.data.database.DaoCharacters
import com.romanuriel.coroutines.data.database.RickAndMortyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleApi {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"

    @Singleton
    @Provides
    fun providerApi(): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            //.client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideClientForPlatform(): OkHttpClient {
        val headerInterceptor = HttpLoggingInterceptor()
        headerInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        val bodyInterceptor = HttpLoggingInterceptor()
        bodyInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(bodyInterceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun provederApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesCharactersDatabase(@ApplicationContext context: Context): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            "characters_db"
        ).build()
    }
    @Provides
    fun providesCharactersDao(rickAndMortyDatabase: RickAndMortyDatabase): DaoCharacters {
        return rickAndMortyDatabase.getCharactersDao()
    }

}