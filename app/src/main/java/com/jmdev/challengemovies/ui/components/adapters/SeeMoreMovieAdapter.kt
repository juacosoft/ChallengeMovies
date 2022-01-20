package com.jmdev.challengemovies.ui.components.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import challengemovies.R
import challengemovies.databinding.ItemMovieSeemoreBinding
import com.bumptech.glide.Glide

import com.jmdev.challengemovies.constants.ServerUrls
import com.jmdev.challengemovies.data.models.MovieModel

import com.jmdev.challengemovies.listeners.MovieSelected

class SeeMoreMovieAdapter (
    private val movieSelected: MovieSelected
        ):RecyclerView.Adapter<SeeMoreMovieAdapter.BindableViewHolder>() {

    private var movieModelList= mutableListOf<MovieModel>()


    fun setList(_movieList:List<MovieModel>){
        this.movieModelList.clear()
        this.movieModelList=_movieList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeMoreMovieAdapter.BindableViewHolder {
        val binding= ItemMovieSeemoreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BindableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeeMoreMovieAdapter.BindableViewHolder, pos: Int) {
        holder.itemView.setOnClickListener{
            movieSelected.onMovieSelected(movieModelList[pos])
        }
        holder.bind(movieModelList[pos])
    }

    override fun getItemCount(): Int = movieModelList.size

    inner class BindableViewHolder(
        private var itemMovieBinding: ItemMovieSeemoreBinding
    ): RecyclerView.ViewHolder(itemMovieBinding.root){
        fun bind(movieModel: MovieModel){
            Log.d("SeeMoreMoviesAdapter","Bind: ${movieModel.title}")
            itemMovieBinding.itemmovieTitle.text=movieModel.title
            itemMovieBinding.itemmovieRate.rating=(movieModel.vote_average/2).toFloat()
            itemMovieBinding.itemmovieTitle.visibility=if (movieModel.poster_path.isNullOrEmpty()) View.VISIBLE else View.GONE
            Glide.with(itemView)
                .load(ServerUrls.IMAGE_PATH + movieModel.poster_path)
                .error(R.drawable.no_image)
                .into(itemMovieBinding.itemmoviePoster)
        }
    }


}