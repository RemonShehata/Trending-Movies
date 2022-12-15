package com.example.trendingmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trendingmovies.database.MoviesDatabase
import com.example.trendingmovies.databinding.FragmentMoviesListBinding
import com.example.trendingmovies.network.MoviesApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    @Inject
    lateinit var moviesApi: MoviesApi

    @Inject
    lateinit var moviesDatabase: MoviesDatabase

    private val trendingTrendingMoviesViewModel: TrendingMoviesViewModel by viewModels()

    private lateinit var binding: FragmentMoviesListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moviesListAdapter = MoviesListAdapter(onItemClicked)
        binding.moviesListRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = moviesListAdapter
        }

        with(trendingTrendingMoviesViewModel) {
            getAllMovies()
            moviesLiveData.observe(requireActivity()) { movies ->
                moviesListAdapter.submitList(movies)
            }
        }
    }

    private val onItemClicked: (movieId: String) -> Unit = { movieId ->
        findNavController().navigate(
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(movieId)
        )
    }
}
