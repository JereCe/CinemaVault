package com.lexosis.cinemavault.ui.watchlist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.model.FavoriteMovie

class WatchListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ivWatchList = itemView.findViewById<ImageView>(R.id.ivWatchList)
    val tvNameMovie = itemView.findViewById<TextView>(R.id.tvNameMovie)
    val tvDateMovie = itemView.findViewById<TextView>(R.id.tvDateMovie)
    val cvWLMovie = itemView.findViewById<CardView>(R.id.cvWLMovie)

}