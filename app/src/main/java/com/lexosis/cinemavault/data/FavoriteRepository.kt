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
        Log.d(_TAG, "entro a favorite repository")
        val peliculasFavoritas = ArrayList<FavoriteMovie>()
        if (userId != null) {
            try {
                val documents = FirebaseFirestore.getInstance()
                    .collection("usuarios").document(userId.email.toString())
                    .collection("peliculasFavoritas")
                    .get()
                    .await()
                Log.d(_TAG, "await")
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

    fun deleteFavoriteMovie(movieId: String) {
        userId?.let { user ->
            val userDocumentRef = db.collection("usuarios").document(user.email.toString())
            val favoriteMovieDocumentRef = userDocumentRef
                .collection("peliculasFavoritas").document(movieId)

            favoriteMovieDocumentRef
                .delete()
                .addOnSuccessListener { Log.d(_TAG, "Película favorita eliminada con éxito") }
                .addOnFailureListener { e -> Log.w(_TAG, "Error al eliminar película favorita", e) }
        } ?: run {
            Log.d(_TAG, "userId es null")
        }
    }



}