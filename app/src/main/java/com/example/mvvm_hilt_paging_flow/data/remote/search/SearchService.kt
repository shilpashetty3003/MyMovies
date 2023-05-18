package com.example.mvvm_hilt_paging_flow.data.remote.search

import com.example.mvvm_hilt_paging_flow.data.remote.model.MoviePagedListResponse
import com.example.mvvm_hilt_paging_flow.data.repository.search.SearchRemoteDataSource
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService:SearchRemoteDataSource {


    @GET("search/movie?sort_by=popularity.desc")
    override suspend fun searchMovies(@Query("query") query: String,
                                      @Query("page") page: Int,): MoviePagedListResponse

}


