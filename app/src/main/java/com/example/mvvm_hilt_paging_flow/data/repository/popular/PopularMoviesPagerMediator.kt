package com.example.mvvm_hilt_paging_flow.data.repository.popular

import android.provider.MediaStore.Audio.Media
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import com.example.mvvm_hilt_paging_flow.data.local.popular.PopularMoviesKeyEntity
import com.example.mvvm_hilt_paging_flow.data.remote.STARTING_PAGE_INDEX
import com.example.mvvm_hilt_paging_flow.domain.model.Movie
import com.example.mvvm_hilt_paging_flow.domain.model.utils.NetworkError
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PopularMoviesPagerMediator (
    private val local:PopularMoviesLocalDataSource,
    private val remote:PopularMoviesRemoteDataSource
): RemoteMediator<Int,MovieEntity>(){
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val pageKey=when(loadType){
            LoadType.REFRESH -> {
                val remoteKey=getRemoteKeyClosestToCurrentPosition(state)
                remoteKey?.nextKey?.minus(1)?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKey=getRemoteKeyForFirstItem(state)
                remoteKey?.preKey?:return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
            LoadType.APPEND ->{
                val remoteKey=getRemoteKeyForLastItem(state)
                remoteKey?.nextKey?:return MediatorResult.Success(endOfPaginationReached = remoteKey != null)
            }
        }
        return  try{
            val apiResponse=remote.getPopularMovies(pageKey)
            local.cacheResponse(apiResponse,pageKey,loadType==LoadType.REFRESH)
            MediatorResult.Success(endOfPaginationReached = pageKey >= apiResponse.total_pages)
        }catch (exception:Exception){
            MediatorResult.Error(com.example.mvvm_hilt_paging_flow.domain.model.utils.UnknownError(exception))
        }
        catch (exception:HttpException){
            MediatorResult.Error(NetworkError(exception))
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): PopularMoviesKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)
                ?.id?.let { movieId -> local.getRemoteKeysForMovieId(movieId) }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): PopularMoviesKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie -> local.getRemoteKeysForMovieId(movie.id) }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): PopularMoviesKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie -> local.getRemoteKeysForMovieId(movie.id) }
    }
}