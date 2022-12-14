package com.example.trendingmovies.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trendingmovies.database.MoviesDatabaseFactory.DATABASE_VERSION

@Database(
    entities = [TrendingMoviesEntity::class, MovieDetailsEntity::class],
    version = DATABASE_VERSION
)
@TypeConverters(
    TrendingMoviesConverter::class,
    GenreIdsConverter::class,
    GenreConverter::class
)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun trendingMoviesDao(): TrendingMoviesDao
}

object MoviesDatabaseFactory {
    private const val MOVIES_DATABASE_NAME = "movies_database.db"
    const val DATABASE_VERSION = 1

    @Volatile
    private var instance: MoviesDatabase? = null

    fun buildNewsDatabaseProvider(context: Context): MoviesDatabase {
        return instance ?: kotlin.run {
            synchronized(this) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java, MOVIES_DATABASE_NAME
                ).build()
                instance!!
            }
        }
    }
}