package com.achsanit.movieapp.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.movieapp.data.MovieRepository
import com.achsanit.movieapp.data.entity.MoviePoster
import com.achsanit.movieapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _popularMovies: MutableStateFlow<Resource<List<MoviePoster>>> =
        MutableStateFlow(Resource.Loading())
    val popularMovies get() = _popularMovies.asStateFlow()

    private val _topRatedMovies: MutableStateFlow<Resource<List<MoviePoster>>> =
        MutableStateFlow(Resource.Loading())
    val topRatedMovies get() = _topRatedMovies.asStateFlow()

    init {
        getPopularMovies()
        getTopRatedMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _popularMovies.update { repository.getPopularMovies() }
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch {
            _topRatedMovies.update { repository.getTopRatedMovies() }
        }
    }

}