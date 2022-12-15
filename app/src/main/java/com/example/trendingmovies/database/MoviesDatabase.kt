package com.example.trendingmovies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [TrendingMoviesEntity::class, MovieDetailsEntity::class, ConfigurationEntity::class],
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
}

private const val DATABASE_VERSION = 1