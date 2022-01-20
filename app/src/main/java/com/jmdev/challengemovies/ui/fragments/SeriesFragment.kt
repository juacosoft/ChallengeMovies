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
import androidx.recyclerview.widget.GridLayoutManager
import challengemovies.databinding.SeriesFragmentBinding
import com.jmdev.challengemovies.data.models.TvSeriesModel
import com.jmdev.challengemovies.listeners.TvSerieSelected
import com.jmdev.challengemovies.ui.components.adapters.TvSeriesAdapter

import com.jmdev.challengemovies.ui.viewmodels.SeriesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesFragment : Fragment(),TvSerieSelected {


    private var _binding: SeriesFragmentBinding?=null
    private val binding get() = _binding!!
    private val seriesViewModel:SeriesViewModel by viewModels()
    private val page=1
    lateinit var seriesAdapter: TvSeriesAdapter
    private var listSeries:MutableList<TvSeriesModel>?= mutableListOf()

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
        _binding= SeriesFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seriesAdapter= TvSeriesAdapter(this)
        binding.apply {
            rvDiscoveryMovies.adapter=seriesAdapter

        }
        callObservers()
    }
    private fun callObservers(){
        seriesViewModel.getTvSeries().observe(viewLifecycleOwner, Observer {tvSeries->
            tvSeries.let {
                listSeries?.addAll(it)
                seriesAdapter.setList(listSeries!!)
            }
        })

        seriesViewModel.getLoading().observe(viewLifecycleOwner, Observer {isLoading->
            if(isLoading){
                binding.pbDiscoveryProgress.visibility=View.VISIBLE
            }else{
                binding.pbDiscoveryProgress.visibility=View.GONE
            }
        })
    }

    override fun onSerieSelected(serieModel: TvSeriesModel) {
        Log.d("SeriesSelected","${serieModel}")

    }
}