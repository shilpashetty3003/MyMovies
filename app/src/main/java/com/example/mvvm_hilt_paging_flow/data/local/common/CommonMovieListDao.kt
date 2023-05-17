package com.example.mvvm_hilt_paging_flow.data.local.common

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity

interface CommonMovieListDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies:List<MovieEntity>)
}