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
import challengemovies.databinding.SeeMoreBinding

import com.jmdev.challengemovies.data.models.MovieModel

import com.jmdev.challengemovies.listeners.MovieSelected
import com.jmdev.challengemovies.ui.components.adapters.SeeMoreMovieAdapter
import com.jmdev.challengemovies.ui.viewmodels.SeeMoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeMoreMovieFragment : Fragment(),MovieSelected {

    private var _binding:SeeMoreBinding?=null
    private val binding get() =_binding!!
    private val seeMoreViewModel:SeeMoreViewModel by viewModels()
    lateinit var query:String
    lateinit var option:String
    lateinit var  moviesAdapter:SeeMoreMovieAdapter
    private var page:Int=1
    private var listMovie:MutableList<MovieModel> ?= mutableListOf()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            (binding.rvSeemoreMovies.layoutManager as GridLayoutManager).spanCount = 4
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            (binding.rvSeemoreMovies.layoutManager as GridLayoutManager).spanCount = 2
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= SeeMoreBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        option= arguments?.getString("option")!!
        moviesAdapter= SeeMoreMovieAdapter(this)
        binding.rvSeemoreMovies.adapter=moviesAdapter
        binding.seemoreBack.setOnClickListener{
            Navigation.findNavController(it).popBackStack()
        }

        callAndRefreshMovies(1)
        binding.refreshMovies.setOnRefreshListener {
            page+=1
            callAndRefreshMovies(page)
        }

        callObservers()

    }
    private fun callObservers(){
        seeMoreViewModel.getListMovies().observe(viewLifecycleOwner, Observer {movies->
            movies.let {
                //listMovie?.clear()
                listMovie?.addAll(it)
                moviesAdapter.setList(listMovie!!)
            }
        })
        seeMoreViewModel.isLoading().observe(viewLifecycleOwner, Observer {isLoading->
            binding.refreshMovies.isRefreshing=isLoading
            if(isLoading){
                binding.pbSeemoreProgress.visibility=View.VISIBLE
            }else{
                binding.pbSeemoreProgress.visibility=View.GONE
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


    fun callAndRefreshMovies(page:Int){

        var title="Search by: "
        when(option){
            "popular"->{
                seeMoreViewModel.fecthAllMovies("popular",page,null)
                title += "Popular"
            }
            "toprated"->{

                seeMoreViewModel.fecthAllMovies("toprated",page,null)
                title += "Top Rated"
            }
            "query"->{
                query=arguments?.getString("querytext")!!
                if(!query.isEmpty()){
                        seeMoreViewModel.fecthAllMovies("query",page,query.toLowerCase())
                    title += query
                }
            }
            else ->{
                Log.e("Error","no Opcion")
                binding.tvSeemoreError.visibility=View.VISIBLE
            }
        }
        title.also { binding.tvSeemoreTitle.text = it }
    }

}