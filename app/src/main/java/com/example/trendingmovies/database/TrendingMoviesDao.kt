package com.example.trendingmovies.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: TrendingMoviesEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(moviesEntity: List<TrendingMoviesEntity>) //TODO: Do we need long return here?

    @Query("SELECT * FROM TrendingMoviesEntity")
    suspend fun getAllMoviesSync(): List<TrendingMoviesEntity>
}