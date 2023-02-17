package com.example.movieapp.network

import javax.inject.Inject

class NetWorkRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {

    suspend fun getPopularMovieList() = apiHelper.getPopularMovieList()

    suspend fun getLatestMovie() = apiHelper.getLatestMovie()
}

