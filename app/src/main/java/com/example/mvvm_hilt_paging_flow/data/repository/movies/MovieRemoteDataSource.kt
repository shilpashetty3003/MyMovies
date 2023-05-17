package com.example.mvvm_hilt_paging_flow.data.repository.movies

import com.example.mvvm_hilt_paging_flow.data.remote.model.MovieResponse

interface MovieRemoteDataSource {

    suspend fun getMovie(id:Long):MovieResponse
}