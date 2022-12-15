package com.example.trendingmovies.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.trendingmovies.R
import com.example.trendingmovies.TAG
import com.example.trendingmovies.databinding.FragmentMovieDetailsBinding
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
                Log.d(TAG, "onViewCreated: ${movie.posterUrl}")
                Glide
                    .with(requireContext())
                    .load(movie.posterUrl)
                    .centerCrop()
                    .placeholder(R.drawable.black_adam)
                    .into(binding.moviePosterImageView)

            }
        }

    }

}