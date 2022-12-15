package com.example.trendingmovies.di

import com.example.trendingmovies.*
import com.example.trendingmovies.database.ConfigurationDao
import com.example.trendingmovies.database.MovieDetailsDao
import com.example.trendingmovies.database.TrendingMoviesDao
import com.example.trendingmovies.database.TrendingMoviesPageDao
import com.example.trendingmovies.network.MoviesApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun provideMoviesListRepo(
        trendingMoviesDao: TrendingMoviesDao,
        trendingMoviesPageDao: TrendingMoviesPageDao,
        moviesApi: MoviesApi
    ): TrendingMoviesRepo {
        return TrendingMoviesRepository(trendingMoviesDao, trendingMoviesPageDao, moviesApi)
    }

    @Singleton
    @Provides
    fun provideConfigurationRepo(
        configurationDao: ConfigurationDao,
        moviesApi: MoviesApi
    ): ConfigurationRepo {
        return ConfigurationRepository(moviesApi, configurationDao)
    }

    @Singleton
    @Provides
    fun provideMovieDetailsRepo(
        movieDetailsDao: MovieDetailsDao,
        moviesApi: MoviesApi
    ): MovieDetailsRepo {
        return MovieDetailsRepository(moviesApi, movieDetailsDao)
    }
}
