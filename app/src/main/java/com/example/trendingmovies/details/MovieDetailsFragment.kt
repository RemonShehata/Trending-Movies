package com.example.trendingmovies.details

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.trendingmovies.R
import com.example.trendingmovies.TAG
import com.example.trendingmovies.database.Status
import com.example.trendingmovies.databinding.FragmentMovieDetailsBinding
import com.example.trendingmovies.di.MoviesGlideModule
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailsBinding

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)

        Log.d(TAG, "onCreateView: ${args.movieId}")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(movieDetailsViewModel) {
            getMovieDetails(args.movieId)

            movieDetailsLiveData.observe(viewLifecycleOwner) { movie ->
                binding.movieTitletextView.text = movie.title
                binding.overviewTextView.text = movie.overview
                movie.runtime?.let {
                    binding.durationTextview.text = "$it min"
                }


                binding.genre1TextView.isVisible = movie.genres.isNotEmpty()
                binding.genre2TextView.isVisible = movie.genres.size > 1
                binding.genre3TextView.isVisible = movie.genres.size > 2

                when (movie.genres.size) {
                    1 -> binding.genre1TextView.text = movie.genres[0]
                    2 -> binding.genre2TextView.text = movie.genres[1]
                    3 -> binding.genre3TextView.text = movie.genres[2]
                }

                binding.releaseDateTextView.text = movie.releaseDate
                binding.votCountTextView.text = movie.voteCount

                val statusImage: Drawable = when (movie.status) {
                    Status.Rumored, Status.Planned ->
                        resources.getDrawable(R.drawable.ic_error)
                    Status.InProduction, Status.PostProduction, Status.Released ->
                        resources.getDrawable(R.drawable.ic_check_circle)
                    Status.Canceled -> resources.getDrawable(R.drawable.ic_cancel)
                }

                binding.statusImageView.setImageDrawable(statusImage)
                binding.statusTextView.text = movie.status.value

                binding.languageTextView.text = movie.originalLanguage
                binding.revenueTextView.text = movie.revenue

                MoviesGlideModule()
                com.bumptech.glide.Glide.with(requireContext())
                    .load(movie.posterUrl)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_loading)
                    .into(binding.moviePosterImageView)

            }
        }

    }

}