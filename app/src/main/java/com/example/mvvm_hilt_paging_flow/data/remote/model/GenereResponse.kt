package com.example.mvvm_hilt_paging_flow.data.remote.model

import com.google.gson.annotations.SerializedName

data class GenereResponse(
    @SerializedName("name") val name:String
){
    override fun toString()=name
}

