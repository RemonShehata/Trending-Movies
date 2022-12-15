package com.example.trendingmovies.di

import android.content.Context
import androidx.room.Room
import com.example.trendingmovies.database.*
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

    @Singleton
    @Provides
    fun provideTrendingMoviesDao(moviesDatabase: MoviesDatabase): TrendingMoviesDao{
        return moviesDatabase.trendingMoviesDao()
    }

    @Singleton
    @Provides
    fun provideConfigurationDao(moviesDatabase: MoviesDatabase): ConfigurationDao {
        return moviesDatabase.configurationDao()
    }

    @Singleton
    @Provides
    fun provideMovieDetailsDao(moviesDatabase: MoviesDatabase): MovieDetailsDao {
        return moviesDatabase.movieDetailsDao()
    }

    @Singleton
    @Provides
    fun provideTrendingMoviesPageDao(moviesDatabase: MoviesDatabase): TrendingMoviesPageDao {
        return moviesDatabase.trendingMoviesPageDao()
    }

    companion object {
        private const val MOVIES_DATABASE_NAME = "movies_database.db"
    }
}