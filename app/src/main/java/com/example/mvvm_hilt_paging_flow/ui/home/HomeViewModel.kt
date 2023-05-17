package com.example.mvvm_hilt_paging_flow.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.mvvm_hilt_paging_flow.data.repository.popular.PopularMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: PopularMovieRepository
) :ViewModel(){
    val movies=repository.getPopularMovies().cachedIn(viewModelScope)
}