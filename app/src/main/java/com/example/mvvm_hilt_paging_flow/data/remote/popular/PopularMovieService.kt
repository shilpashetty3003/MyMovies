package com.example.mvvm_hilt_paging_flow.data.remote.popular

import com.example.mvvm_hilt_paging_flow.data.remote.model.MoviePagedListResponse
import com.example.mvvm_hilt_paging_flow.data.repository.popular.PopularMoviesRemoteDataSource
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMovieService : PopularMoviesRemoteDataSource {

    @GET("discover/movie?sort_by=popularity.desc")
    override suspend fun getPopularMovies(@Query("page") page: Int): MoviePagedListResponse
}