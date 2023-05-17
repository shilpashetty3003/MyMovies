package com.example.mvvm_hilt_paging_flow.data.repository.popular

import androidx.paging.PagingSource
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import com.example.mvvm_hilt_paging_flow.data.local.popular.PopularMoviesKeyEntity
import com.example.mvvm_hilt_paging_flow.data.remote.model.MoviePagedListResponse

interface PopularMoviesLocalDataSource {

    fun getAll():PagingSource<Int,MovieEntity>
    suspend fun getRemoteKeysForMovieId(id:Long):PopularMoviesKeyEntity?
    suspend fun cacheResponse(response: MoviePagedListResponse,pageKey:Int,isRefresh:Boolean)
}

