package com.example.trendingmovies.movieslist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.with
import com.example.trendingmovies.R
import com.example.trendingmovies.TrendingMoviesDto
import com.example.trendingmovies.databinding.MovieItemLayoutBinding
import com.example.trendingmovies.di.MoviesGlideModule

// Diff utils doesn't work with adding new data, it move the scroll position to the top.
class MoviesListAdapter(
    private val onItemClicked: (movieId: String) -> Unit,
    private var list: List<TrendingMoviesDto>
) :
    RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            MovieItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = list[position]
        holder.binding.root.setOnClickListener {
            onItemClicked(currentItem.id)
        }
        with(holder.binding) {

            rateTextView.text = currentItem.rating

            currentItem.posterUrl?.let { url ->
                MoviesGlideModule()
                with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_loading)
                    .into(holder.binding.posterImageView)
            }
        }

    }

    override fun getItemCount(): Int = list.size

    fun setItems(newList: List<TrendingMoviesDto>) {
        val oldSize = list.size
        this.list = newList
        notifyItemRangeChanged(oldSize, newList.size)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    inner class MoviesViewHolder(val binding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
