package com.example.mvvm_hilt_paging_flow.data.repository.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import com.example.mvvm_hilt_paging_flow.data.local.search.SearchKeysEntity
import com.example.mvvm_hilt_paging_flow.data.remote.STARTING_PAGE_INDEX
import com.example.mvvm_hilt_paging_flow.domain.model.utils.toIIError
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class SearchMediator(
    private val query:String,
    private val localDs: SearchLocalDataSource,
    private val remoteDS: SearchRemoteDataSource
) : RemoteMediator<Int, MovieEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val pageKey = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKey = getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKey = getRemoteKeyForFirstItem(state)
                remoteKey?.preKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
            LoadType.APPEND -> {
                val remoteKey = getRemoteKeyForLastItem(state)
                remoteKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
        }

        return try {
            val api=remoteDS.searchMovies(query,pageKey)
            localDs.cacheResponse(api,pageKey,loadType== LoadType.REFRESH)
            return  MediatorResult.Success(endOfPaginationReached = api.total_pages >= pageKey)
        } catch (e: HttpException) {

            return MediatorResult.Error(e.toIIError())
        } catch (e: Exception) {
            MediatorResult.Error(e.toIIError())
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): SearchKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie -> localDs.getSearchKeysForMoviesID(movie.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): SearchKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie -> localDs.getSearchKeysForMoviesID(movie.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): SearchKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)
                ?.id?.let { movieId -> localDs.getSearchKeysForMoviesID(movieId) }
        }
    }
}

