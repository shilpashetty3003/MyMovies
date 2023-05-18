package com.example.mvvm_hilt_paging_flow.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvm_hilt_paging_flow.data.repository.search.SearchRepository
import com.example.mvvm_hilt_paging_flow.domain.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    val searchRepository: SearchRepository, val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private var currentSearchResult: Flow<PagingData<Movie>>? = null
     var isSearchAnything = false

    fun search(query: String): Flow<PagingData<Movie>>? {
        if (query.trim().isEmpty()) return null
        isSearchAnything = true

        savedStateHandle[KEY_QUERY_STATE] = query
        val newResult = searchRepository.search(query).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult


    }
    fun getSavedQuery() = savedStateHandle.get(KEY_QUERY_STATE) as? String
    companion object {
        const val KEY_QUERY_STATE = "key_store"
    }

}