package com.example.movieapp.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.MovieListResponse
import com.example.movieapp.network.NetWorkRepository
import com.example.movieapp.util.RequestState
import com.example.movieapp.util.RequestState.Idle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val netWorkRepository: NetWorkRepository
): ViewModel() {

    private val _popularMovieList = MutableStateFlow<RequestState<MovieListResponse>>(Idle)
    val popularMovieList = _popularMovieList.asStateFlow()

    private val _latestMovieList = MutableStateFlow<RequestState<MovieListResponse.MovieData>>(Idle)
    val latestMovieList = _latestMovieList.asStateFlow()
    init {
        getPopularMovieList()
        getLatestMovieList()
    }

    private fun getPopularMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            _popularMovieList.value = RequestState.Loading
            netWorkRepository.getPopularMovieList().let {
                if(it.isSuccessful) {
                    _popularMovieList.value = RequestState.Success(it.body())
                } else {
                    _popularMovieList.value = RequestState.Error("Went something wrong!")
                }
            }
        }
    }

    private fun getLatestMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            _latestMovieList.value = RequestState.Loading
            netWorkRepository.getLatestMovie().let {
                if(it.isSuccessful) {
                    _latestMovieList.value = RequestState.Success(it.body())
                } else {
                    _latestMovieList.value = RequestState.Error("Went something wrong!")
                }
            }
        }
    }
}