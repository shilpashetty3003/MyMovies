package com.example.mvvm_hilt_paging_flow.data.remote

import com.example.mvvm_hilt_paging_flow.data.remote.model.MovieResponse
import com.example.mvvm_hilt_paging_flow.data.repository.movies.MovieRemoteDataSource
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService :MovieRemoteDataSource{


    @GET("movie/{id}")
    override suspend fun getMovie(@Path("id") id:Long): MovieResponse


}