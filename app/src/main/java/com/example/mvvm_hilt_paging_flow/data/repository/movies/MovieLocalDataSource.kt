package com.example.mvvm_hilt_paging_flow.data.repository.movies

import android.util.Log
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {

    fun getMovieFlow(id:Long):Flow<MovieEntity?>
    suspend fun insert(movieEntity: MovieEntity)

}