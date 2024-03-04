package com.example.tmdb.di

import android.app.Application
import androidx.room.Room
import com.example.tmdb.api.MovieService
import com.example.tmdb.database.FavouritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MovieServiceModule {

    const val BASE_URL = "https://api.themoviedb.org/"

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit) : MovieService {
        return retrofit.create(MovieService::class.java)
    }

    @Singleton
    @Provides
    fun provideFavouritesDatabase(application: Application): FavouritesDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            FavouritesDatabase::class.java,
            "Favourites Database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideFavouritesDao(favouritesDatabase: FavouritesDatabase) = favouritesDatabase.favouritesDao()
}