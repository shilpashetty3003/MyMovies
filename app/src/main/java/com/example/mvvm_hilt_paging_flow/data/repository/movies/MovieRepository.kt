package com.example.mvvm_hilt_paging_flow.data.repository.movies

import com.example.mvvm_hilt_paging_flow.domain.model.Movie
import com.example.mvvm_hilt_paging_flow.domain.model.utils.NetworkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import java.net.UnknownHostException
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val local:MovieLocalDataSource,
    private val remote:MovieRemoteDataSource
){

    fun getMovie(id:Long):Flow<Movie?> =  local.getMovieFlow(id).transform { localMovie->
        emit(localMovie?.toMovie())
        try {
            if(localMovie ==null || !localMovie.isDetailsLoaded()){
                remote.getMovie(id).let {
                    local.insert(it.toMovieEntity())
                }
            }
        }catch (e:UnknownHostException){
            throw NetworkError(e)
        }catch (e:Exception){
            throw  com.example.mvvm_hilt_paging_flow.domain.model.utils.UnknownError(e)
        }
    }
}