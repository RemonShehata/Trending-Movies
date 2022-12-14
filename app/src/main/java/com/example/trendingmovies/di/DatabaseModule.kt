package com.example.trendingmovies.di

import android.content.Context
import androidx.room.Room
import com.example.trendingmovies.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDatabase(@ApplicationContext applicationContext: Context): MoviesDatabase {
        return Room.databaseBuilder(
            applicationContext.applicationContext,
            MoviesDatabase::class.java, MOVIES_DATABASE_NAME
        ).build()
    }

    companion object {
        private const val MOVIES_DATABASE_NAME = "movies_database.db"
    }
}