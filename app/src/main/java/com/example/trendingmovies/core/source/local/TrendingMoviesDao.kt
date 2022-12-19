package com.example.trendingmovies.core.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trendingmovies.core.source.local.models.TrendingMoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movieEntity: TrendingMoviesEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(moviesEntity: List<TrendingMoviesEntity>) //TODO: Do we need long return here?

    @Query("SELECT * FROM TrendingMoviesEntity ORDER BY timestamp_millis ASC")
    suspend fun getAllMoviesSync(): List<TrendingMoviesEntity>

    @Query("SELECT * FROM TrendingMoviesEntity ORDER BY timestamp_millis ASC")
    fun getAllMoviesFlow(): Flow<List<TrendingMoviesEntity>>
}