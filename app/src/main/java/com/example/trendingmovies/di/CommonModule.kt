package com.example.trendingmovies.di

import android.content.Context
import com.example.trendingmovies.*
import com.example.trendingmovies.core.source.local.ConfigurationDao
import com.example.trendingmovies.core.source.local.MovieDetailsDao
import com.example.trendingmovies.core.source.local.TrendingMoviesDao
import com.example.trendingmovies.core.source.local.TrendingMoviesPageDao
import com.example.trendingmovies.core.source.remote.MoviesApi
import com.example.trendingmovies.core.source.repos.*
import com.example.trendingmovies.utils.NetworkStateMonitor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideNetworkStateMonitor(@ApplicationContext applicationContext: Context): NetworkStateMonitor {
        return NetworkStateMonitor(applicationContext)
    }
}
