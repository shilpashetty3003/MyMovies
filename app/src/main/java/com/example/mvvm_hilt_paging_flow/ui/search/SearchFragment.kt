package com.example.mvvm_hilt_paging_flow.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Query
import com.example.mvvm_hilt_paging_flow.R
import com.example.mvvm_hilt_paging_flow.databinding.FragmentSearchBinding
import com.example.mvvm_hilt_paging_flow.ui.common.BaseFragment
import com.example.mvvm_hilt_paging_flow.utils.ViewLifeCycleDelegates
import com.example.mvvm_hilt_paging_flow.utils.listenOnLoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val searchViewModel: SearchViewModel by viewModels()

    private val searchAdapter: SearchAdapter by ViewLifeCycleDelegates { SearchAdapter(::onMovieClicked) }


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        listenOnPagerStateData()

    }

    private fun initUI() {
        with(binding) {
            editText.addTextChangedListener { text ->
                text?.trim()?.let {
                    listenOnPageData(text.toString())
                }
            }
            recyclerView.adapter=searchAdapter
            recyclerView.layoutManager=LinearLayoutManager(requireActivity())
        }
    }

    private fun listenOnPageData(query: String) = viewLifecycleOwner.lifecycleScope.launch {
        searchViewModel.search(query)?.collectLatest{
            Log.d("TAG", "listenOnPageData: "+it)
             searchAdapter.submitData(it)
        }
    }

    private fun onMovieClicked() {

    }

    private fun listenOnPagerStateData() = viewLifecycleOwner.lifecycleScope.launch {
        with(binding) {
            searchAdapter.listenOnLoadState(
                recyclerView,
                loadStateView,
                { searchAdapter.itemCount == 0 },
                getString(R.string.no_popular_movies)

            )
        }
    }

    private fun updateSearchFromInput() = with(binding.editText) {
        text?.trim()?.let {

        }

    }


}