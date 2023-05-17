package com.example.mvvm_hilt_paging_flow.data.remote.model

import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import com.google.gson.annotations.SerializedName


data class MoviePagedListResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movieResponses: List<MovieResponse>,
    @SerializedName("total_pages") val total_pages: Int,

    ){
    fun toMovieEntity()=movieResponses.map {
        MovieEntity(
            id=it.id,
            title = it.title,
            overview = it.overview,
            rate = it.rate,
            genres = null,
            releaseDate = it.release_date,
            posterPath = it.poster_path,
            popularity = it.popularity
        )
    }

}