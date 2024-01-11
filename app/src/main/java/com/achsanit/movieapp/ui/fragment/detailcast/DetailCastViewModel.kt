package com.achsanit.movieapp.ui.fragment.detailcast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achsanit.movieapp.data.MovieRepository
import com.achsanit.movieapp.data.entity.DetailPersonEntity
import com.achsanit.movieapp.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailCastViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _detailCast: MutableStateFlow<Resource<DetailPersonEntity>> =
        MutableStateFlow(Resource.Loading())
    val detailCast = _detailCast.asStateFlow()

    fun getDetailPerson(personId: Int) {
        viewModelScope.launch {
            _detailCast.update {
                repository.getDetailPersonById(personId)
            }
        }
    }

}