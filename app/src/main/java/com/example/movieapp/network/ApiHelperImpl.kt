package com.example.movieapp.network

import com.example.movieapp.BuildConfig
import com.example.movieapp.data.MovieListResponse
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
):ApiHelper {

    override suspend fun getPopularMovieList(): Response<MovieListResponse> = apiService.getPopularMovieList(apikey = BuildConfig.API_KEY)
    override suspend fun getLatestMovie(): Response<MovieListResponse.MovieData> = apiService.getLatestMovie(apikey = BuildConfig.API_KEY)

}