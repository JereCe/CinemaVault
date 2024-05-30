package com.lexosis.cinemavault.ui.main


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.model.MovieDb
import com.lexosis.cinemavault.ui.movie.MovieActivity



class MovieListAdapter(private val mainViewModel: MainViewModel) :
    RecyclerView.Adapter<MovieListViewHolder>() {


    var movies: MutableList<MovieDb> = ArrayList<MovieDb>()
    private val _TAG = "API-MOVIE"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieListViewHolder(layoutInflater.inflate(R.layout.movie_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {

        if(mainViewModel.isFavorite(movies[position].id)){
            holder.btnMovieWatchList.setColorFilter((ContextCompat.getColor(holder.itemView.context, R.color.active)))
            Log.d(_TAG, "deberia teñirse")
        }else{
            holder.btnMovieWatchList.setColorFilter((ContextCompat.getColor(holder.itemView.context, R.color.inactive)))
            Log.d(_TAG, "deberia falso")
        }

        Glide.with(holder.imagenMovie)
            .load("https://image.tmdb.org/t/p/w300" + movies[position].poster_path)
            .into(holder.imagenMovie)

        holder.btnMovieWatchList.setOnClickListener {
            if(mainViewModel.isFavorite(movies[position].id)){
                holder.btnMovieWatchList.setColorFilter((ContextCompat.getColor(holder.itemView.context, R.color.inactive)))
                mainViewModel.deleteFavorite(movies[position].id)
            }else{
                mainViewModel.saveFavoriteMovie(
                    movies[position].id,
                    movies[position].title,
                    movies[position].release_date,
                    movies[position].poster_path
                )
                holder.btnMovieWatchList.setColorFilter((ContextCompat.getColor(holder.itemView.context, R.color.active)))
            }
        }
        holder.cvMainMovie.setOnClickListener {
            val context = holder.itemView.context
            var intent = Intent(context, MovieActivity::class.java)
            intent.putExtra("ID", movies[position].id)
            context.startActivity(intent)
        }
    }

    fun update(lista: MutableList<MovieDb>) {
        movies = lista
        this.notifyDataSetChanged()
    }
}