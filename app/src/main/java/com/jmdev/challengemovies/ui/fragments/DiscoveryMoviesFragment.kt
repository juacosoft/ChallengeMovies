package com.jmdev.challengemovies.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import challengemovies.R
import challengemovies.databinding.DiscoverymoviesFragmentBinding
import com.jmdev.challengemovies.data.models.MovieModel
import com.jmdev.challengemovies.listeners.MovieSelected
import com.jmdev.challengemovies.ui.components.adapters.SeeMoreMovieAdapter
import com.jmdev.challengemovies.ui.viewmodels.DiscoveryMovieViewModel
import com.jmdev.challengemovies.util.NetworkConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DiscoveryMoviesFragment : Fragment(),MovieSelected {

    private var _binding:DiscoverymoviesFragmentBinding?=null
    private val binding get() = _binding!!
    private var page=1
    private val discoveryViewModel:DiscoveryMovieViewModel by viewModels()
    lateinit var moviesAdapter: SeeMoreMovieAdapter
    private var listMovie:MutableList<MovieModel> ?= mutableListOf()


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
        _binding= DiscoverymoviesFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter= SeeMoreMovieAdapter(this)
        binding.apply {
            rvDiscoveryMovies.adapter=moviesAdapter

            refreshMovies.setOnRefreshListener {
                page+=1
                discoveryViewModel.fetchDiscoveryMovies(page)
            }

        }
        callObservers()
    }

    private fun callObservers() {
        discoveryViewModel.isConected().observe(viewLifecycleOwner, Observer {isConected->
            binding.refreshMovies.isEnabled=isConected
            listMovie?.clear()
            discoveryViewModel.fetchDiscoveryMovies(page)
        })
        discoveryViewModel.getMovies().observe(viewLifecycleOwner, Observer {movies->
            movies.let {
                //listMovie?.clear()
                listMovie?.addAll(it)
                moviesAdapter.setList(listMovie!!)
            }
        })
        discoveryViewModel.getLoading().observe(viewLifecycleOwner, Observer {isLoading->
            binding.refreshMovies.isRefreshing=isLoading
            if(isLoading){
                binding.pbDiscoveryProgress.visibility=View.VISIBLE
            }else{
                binding.pbDiscoveryProgress.visibility=View.GONE
            }
        })
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