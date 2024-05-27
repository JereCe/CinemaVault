package com.lexosis.cinemavault.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.model.MovieDb
import com.lexosis.cinemavault.ui.MainViewModel
import com.lexosis.cinemavault.ui.holder.MovieListViewHolder


class MovieListAdapter (private val mainViewModel: MainViewModel) : RecyclerView.Adapter<MovieListViewHolder>() {

    var movies : MutableList<MovieDb> = ArrayList<MovieDb>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieListViewHolder(layoutInflater.inflate(R.layout.movie_list_item,parent,false))


    }

    override fun getItemCount(): Int {

        return movies.size

    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {

        Glide.with(holder.imagenMovie)
            .load("https://image.tmdb.org/t/p/w300" +movies[position].poster_path).into(holder.imagenMovie)

        holder.btnMovieWatchList.setOnClickListener{
            mainViewModel.guardarFavorito(movies[position].id,movies[position].title,movies[position].release_date,movies[position].poster_path)

        }
    }

    fun update(lista : MutableList<MovieDb>){
        movies = lista
        this.notifyDataSetChanged()
    }
}