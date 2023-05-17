package com.example.mvvm_hilt_paging_flow.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm_hilt_paging_flow.R
import com.example.mvvm_hilt_paging_flow.databinding.ListHomeItemBinding
import com.example.mvvm_hilt_paging_flow.domain.model.Movie
import com.example.mvvm_hilt_paging_flow.utils.dateTransitionName
import com.example.mvvm_hilt_paging_flow.utils.posterTransitionName
import com.example.mvvm_hilt_paging_flow.utils.titleTransitionName
import timber.log.Timber

class HomeAdapter(private val onItemClick: (Movie, ImageView, TextView, TextView) -> Unit) :
    PagingDataAdapter<Movie,HomeViewHolder>(COMPARATOR) {

        companion object{
            val COMPARATOR=object : DiffUtil.ItemCallback<Movie>(){
                override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                    return oldItem.id==newItem.id
                }

                override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                   return  oldItem == newItem
                }
            }
        }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder.create(parent,onItemClick)
}

class HomeViewHolder(
    private val binding: ListHomeItemBinding,
    private val onItemClick: (Movie, ImageView, TextView, TextView) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var _movie:Movie?=null

    init {
        with(binding){
            root.setOnClickListener {
                _movie?.let {
                    onItemClick.invoke(
                        it,posterImageView,titleTextView,dateTextView
                    )
                }
            }
        }
    }

    fun bind(movie: Movie)= with(binding){
       Timber.d("Movies:: $movie")
        _movie=movie
        titleTextView.text=movie.title
        dateTextView.text=movie.releaseDate

        ratingView.rateProgress.apply {
            isIndeterminate=false
            progress=movie.rate100
            max=100
        }

        ratingView.rateText.text=movie.rate100.toString()
        posterImageView.transitionName= posterTransitionName(movie.id)
        titleTextView.transitionName= titleTransitionName(movie.id)
        dateTextView.transitionName= dateTransitionName(movie.id)
        Glide.with(root.context)
            .load(movie.posterUrl)
            .placeholder(R.drawable.ic_place_holder_24dp)
            .into(posterImageView)
    }
    companion object{
        fun create(parent: ViewGroup,onItemClick: (Movie, ImageView, TextView, TextView) -> Unit)=
            HomeViewHolder(ListHomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),onItemClick)
    }
}