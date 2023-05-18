package com.example.mvvm_hilt_paging_flow.data.repository.search

import com.example.mvvm_hilt_paging_flow.data.remote.model.MoviePagedListResponse

interface SearchRemoteDataSource {

    suspend fun searchMovies(query:String,page:Int):MoviePagedListResponse
}