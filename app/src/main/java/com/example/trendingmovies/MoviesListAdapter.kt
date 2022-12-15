package com.example.trendingmovies

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.trendingmovies.database.TrendingMoviesEntity
import com.example.trendingmovies.databinding.MovieItemLayoutBinding

class MoviesListAdapter : RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

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
        holder.binding.movieTitleTextView.text =
            differ.currentList[position].title

        holder.binding.releaseDateTextView.text =
            differ.currentList[position].releaseDate

        holder.binding.voteCountTextView.text =
            differ.currentList[position].voteCount.toString()

//
//        differ.currentList[position].urlToImage?.let { url ->
//            Glide
//                .with(context)
//                .load(url)
//                .centerCrop()
//                .placeholder(R.drawable.placeholder_image_svg)
//                .into(holder.binding.image)
//        }
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
