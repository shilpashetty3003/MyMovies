package com.example.mvvm_hilt_paging_flow.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mvvm_hilt_paging_flow.R
import com.example.mvvm_hilt_paging_flow.databinding.FragmentDetailBinding
import com.example.mvvm_hilt_paging_flow.domain.model.Movie
import com.example.mvvm_hilt_paging_flow.domain.model.utils.IIError
import com.example.mvvm_hilt_paging_flow.domain.model.utils.getHumanReadableText
import com.example.mvvm_hilt_paging_flow.ui.common.BaseFragment
import com.example.mvvm_hilt_paging_flow.utils.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {


    val viewModel: DetailViewModel by viewModels()
    val args: DetailFragmentArgs by navArgs()


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        loadData()
    }

    private fun loadData() = viewModel.apply {
        loadMovie(args.movie.id).observe(viewLifecycleOwner) {state->
            when(state){
                is State.Failure -> populateError(state.error)
                is State.Loading -> showLoading()
                is State.Success -> state.value?.let { populateUI(it) }
            }
        }
    }

    private fun initUI() = with(binding) {
        val movie = args.movie
        titleTextView.text = movie.title
        dateTextView.text = movie.releaseDate

        Glide.with(requireContext())
            .load(movie.posterUrl)
            .placeholder(R.drawable.ic_place_holder_24dp)
            .into(posterImageView)
        loadStateView.setOnRetryClickListener { loadData() }


    }

    private fun showLoading()= with(binding){
        loadStateView.isLoadingVisible=true
        loadStateView.hideErrorMessage()
    }
    fun populateUI(movie:Movie)= with(binding){
        loadStateView.hide()
        Glide.with(requireContext())
            .load(movie.posterUrl)
            .placeholder(R.drawable.ic_place_holder_24dp)
            .into(posterImageView)

        ratingView.apply {
            root.isVisible=true
            rateProgress.progress=movie.rate100
            rateText.text=movie.rate100.toString()
        }

        titleTextView.text=movie.title
        dateTextView.text=movie.releaseDate
        genreTextView.text = movie.genres
        overviewTextView.text = movie.overview
        headingOverview.isVisible = true
        loadStateView.hideErrorMessage()
    }

    fun populateError(error:IIError)= with(binding){
        loadStateView.showErrorMessage(error.getHumanReadableText(requireContext()))
    }
}