package com.lexosis.cinemavault.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.model.MovieDb

class MovieListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val imagenMovie = itemView.findViewById<ImageView>(R.id.ivMovie)


    fun render(movieDb : MovieDb){

        Glide.with(imagenMovie.context).load("https://image.tmdb.org/t/p/w300"+movieDb.poster_path).into(imagenMovie)
    }




}