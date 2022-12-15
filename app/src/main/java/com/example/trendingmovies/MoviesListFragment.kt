package com.example.trendingmovies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.trendingmovies.database.MoviesDatabase
import com.example.trendingmovies.databinding.FragmentMoviesListBinding
import com.example.trendingmovies.network.MoviesApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MoviesListFragment: Fragment() {

    @Inject
    lateinit var moviesApi: MoviesApi

    @Inject
    lateinit var moviesDatabase: MoviesDatabase

    val trendingMoviesListViewModel: MoviesListViewModel by viewModels()

    private lateinit var binding: FragmentMoviesListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(layoutInflater).apply {
//            button.setOnClickListener {
//                findNavController().navigate()
//            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(trendingMoviesListViewModel) {
            getAllMovies()
            moviesLiveData.observe(requireActivity()){
                Log.d(TAG, "onViewCreated: $it")
            }
        }

        val moviesListAdapter = MoviesListAdapter()
        binding.moviesListRecycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = moviesListAdapter
        }

        lifecycleScope.launch(Dispatchers.IO){
//            val result = moviesApi.getTrendingMovies()
//            moviesDatabase.trendingMoviesDao().insertMovies(result.toTrendingMoviesEntityList())
            val dbResult = moviesDatabase.trendingMoviesDao().getAllMoviesSync()

            withContext(Dispatchers.Main){
                moviesListAdapter.submitList(dbResult)
            }
        }


    }
}