package com.jmdev.challengemovies.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import challengemovies.R
import challengemovies.databinding.MoviesFragmentBinding

import com.jmdev.challengemovies.data.models.MovieModel

import com.jmdev.challengemovies.listeners.MovieSelected
import com.jmdev.challengemovies.ui.components.adapters.MoviesAdapter
import com.jmdev.challengemovies.ui.viewmodels.MoviesViewModel
import com.jmdev.challengemovies.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(),MovieSelected {
    private var _binding: MoviesFragmentBinding?=null
    private val binding get() = _binding!!

    private var page:Int=0
    lateinit var popularAdapter:MoviesAdapter
    lateinit var topRatedAdapter:MoviesAdapter


    private val moviesViewModel:MoviesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= MoviesFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularAdapter= MoviesAdapter(this)
        topRatedAdapter= MoviesAdapter(this)
        binding.rvPopularlist.adapter=popularAdapter
        binding.rvtopratedList.adapter=topRatedAdapter

        callObservers()
        //options= ["popular","toprated","query" =
        binding.apply {
            btnSeemorePopular.setOnClickListener {
                val bundle=Bundle()

                bundle.putString("option","popular")
                Navigation.findNavController(it).navigate(R.id.seeMoreMovieFragment,bundle)
            }
            btnSeemoreToprated.setOnClickListener {
                val bundle=Bundle()
                bundle.putString("option","toprated")
                Navigation.findNavController(it).navigate(R.id.seeMoreMovieFragment,bundle)
            }
            moviesSearchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val bundle=Bundle()
                    bundle.putString("option","query")
                    bundle.putString("querytext",query)
                    Navigation.findNavController(view).navigate(R.id.seeMoreMovieFragment,bundle)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })

        }




    }


    private fun callObservers() {
        moviesViewModel.popularList.observe(viewLifecycleOwner, Observer {result->
            result.data?.let { popularAdapter.setList(it) }
            binding.progressMovies.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()


            Log.e("MoviesFragment","${result.error?.localizedMessage}")
        })
        moviesViewModel.topratedList.observe(viewLifecycleOwner, Observer { result->
            result.data?.let { topRatedAdapter.setList(it) }
            binding.progressMovies.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
            Log.e("MoviesFragment","${result.error?.localizedMessage}")
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