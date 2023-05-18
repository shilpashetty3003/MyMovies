package com.example.mvvm_hilt_paging_flow.data.repository.search

import androidx.paging.PagingSource
import androidx.room.Transaction
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import com.example.mvvm_hilt_paging_flow.data.local.search.SearchKeysEntity
import com.example.mvvm_hilt_paging_flow.data.remote.model.MoviePagedListResponse

interface SearchLocalDataSource {

    fun searchMovies(queryString:String):PagingSource<Int,MovieEntity>

    suspend fun getSearchKeysForMoviesID(movieId:Long):SearchKeysEntity?

    @Transaction
    suspend fun cacheResponse(response: MoviePagedListResponse,pageKey:Int,isRefresh:Boolean)
}