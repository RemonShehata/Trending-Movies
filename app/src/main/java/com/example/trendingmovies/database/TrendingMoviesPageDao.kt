package com.example.trendingmovies.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TrendingMoviesPageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(trendingMoviesPageEntity: TrendingMoviesPageEntity): Long

    @Query("SELECT * FROM TrendingMoviesPageEntity")
    suspend fun getPageSync(): TrendingMoviesPageEntity

    @Query("DELETE FROM TrendingMoviesPageEntity")
    suspend fun deleteAll()
}