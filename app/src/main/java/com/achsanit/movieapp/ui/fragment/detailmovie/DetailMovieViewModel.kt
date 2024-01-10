package com.achsanit.movieapp.ui.fragment.detailmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.movieapp.data.MovieRepository
import com.achsanit.movieapp.data.entity.DetailMovieEntity
import com.achsanit.movieapp.data.entity.MoviePoster
import com.achsanit.movieapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _detailMovie: MutableStateFlow<Resource<DetailMovieEntity>> =
        MutableStateFlow(Resource.Loading())
    val detailMovie = _detailMovie.asStateFlow()

    private val _similarMovies: MutableStateFlow<Resource<List<MoviePoster>>> =
        MutableStateFlow(Resource.Loading())
    val similarMovies = _similarMovies.asStateFlow()

    fun getDetailMovie(movieId: Int) {
        viewModelScope.launch {
            _detailMovie.update {
                repository.getDetailMovieById(movieId)
            }
        }
    }

    fun getSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            _similarMovies.update {
                repository.getSimilarMovies(movieId)
            }
        }
    }

    fun refreshPage(movieId: Int) {
        viewModelScope.launch {
            _detailMovie.emit(Resource.Loading())
            getDetailMovie(movieId)
            _similarMovies.emit(Resource.Loading())
            getSimilarMovies(movieId)
        }
    }
}