package com.example.mvvm_hilt_paging_flow.data.repository.search

import androidx.paging.*
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import com.example.mvvm_hilt_paging_flow.data.remote.NETWORK_PAGE_SIZE
import com.example.mvvm_hilt_paging_flow.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepository @Inject constructor(
    val localDS:SearchLocalDataSource,
    val remoteDS:SearchRemoteDataSource
) {
    @OptIn(ExperimentalPagingApi::class)
    fun search(query:String)= Pager(
        config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = {localDS.searchMovies(query.toSearchQuery())},
        remoteMediator = SearchMediator(query,localDS,remoteDS)
    ).flow.map {pagingData->
           pagingData.map { it.toMovie() }
    }

    companion object {
        fun String.toSearchQuery() = "%${this.replace(' ', '%')}%"
    }
}