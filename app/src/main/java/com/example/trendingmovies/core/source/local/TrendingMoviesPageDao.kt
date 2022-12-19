package com.example.trendingmovies.core.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.trendingmovies.core.source.local.models.TrendingMoviesPageEntity

@Dao
interface TrendingMoviesPageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(trendingMoviesPageEntity: TrendingMoviesPageEntity): Long

    @Query("SELECT * FROM TrendingMoviesPageEntity")
    suspend fun getPageSync(): TrendingMoviesPageEntity?

    @Query("DELETE FROM TrendingMoviesPageEntity")
    suspend fun deleteAll()
}