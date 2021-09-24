package com.tonicapp.movieapp.ui.movie

import androidx.lifecycle.ViewModel
import com.tonicapp.movieapp.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: MovieRepository)
    : ViewModel() {

        val movies = repository.getNowPlayingMovies()

}