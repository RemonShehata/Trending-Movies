package com.example.trendingmovies.core.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trendingmovies.core.source.local.models.MovieDetailsEntity

@Dao
interface MovieDetailsDao {

    @Insert
    fun insertMovieDetails(movieDetailsEntity: MovieDetailsEntity): Long

    @Query("SELECT * FROM MovieDetailsEntity WHERE id = :movieId LIMIT 1")
    fun getMovieById(movieId: String): MovieDetailsEntity?
}
