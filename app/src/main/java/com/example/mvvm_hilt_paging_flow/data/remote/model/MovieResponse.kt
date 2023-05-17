package com.example.mvvm_hilt_paging_flow.data.remote.model

import com.example.mvvm_hilt_paging_flow.data.local.movie.MovieEntity
import com.example.mvvm_hilt_paging_flow.data.remote.CDN_BASE_URL
import com.example.mvvm_hilt_paging_flow.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(

    val genre_ids: List<Int>,
    @SerializedName("id") val id: Long,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("poster_path") val poster_path: String,
    @field:SerializedName("genres") val genreResponses: List<GenereResponse>,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("title") val title: String,
    @SerializedName("vote_average") val rate: Float,
    val vote_count: Int
)
{
    fun toMovieEntity()=MovieEntity(
            id=id,
        title=title,
        overview=overview,
        rate = rate ,
        releaseDate = release_date,
        posterPath = poster_path,
        popularity = popularity,
        genres = genreResponses.joinToString(", ")
    )
}