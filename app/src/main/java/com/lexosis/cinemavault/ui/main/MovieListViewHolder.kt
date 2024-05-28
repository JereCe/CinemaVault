package com.lexosis.cinemavault.ui.main

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lexosis.cinemavault.R

class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val _TAG = "API-MOVIE"
    val imagenMovie = itemView.findViewById<ImageView>(R.id.ivMovie)
    val btnMovieWatchList: ImageView = itemView.findViewById(R.id.btnMovieWatchList)
    val cvMainMovie: CardView = itemView.findViewById(R.id.cvMainMovie)


}
