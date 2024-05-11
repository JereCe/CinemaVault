package com.lexosis.cinemavault.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.model.MovieDb


class MovieListAdapter : RecyclerView.Adapter<MovieListViewHolder>() {

    var movies : MutableList<MovieDb> = ArrayList<MovieDb>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieListViewHolder(layoutInflater.inflate(R.layout.movie_list_item,parent,false))

    }

    override fun getItemCount(): Int {

        return movies.size

    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val item = movies[position]
        holder.render(item)


    }

    fun update(lista : MutableList<MovieDb>){
        movies = lista
        this.notifyDataSetChanged()
    }
}