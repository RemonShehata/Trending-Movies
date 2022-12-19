package com.example.trendingmovies.features.movies_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trendingmovies.ErrorType
import com.example.trendingmovies.State
import com.example.trendingmovies.core.TAG
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
            mediatorLiveData.observe(viewLifecycleOwner){
                Log.d(TAG, "mediatorLiveData: state = ${it.first}")
                Log.d(TAG, "mediatorLiveData: isOnline = ${it.second}")
                if (it.first is State.Success && it.second == false) {
                    Toast.makeText(requireContext(), "You are viewing cached data", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            moviesLiveData.observe(viewLifecycleOwner) { result ->
                Log.d(TAG, "onViewCreated: result = $result")
                when (result) {
                    is State.Error -> {
                        when (result.errorType) {
                            ErrorType.NoInternet -> {
                                binding.noInternet.root.visibility = View.VISIBLE
                                binding.moviesListRecycler.visibility = View.GONE
                            }

                            ErrorType.NoInternetForNextPage -> {
                                Toast.makeText(requireContext(), "NoInternetForNextPage", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            ErrorType.ReachedEndOfList -> TODO()
                            ErrorType.UnknownError -> {
                                Toast.makeText(requireContext(), "Unkown error!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                    State.Loading -> {
                        Log.d(TAG, "onViewCreated: loading")
                        binding.noInternet.root.visibility = View.GONE
                    }

                    is State.Success -> {
                        binding.noInternet.root.visibility = View.GONE
                        binding.moviesListRecycler.visibility = View.VISIBLE
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
