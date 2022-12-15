package com.example.trendingmovies.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.IDN

@Entity
class TrendingMoviesPageEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = false) val id: Int = 0,
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "total_pages") val totalPages: Int?,
)
