package com.example.mvvm_hilt_paging_flow.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm_hilt_paging_flow.R
import com.example.mvvm_hilt_paging_flow.databinding.ListItemSearchBinding
import com.example.mvvm_hilt_paging_flow.domain.model.Movie

class SearchAdapter(val clicked:()->Unit):PagingDataAdapter<Movie,SearchViewHolder>(COMPARATOR){



    companion object{
        val COMPARATOR=object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
               return oldItem.id== newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder.create(parent,clicked)

}


class SearchViewHolder(private val binding:ListItemSearchBinding, val clicked: () -> Unit):RecyclerView.ViewHolder(binding.root){

    init {
        with(binding){
            root.setOnClickListener{clicked}
        }
    }

    fun bind(movie:Movie)= with(binding){

        titleTextView.text = movie.title
        dateTextView.text = movie.releaseDate
        overviewTextView.text = movie.overview
        Glide.with(root.context)
            .load(movie.posterUrl)
            .placeholder(R.drawable.ic_place_holder_24dp)
            .into(posterImageView)
    }

    companion object{
        fun create(parent: ViewGroup,clicked: () -> Unit)=
            SearchViewHolder(ListItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false),clicked)
    }


}