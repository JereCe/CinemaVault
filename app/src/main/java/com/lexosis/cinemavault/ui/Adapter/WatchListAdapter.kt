package com.lexosis.cinemavault.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.model.MovieDb
import com.lexosis.cinemavault.ui.holder.WatchListViewHolder


class WatchListAdapter : RecyclerView.Adapter<WatchListViewHolder>() {

    var movies : MutableList<MovieDb> = ArrayList<MovieDb>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return WatchListViewHolder(layoutInflater.inflate(R.layout.watch_list_item,parent,false))

    }

    override fun getItemCount(): Int {

        return movies.size

    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        val item = movies[position]
        holder.render(item)


    }

    fun update(lista : MutableList<MovieDb>){
        movies = lista
        this.notifyDataSetChanged()
    }
}