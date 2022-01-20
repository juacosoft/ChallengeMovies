package com.jmdev.challengemovies.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import challengemovies.R
import challengemovies.databinding.FavoritemoviesFragmentBinding
import com.jmdev.challengemovies.data.models.MovieModel
import com.jmdev.challengemovies.listeners.MovieSelected
import com.jmdev.challengemovies.ui.components.adapters.SeeMoreMovieAdapter
import com.jmdev.challengemovies.ui.viewmodels.FavoriteMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment :Fragment(),MovieSelected {

    private var _binding: FavoritemoviesFragmentBinding?=null
    private val binding get() = _binding!!
    lateinit var moviesAdapter: SeeMoreMovieAdapter
    private var listMovie:MutableList<MovieModel> ?= mutableListOf()
    private val favoriteViewModel:FavoriteMoviesViewModel by viewModels()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            (binding.rvDiscoveryMovies.layoutManager as GridLayoutManager).spanCount = 4
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            (binding.rvDiscoveryMovies.layoutManager as GridLayoutManager).spanCount = 2
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FavoritemoviesFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter= SeeMoreMovieAdapter(this)
        binding.apply {
            rvDiscoveryMovies.adapter=moviesAdapter
        }
        callObservers()
    }

    private fun callObservers() {
        favoriteViewModel.isLoading().observe(viewLifecycleOwner, Observer {isLoading ->
            if(isLoading){
                binding.pbDiscoveryProgress.visibility=View.VISIBLE
            }else{
                binding.pbDiscoveryProgress.visibility=View.GONE
            }
        })
        favoriteViewModel.getMovieList().observe(viewLifecycleOwner, Observer {movies ->
            moviesAdapter.setList(movies)
        })
    }

    override fun onStart() {
        super.onStart()
        favoriteViewModel.fetchFavorite()
    }
    override fun onMovieSelected(movieModel: MovieModel) {
        Log.d("MovieSelected","$movieModel")
        val bundle=Bundle()
        bundle.putInt("movieid",movieModel.id)
        view?.let {
            Navigation.findNavController(it).navigate(R.id.detailMovieFragment,bundle)
        }
    }
}