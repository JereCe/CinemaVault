package com.lexosis.cinemavault.ui.watchlist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.model.FavoriteMovie

class WatchListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val ivWatchList = itemView.findViewById<ImageView>(R.id.ivWatchList)

    val tvNameMovie = itemView.findViewById<TextView>(R.id.tvNameMovie)

    val tvDateMovie = itemView.findViewById<TextView>(R.id.tvDateMovie)


    fun render(movieDb: FavoriteMovie){
        tvNameMovie.text= movieDb.title
        tvDateMovie.text=movieDb.release_date
        Glide.with(ivWatchList.context).load("https://image.tmdb.org/t/p/w300"+movieDb.poster_path).into(ivWatchList)
    }




}