package com.lexosis.cinemavault.ui.holder

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.model.MovieDb
import com.lexosis.cinemavault.ui.MainViewModel

class MovieListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val _TAG = "API-MOVIE"
    val imagenMovie = itemView.findViewById<ImageView>(R.id.ivMovie)
    val btnMovieWatchList : ImageView = itemView.findViewById(R.id.btnMovieWatchList)
    private lateinit var viewModel: MainViewModel

    init {
        // Obtener el ViewModel
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(itemView.context.applicationContext as Application)
            .create(MainViewModel::class.java)
    }


        fun render(movieDb: MovieDb) {


            Glide.with(imagenMovie.context)
                .load("https://image.tmdb.org/t/p/w300" + movieDb.poster_path).into(imagenMovie)

            btnMovieWatchList.setOnClickListener{
                Log.d(_TAG,"llegue al boton")

                viewModel.guardarFavorito(movieDb.id,movieDb.title,movieDb.release_date,movieDb.poster_path)


            }


    }
}