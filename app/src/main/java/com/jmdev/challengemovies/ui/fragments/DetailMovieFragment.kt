package com.jmdev.challengemovies.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import challengemovies.R
import challengemovies.databinding.DetailmovieFragmentBinding
import com.bumptech.glide.Glide

import com.jmdev.challengemovies.constants.ServerUrls.IMAGE_PATH
import com.jmdev.challengemovies.data.models.TrailerModel

import com.jmdev.challengemovies.listeners.TrailerSelected
import com.jmdev.challengemovies.ui.components.adapters.TrailersAdapter
import com.jmdev.challengemovies.ui.viewmodels.DetailMovieViewModel
import com.jmdev.challengemovies.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class DetailMovieFragment : Fragment(),TrailerSelected {
    private var _binding: DetailmovieFragmentBinding?=null
    private val binding get() = _binding!!
    private val detailViewmodel:DetailMovieViewModel by viewModels()
    lateinit var trailersAdapter:TrailersAdapter
    lateinit var myContext:Context
    private var favoriteControl=false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= DetailmovieFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBackdropMovie.setOnClickListener{
            Navigation.findNavController(it).popBackStack()
        }
        myContext=view.context
        trailersAdapter= TrailersAdapter(this)
        binding.rvDetailmovieTrailers.adapter=trailersAdapter
        Log.d("MovieSelected","${arguments?.getInt("movieid")}")
        val idmovie=arguments?.getInt("movieid")
        if(idmovie!=null){
            callObservers(idmovie)

        }
    }


    fun callObservers(idMovie:Int){
        detailViewmodel.isFavorite().observe(viewLifecycleOwner, Observer {
            favoriteControl=it
            if(it){
                binding.ivDetailmovieIsfavorite.setImageDrawable(AppCompatResources.getDrawable(myContext,R.drawable.ic_favorite_on))
            }else{
                binding.ivDetailmovieIsfavorite.setImageDrawable(AppCompatResources.getDrawable(myContext,R.drawable.ic_favorite_off))
            }
        })

        detailViewmodel.movie(idMovie).observe(viewLifecycleOwner, Observer {result->
            val movieSelected=result.data
            Log.d("DetailMovieFragment","observer: $movieSelected")
            binding.apply {
                Glide.with(binding.root)
                    .load(IMAGE_PATH+movieSelected?.poster_path)
                    .error(R.drawable.ic_placeholder_movie)
                    .into(ivDetailmoviePoster)
                Glide.with(binding.root)
                    .load(IMAGE_PATH+movieSelected?.backdrop_path)
                    .into(ivBackdropMovie)
                tvDetailmovieOverview.text=movieSelected?.overview
                tvDetailmovieTitle.text=movieSelected?.title
                rtDetailmovieRate.rating= (movieSelected?.vote_average?.div(2))?.toFloat() ?: 0.0f
                tvDetailmovieRelease.text=movieSelected?.release_date
                pgdetailmovie.isVisible = result is Resource.Loading && result.data==null
                errorData.isVisible = result is Resource.Error && result.data==null
                textError.text = result.error?.localizedMessage

                ivDetailmovieIsfavorite.setOnClickListener{
                    Log .d("ClickFavorite","$favoriteControl - $movieSelected")
                    when(favoriteControl){
                        false-> {
                            if (movieSelected != null) detailViewmodel.insertInFavorite(movieSelected)
                        }
                        true->{
                            if(movieSelected!=null)detailViewmodel.removeFavoriteMovie(movieSelected)
                        }
                    }

                }

            }
        })

        detailViewmodel.isConection.observe(viewLifecycleOwner, Observer {
            if(it){
                detailViewmodel.fechtTrailers()
                binding.rvDetailmovieTrailers.visibility=View.VISIBLE
                binding.tvDetailmovieTrailerstitle.text="Trailers"
            }else{
                binding.rvDetailmovieTrailers.visibility=View.GONE
                binding.tvDetailmovieTrailerstitle.text="No Trailers"
            }
        })

        detailViewmodel.trailers.observe(viewLifecycleOwner, Observer {
            trailersAdapter.setList(it)
        })


    }

    override fun onTRailerSelected(trailerModel: TrailerModel) {
        Log.d("TrailerSelected","$trailerModel")

        if(trailerModel.site.toLowerCase(Locale.ROOT) == "youtube") {
            val bundle=Bundle()
            bundle.putString("urlvideo", trailerModel.key)
            view?.let {
                Navigation.findNavController(it).navigate(R.id.videoPlayerFragment, bundle)
            }
        }else{
            Toast.makeText(context,"no adviable",Toast.LENGTH_SHORT).show()
        }

    }
}