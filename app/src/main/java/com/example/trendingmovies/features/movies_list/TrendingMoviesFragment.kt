package com.example.trendingmovies.features.movies_list

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
import com.example.trendingmovies.BuildConfig
import com.example.trendingmovies.core.models.ErrorType
import com.example.trendingmovies.core.models.State
import com.example.trendingmovies.core.models.TrendingMoviesDto
import com.example.trendingmovies.core.source.local.MoviesDatabase
import com.example.trendingmovies.core.source.remote.MoviesApi
import com.example.trendingmovies.databinding.FragmentMoviesListBinding
import com.example.trendingmovies.utils.gone
import com.example.trendingmovies.utils.showToast
import com.example.trendingmovies.utils.visible
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

    private lateinit var moviesListAdapter: MoviesListAdapter

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
                        trendingTrendingMoviesViewModel.atBottomOfScreen = true
                        trendingTrendingMoviesViewModel.getNextPageData()
                    } else {
                        trendingTrendingMoviesViewModel.atBottomOfScreen = false
                    }
                }
            })

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        moviesListAdapter = MoviesListAdapter(onItemClicked, emptyList())
        binding.moviesListRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = moviesListAdapter
        }

        with(trendingTrendingMoviesViewModel) {
            mediatorLiveData.observe(viewLifecycleOwner, ::onNetworkAndStateChanged)
            moviesLiveData.observe(viewLifecycleOwner, ::onStateChanged)
        }
    }

    private fun onNetworkAndStateChanged(result: Pair<State<List<TrendingMoviesDto>>?, Boolean?>) {
        Log.d(TAG, "mediatorLiveData: state = ${result.first}")
        Log.d(TAG, "mediatorLiveData: isOnline = ${result.second}")
        if (result.first is State.Success && result.second == false) {
            showToast("You are viewing cached data...")
        }
    }

    private fun onStateChanged(result: State<List<TrendingMoviesDto>>) {
        Log.d(TAG, "onViewCreated: result = $result")
        when (result) {
            is State.Error -> {
                binding.progressBar.gone()

                when (result.errorType) {
                    ErrorType.NoInternet -> {
                        binding.moviesListRecycler.gone()
                        binding.noInternet.root.visible()
                    }

                    ErrorType.NoInternetForNextPage -> {
                        showToast("NoInternetForNextPage")
                    }

                    ErrorType.ReachedEndOfList -> {
                        showToast("ReachedEndOfList")
                    }

                    is ErrorType.UnknownError -> {
                        binding.moviesListRecycler.gone()
                        showToast("Unknown error!")
                    }

                    is ErrorType.RemoteResponseParsingError, ErrorType.ResourceNotFound,
                    ErrorType.ResourceNotFound, is ErrorType.ServerError -> {
                        Log.e(TAG, "Error: ${result.errorType}")
                        showToast("An error has occurred! check the logs.")
                    }

                    ErrorType.UnAuthorized -> {
                        Log.e(TAG, "Error: ${result.errorType}")
                        Log.e(TAG, "API Key: ${BuildConfig.TMDB_API_KEY}")
                        showToast("API key is wrong or invalid!")
                    }
                }
            }

            State.Loading -> {
                Log.d(TAG, "onViewCreated: loading")
                binding.noInternet.root.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }

            is State.Success -> {
                binding.noInternet.root.visibility = View.GONE
                binding.moviesListRecycler.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                moviesListAdapter.setItems(result.data)
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

    companion object {
        private const val TAG = "TrendingMoviesFragment"
    }
}
