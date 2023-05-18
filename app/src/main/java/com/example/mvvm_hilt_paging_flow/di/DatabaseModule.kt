package com.example.mvvm_hilt_paging_flow.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvm_hilt_paging_flow.data.local.MovieDatabase
import com.example.mvvm_hilt_paging_flow.data.repository.movies.MovieLocalDataSource
import com.example.mvvm_hilt_paging_flow.data.repository.popular.PopularMoviesLocalDataSource
import com.example.mvvm_hilt_paging_flow.data.repository.search.SearchLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context):MovieDatabase{
        return Room.databaseBuilder(context,MovieDatabase::class.java,"Movie.db").build()

    }

    @Provides
    @Singleton
    fun providePopularMovieDataSource(database:MovieDatabase):PopularMoviesLocalDataSource{
        return database.popularMoviesDao()
    }

    @Provides
    @Singleton
    fun provideDetailMovieDataSource(database:MovieDatabase):MovieLocalDataSource{
        return database.movieDetailDao()
    }

    @Provides
    @Singleton
    fun searchMovieDataSource(database:MovieDatabase):SearchLocalDataSource{
        return database.searchDetailDao()
    }
}