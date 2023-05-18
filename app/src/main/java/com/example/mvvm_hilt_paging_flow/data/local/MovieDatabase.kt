package com.example.mvvm_hilt_paging_flow.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieDao
import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import com.example.mvvm_hilt_paging_flow.data.local.popular.PopularMovieDao
import com.example.mvvm_hilt_paging_flow.data.local.popular.PopularMoviesKeyEntity
import com.example.mvvm_hilt_paging_flow.data.local.search.SearchDao
import com.example.mvvm_hilt_paging_flow.data.local.search.SearchKeysEntity


@Database(entities = [MovieEntity::class,PopularMoviesKeyEntity::class,SearchKeysEntity::class], version = 1)
abstract class MovieDatabase :RoomDatabase() {

    abstract fun popularMoviesDao():PopularMovieDao

    abstract fun movieDetailDao():MovieDao

    abstract fun searchDetailDao():SearchDao
}