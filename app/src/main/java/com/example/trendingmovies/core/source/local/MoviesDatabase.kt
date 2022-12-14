package com.example.trendingmovies.core.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trendingmovies.core.source.local.models.ConfigurationEntity
import com.example.trendingmovies.core.source.local.models.MovieDetailsEntity
import com.example.trendingmovies.core.source.local.models.TrendingMoviesEntity
import com.example.trendingmovies.core.source.local.models.TrendingMoviesPageEntity

@Database(
    entities = [TrendingMoviesEntity::class, MovieDetailsEntity::class,
        ConfigurationEntity::class, TrendingMoviesPageEntity::class],
    version = DATABASE_VERSION
)
@TypeConverters(
    TrendingMoviesConverter::class,
    IntListConverter::class,
    GenreConverter::class,
    StringListConverter::class,
    ProductionCountryListConverter::class,
    ProductionCompanyListConverter::class,
    SpokenLanguageListConverter::class
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun trendingMoviesDao(): TrendingMoviesDao
    abstract fun configurationDao(): ConfigurationDao
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun trendingMoviesPageDao(): TrendingMoviesPageDao
}

private const val DATABASE_VERSION = 1