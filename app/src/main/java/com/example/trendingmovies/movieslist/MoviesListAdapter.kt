package com.example.trendingmovies.movieslist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.with
import com.example.trendingmovies.R
import com.example.trendingmovies.TrendingMoviesDto
import com.example.trendingmovies.databinding.MovieItemLayoutBinding
import com.example.trendingmovies.di.MoviesGlideModule

class MoviesListAdapter(private val onItemClicked: (movieId: String) -> Unit) :
    RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    private val differ: AsyncListDiffer<TrendingMoviesDto> =
        AsyncListDiffer(this, DIFF_CALLBACK)

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
        val currentItem = differ.currentList[position]
        holder.binding.root.setOnClickListener {
            onItemClicked(currentItem.id)
        }
        with(holder.binding) {
            movieTitleTextView.text = currentItem.title

            releaseDateTextView.text = currentItem.releaseDate

            voteCountTextView.text = currentItem.voteCount

            rateTextView.text = currentItem.rating

            differ.currentList[position].posterUrl?.let { url ->
                MoviesGlideModule()
                with(context)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.black_adam)
                    .into(holder.binding.posterImageView)
            }
        }

    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    fun submitList(trendingMovies: List<TrendingMoviesDto>) {
        differ.submitList(trendingMovies)
    }

    inner class MoviesViewHolder(val binding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TrendingMoviesDto>() {
            override fun areItemsTheSame(
                oldItem: TrendingMoviesDto,
                newItem: TrendingMoviesDto
            ): Boolean =
                oldItem === newItem // this is data class


            override fun areContentsTheSame(
                oldItem: TrendingMoviesDto,
                newItem: TrendingMoviesDto
            ): Boolean = oldItem == newItem
        }
    }
}
