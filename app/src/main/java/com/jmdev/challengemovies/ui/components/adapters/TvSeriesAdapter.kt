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
import com.jmdev.challengemovies.data.models.TvSeriesModel
import com.jmdev.challengemovies.listeners.TvSerieSelected

class TvSeriesAdapter (
    private val serieSelected: TvSerieSelected
        ):RecyclerView.Adapter<TvSeriesAdapter.BindableViewHolder>() {

    private var seriesList = mutableListOf<TvSeriesModel>()

    fun setList(_seriesList:List<TvSeriesModel>){
        this.seriesList.clear()
        this.seriesList=_seriesList.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder {
        val binding=ItemMovieSeemoreBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BindableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindableViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            serieSelected.onSerieSelected(seriesList[position])
        }
        holder.bind(seriesList[position])
    }

    override fun getItemCount(): Int = seriesList.size

    inner class BindableViewHolder (
        private var itemSerieBinding: ItemMovieSeemoreBinding
    ):RecyclerView.ViewHolder(itemSerieBinding.root){

        fun bind(tvSeriesModel: TvSeriesModel){
            Log.d("TVSeriesAdapter","Bind: ${tvSeriesModel.toString()}")
            itemSerieBinding.itemmovieTitle.text=tvSeriesModel.name
            itemSerieBinding.itemmovieRate.rating=(tvSeriesModel.vote_average/2).toFloat()
            itemSerieBinding.itemmovieTitle.visibility=if(tvSeriesModel.poster_path.isNullOrEmpty())View.VISIBLE else View.GONE
            Glide.with(itemView)
                .load(ServerUrls.IMAGE_PATH + tvSeriesModel.poster_path)
                .error(R.drawable.no_image)
                .into(itemSerieBinding.itemmoviePoster)
        }

    }
}