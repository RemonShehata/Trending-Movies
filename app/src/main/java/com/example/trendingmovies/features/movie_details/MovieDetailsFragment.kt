package com.example.trendingmovies.features.movie_details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.trendingmovies.core.models.ErrorType
import com.example.trendingmovies.R
import com.example.trendingmovies.core.models.State
import com.example.trendingmovies.base.TAG
import com.example.trendingmovies.core.models.MovieDetailsDto
import com.example.trendingmovies.core.source.local.models.Status
import com.example.trendingmovies.databinding.FragmentMovieDetailsBinding
import com.example.trendingmovies.di.MoviesGlideModule
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

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

            movieDetailsLiveData.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is State.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.dataViewGroup.visibility = View.GONE

                        when (result.errorType) {
                            ErrorType.NoInternet -> {
                                binding.noInternet.root.visibility = View.VISIBLE

                            }

                            ErrorType.NoInternetForNextPage -> {
                                TODO()
                            }

                            ErrorType.ReachedEndOfList -> TODO()
                            is ErrorType.UnknownError -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Unknown error!",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }

                    State.Loading -> {
                        Log.d(TAG, "onViewCreated: loading")
                        binding.noInternet.root.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                        binding.dataViewGroup.visibility = View.GONE
                    }

                    is State.Success -> {
                        binding.noInternet.root.visibility = View.GONE
                        binding.dataViewGroup.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        renderDataOnUI(result.data)
                    }
                }
            }
        }
    }

    private fun renderDataOnUI(movie: MovieDetailsDto) {
        binding.movieTitletextView.text = movie.title
        binding.overviewTextView.text = movie.overview
        movie.runtime?.let {
            binding.durationTextview.text = "$it min"
        }


        binding.genre1TextView.isVisible = movie.genres.isNotEmpty()
        binding.genre2TextView.isVisible = movie.genres.size > 1
        binding.genre3TextView.isVisible = movie.genres.size > 2
        binding.genre4TextView.isVisible = movie.genres.size > 3
        binding.genre5TextView.isVisible = movie.genres.size > 4

        movie.genres.getOrNull(0)?.let { binding.genre1TextView.text = it }
        movie.genres.getOrNull(1)?.let { binding.genre2TextView.text = it }
        movie.genres.getOrNull(2)?.let { binding.genre3TextView.text = it }
        movie.genres.getOrNull(3)?.let { binding.genre4TextView.text = it }
        movie.genres.getOrNull(4)?.let { binding.genre5TextView.text = it }

        binding.releaseDateTextView.text = movie.releaseDate
        binding.votCountTextView.text = movie.voteCount

        val statusImage: Drawable? = when (movie.status) {
            Status.Rumored, Status.Planned ->
                ResourcesCompat.getDrawable(resources, R.drawable.ic_error, null)
            Status.InProduction, Status.PostProduction, Status.Released ->
                ResourcesCompat.getDrawable(resources, R.drawable.ic_check_circle, null)
            Status.Canceled -> ResourcesCompat.getDrawable(resources, R.drawable.ic_cancel, null)
        }

        binding.statusImageView.setImageDrawable(statusImage)
        binding.statusTextView.text = movie.status.value

        binding.languageTextView.text = movie.originalLanguage

        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("USD")
        binding.revenueTextView.text = format.format(movie.revenue)

        MoviesGlideModule()
        com.bumptech.glide.Glide.with(requireContext())
            .load(movie.posterUrl)
            .centerCrop()
            .placeholder(R.drawable.placeholder_loading)
            .into(binding.moviePosterImageView)
    }

}