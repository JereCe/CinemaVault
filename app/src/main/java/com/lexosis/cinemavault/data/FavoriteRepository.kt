package com.lexosis.cinemavault.data


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lexosis.cinemavault.model.FavoriteMovie
import kotlinx.coroutines.tasks.await

class FavoriteRepository {

    private val db = FirebaseFirestore.getInstance()
    private val _TAG = "API-MOVIE"
    private val mAuth = FirebaseAuth.getInstance()
    private val userId = mAuth.currentUser

    fun saveFavoriteMovie(pelicula: FavoriteMovie) {
        if (userId != null) {
            db.collection("usuarios").document(userId.email.toString())
                .collection("peliculasFavoritas").document(pelicula.id.toString())
                .set(pelicula)
                .addOnSuccessListener { Log.d(_TAG, "Película favorita guardada con éxito") }
                .addOnFailureListener { e -> Log.w(_TAG, "Error al guardar película favorita", e) }
        } else {
            Log.d(_TAG, "null")
        }
    }

    suspend fun getFavoriteMovies(): ArrayList<FavoriteMovie> {
        val peliculasFavoritas = ArrayList<FavoriteMovie>()
        if (userId != null) {
            try {
                val documents = FirebaseFirestore.getInstance()
                    .collection("usuarios").document(userId.email.toString())
                    .collection("peliculasFavoritas")
                    .get()
                    .await() // Await suspends the coroutine until the task is complete

                for (document in documents) {
                    val pelicula = document.toObject(FavoriteMovie::class.java)
                    peliculasFavoritas.add(pelicula)
                }
                Log.d(_TAG, "Películas favoritas recuperadas con éxito")
            } catch (e: Exception) {
                Log.w(_TAG, "Error al recuperar películas favoritas", e)
            }
        } else {
            Log.d(_TAG, "userId es null")
        }
        return peliculasFavoritas
    }
}