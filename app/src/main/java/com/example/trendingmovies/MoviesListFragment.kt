package com.example.trendingmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.trendingmovies.databinding.FragmentMoviesListBinding

class MoviesListFragment: Fragment() {

    private lateinit var binding: FragmentMoviesListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(layoutInflater).apply {
            button.setOnClickListener {
//                findNavController().navigate()
            }
        }

        return binding.root
    }
}