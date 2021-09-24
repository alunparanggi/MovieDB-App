package com.tonicapp.movieapp.api

import com.tonicapp.movieapp.data.model.Movie

data class MovieResponse(
        val results: List<Movie>
)