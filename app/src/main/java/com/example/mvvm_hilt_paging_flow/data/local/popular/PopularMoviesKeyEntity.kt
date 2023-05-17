package com.example.mvvm_hilt_paging_flow.data.local.popular

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movies_keys")
data class PopularMoviesKeyEntity(


    @PrimaryKey
    val movieID:Long,
    val preKey:Int?,
    val curKey:Int,
    val nextKey :Int?


)