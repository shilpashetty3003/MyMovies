package com.example.mvvm_hilt_paging_flow.data.local.popular

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mvvm_hilt_paging_flow.data.local.common.CommonMovieListDao
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import com.example.mvvm_hilt_paging_flow.data.remote.STARTING_PAGE_INDEX
import com.example.mvvm_hilt_paging_flow.data.remote.model.MoviePagedListResponse
import com.example.mvvm_hilt_paging_flow.data.repository.popular.PopularMoviesLocalDataSource

@Dao
interface PopularMovieDao:CommonMovieListDao,PopularMoviesLocalDataSource {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAll(remoteKey:List<PopularMoviesKeyEntity>)


   @Query("select * from popular_movies_keys where movieID=:id")
    override suspend fun getRemoteKeysForMovieId(id: Long): PopularMoviesKeyEntity?

    @Query(
        """SELECT movies.* FROM movies
        INNER JOIN popular_movies_keys ON movies.id=popular_movies_keys.movieId
        ORDER BY curKey ASC, popularity DESC, title ASC"""
    )
    override fun getAll(): PagingSource<Int, MovieEntity>

    @Query("delete from  popular_movies_keys")
    suspend fun clearRemoteKeys()

    @Transaction
    override suspend fun cacheResponse(
        response: MoviePagedListResponse,
        pageKey: Int,
        isRefresh: Boolean
    ) {
        if(isRefresh)
            clearRemoteKeys()

        val prevKey=if (pageKey == STARTING_PAGE_INDEX) null else pageKey-1
        val nextKey=if(pageKey >=response.total_pages) null else pageKey+1
        val keys=response.movieResponses.map {
            PopularMoviesKeyEntity(it.id,prevKey,pageKey,nextKey)
        }
        insertAll(keys)
        insertAllMovies(response.toMovieEntity())
    }
}