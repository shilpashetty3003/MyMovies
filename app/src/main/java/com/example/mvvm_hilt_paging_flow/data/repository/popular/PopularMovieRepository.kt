package com.example.mvvm_hilt_paging_flow.data.repository.popular

import androidx.paging.*
import com.example.mvvm_hilt_paging_flow.data.remote.NETWORK_PAGE_SIZE
import com.example.mvvm_hilt_paging_flow.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PopularMovieRepository @Inject constructor(
    private val localDS:PopularMoviesLocalDataSource,
    private val remoteDS:PopularMoviesRemoteDataSource
){

    @OptIn(ExperimentalPagingApi::class)
    fun getPopularMovies() = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
        remoteMediator = PopularMoviesPagerMediator(localDS,remoteDS),
        pagingSourceFactory = {
            localDS.getAll()
        }
    ).flow.map { pagingData ->
        pagingData.map {
            it.toMovie()
        }
    }
}