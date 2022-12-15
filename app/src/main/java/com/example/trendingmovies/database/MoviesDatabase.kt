package com.example.trendingmovies.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trendingmovies.ConfigurationDao

@Database(
    entities = [TrendingMoviesEntity::class, MovieDetailsEntity::class, ConfigurationEntity::class],
    version = DATABASE_VERSION
)
@TypeConverters(
    TrendingMoviesConverter::class,
    GenreIdsConverter::class,
    GenreConverter::class,
    StringListConverter::class
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun trendingMoviesDao(): TrendingMoviesDao
    abstract fun configurationDao(): ConfigurationDao
}

private const val DATABASE_VERSION = 1