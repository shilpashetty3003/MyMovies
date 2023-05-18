package com.example.mvvm_hilt_paging_flow.data.local.search

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.mvvm_hilt_paging_flow.data.local.common.CommonMovieListDao
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import com.example.mvvm_hilt_paging_flow.data.remote.STARTING_PAGE_INDEX
import com.example.mvvm_hilt_paging_flow.data.remote.model.MoviePagedListResponse
import com.example.mvvm_hilt_paging_flow.data.repository.search.SearchLocalDataSource


@Dao
interface SearchDao:SearchLocalDataSource,CommonMovieListDao {


    @Query(
        """SELECT * FROM movies
            INNER JOIN search_keys  ON movies.id=search_keys.movieId
            WHERE title LIKE :queryString OR overview LIKE :queryString 
            ORDER BY curKey ASC, popularity DESC, title ASC"""
    )
    @Transaction
    override fun searchMovies(queryString: String): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM search_keys WHERE movieId = :movieId")
    override suspend fun getSearchKeysForMoviesID(movieId: Long): SearchKeysEntity?

    @Transaction
    override suspend fun cacheResponse(
        response: MoviePagedListResponse,
        pageKey: Int,
        isRefresh: Boolean
    ) {
        if(isRefresh){
            deleteAll()
        }

        val preKey=if(pageKey == STARTING_PAGE_INDEX) null else pageKey-1
        val nextKey=if(pageKey == response.total_pages) null else pageKey+1

        val keys=response.movieResponses.map {
            SearchKeysEntity(it.id,preKey,pageKey,nextKey)
        }
        insertAll(keys)
        insertAllMovies(response.toMovieEntity())
    }


    @Query("delete from search_keys")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(searchKey:List<SearchKeysEntity>)




}