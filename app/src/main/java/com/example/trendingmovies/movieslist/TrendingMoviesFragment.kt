package com.example.trendingmovies.movieslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trendingmovies.State
import com.example.trendingmovies.TAG
import com.example.trendingmovies.database.MoviesDatabase
import com.example.trendingmovies.databinding.FragmentMoviesListBinding
import com.example.trendingmovies.network.MoviesApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TrendingMoviesFragment : Fragment() {

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
        binding = FragmentMoviesListBinding.inflate(layoutInflater).apply {
            moviesListRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        Log.d(TAG, "onScrollStateChanged: end of scroll")
                        trendingTrendingMoviesViewModel.getNextPageData()
                    }
                }
            })
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        val moviesListAdapter = MoviesListAdapter(onItemClicked, emptyList())
        binding.moviesListRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = moviesListAdapter
        }

        with(trendingTrendingMoviesViewModel) {
            moviesLiveData.observe(requireActivity()) { result ->
                Log.d(TAG, "onViewCreated: result = $result")
                when (result) {
                    is State.Error -> TODO()
                    State.Loading -> {
                        Log.d(TAG, "onViewCreated: loading")
                    }
                    is State.Success -> {
                        moviesListAdapter.setItems(result.data)
                    }
                }
            }
        }
    }

    private val onItemClicked: (movieId: String) -> Unit = { movieId ->
        findNavController().navigate(
            TrendingMoviesFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment(
                movieId
            )
        )
    }
}
