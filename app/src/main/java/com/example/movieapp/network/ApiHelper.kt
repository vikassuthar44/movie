package com.example.movieapp.network

import com.example.movieapp.data.MovieListResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getPopularMovieList(): Response<MovieListResponse>

    suspend fun getLatestMovie(): Response<MovieListResponse.MovieData>

}