package com.example.mvvm_hilt_paging_flow.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm_hilt_paging_flow.data.repository.movies.MovieRepository
import com.example.mvvm_hilt_paging_flow.utils.withState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val movieRepository: MovieRepository) :ViewModel(){

    fun loadMovie(id:Long)= withState(500){movieRepository.getMovie(id)}.asLiveData(viewModelScope.coroutineContext)
}