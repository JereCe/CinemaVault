package com.lexosis.cinemavault.data.dbLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDetailDAO {
    @Query("SELECT  * FROM movies WHERE id= :id LIMIT 1")
    fun getMovieByPK(id : Int) : MovieDetailLocal
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(vararg movie :MovieDetailLocal)
    @Delete
    fun deleteMovie(movie : MovieDetailLocal)
}