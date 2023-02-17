package com.example.movieapp.network

import com.example.movieapp.data.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("3/movie/popular")
    suspend fun getPopularMovieList(
        @Query("api_key") apikey: String
    ): Response<MovieListResponse>

    @GET("3/movie/latest")
    suspend fun getLatestMovie(
        @Query("api_key") apikey: String
    ): Response<MovieListResponse.MovieData>
}