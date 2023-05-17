package com.example.mvvm_hilt_paging_flow.data.repository.popular

import com.example.mvvm_hilt_paging_flow.data.remote.model.MoviePagedListResponse

interface PopularMoviesRemoteDataSource {

    suspend fun getPopularMovies(page:Int):MoviePagedListResponse
}