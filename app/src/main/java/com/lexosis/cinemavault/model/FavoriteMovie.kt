package com.lexosis.cinemavault.model


data class FavoriteMovie(
    val id: Int = 0,
    val title: String = "",
    val release_date: String = "",
    val poster_path: String = ""
) {

    constructor() : this(0, "", "", "")
}