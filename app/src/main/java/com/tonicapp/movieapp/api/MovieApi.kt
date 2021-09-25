package com.tonicapp.movieapp.api

import com.tonicapp.movieapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.MOVIEDB_API_KEY
    }

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlayingMovies(
        @Query("page") position: Int
    ): MovieResponse

    @GET("search/movie?api_key=$API_KEY")
    suspend fun searchMovie(
        @Query("query") query : String,
        @Query("page") position: Int
    ) : MovieResponse
}