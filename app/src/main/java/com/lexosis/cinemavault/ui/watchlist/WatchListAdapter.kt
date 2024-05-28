package com.lexosis.cinemavault.ui.watchlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.model.FavoriteMovie
import com.lexosis.cinemavault.ui.movie.MovieActivity
import com.lexosis.cinemavault.ui.watchlist.WatchListViewHolder


class WatchListAdapter : RecyclerView.Adapter<WatchListViewHolder>() {

    var movies: MutableList<FavoriteMovie> = ArrayList<FavoriteMovie>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WatchListViewHolder(layoutInflater.inflate(R.layout.watch_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        holder.tvNameMovie.text = movies[position].title
        holder.tvDateMovie.text = movies[position].release_date
        Glide.with(holder.ivWatchList)
            .load("https://image.tmdb.org/t/p/w300" + movies[position].poster_path)
            .into(holder.ivWatchList)
        holder.cvWLMovie.setOnClickListener {
            val context = holder.itemView.context
            var intent = Intent(context, MovieActivity::class.java)
            intent.putExtra("ID", movies[position].id)
            context.startActivity(intent)
        }
    }

    fun update(lista: MutableList<FavoriteMovie>) {
        movies = lista
        this.notifyDataSetChanged()
    }
}