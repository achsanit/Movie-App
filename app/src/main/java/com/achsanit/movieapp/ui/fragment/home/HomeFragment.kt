package com.achsanit.movieapp.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.achsanit.movieapp.data.entity.MoviePoster
import com.achsanit.movieapp.databinding.FragmentHomeBinding
import com.achsanit.movieapp.ui.adapter.PosterMovieAdapter
import com.achsanit.movieapp.utils.Resource
import com.achsanit.movieapp.utils.makeGone
import com.achsanit.movieapp.utils.makeVisible
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()
    private val popMovAdapter: PosterMovieAdapter by lazy {
        PosterMovieAdapter { }
    }
    private val topRateMovAdapter: PosterMovieAdapter by lazy {
        PosterMovieAdapter { }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListener()

        initView()

    }

    private fun initView() {
        with(binding) {
            rvPopularMovie.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = popMovAdapter
            }

            rvTopRatedMovie.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = topRateMovAdapter
            }
        }
    }

    private fun setUpListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                homeViewModel.popularMovies.collect(::popMovListener)
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                homeViewModel.topRatedMovies.collect(::topRatedListener)
            }
        }
    }

    private fun popMovListener(data: Resource<List<MoviePoster>>) {
        with(binding) {
            when(data) {
                is Resource.Loading -> {
                    shimmerPopularMovie.makeVisible()
                    rvPopularMovie.makeGone()
                }
                is Resource.Success -> {
                    shimmerPopularMovie.makeGone()
                    rvPopularMovie.makeVisible()
                    data.data?.let { popMovAdapter.setData(it) }
                }
                else -> {
                    shimmerPopularMovie.makeGone()
                    Toast.makeText(requireContext(), data.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun topRatedListener(data: Resource<List<MoviePoster>>) {
        with(binding) {
            when(data) {
                is Resource.Loading -> {
                    shimmerTopRatedMovie.makeVisible()
                    rvTopRatedMovie.makeGone()
                }
                is Resource.Success -> {
                    shimmerTopRatedMovie.makeGone()
                    rvTopRatedMovie.makeVisible()
                    data.data?.let { topRateMovAdapter.setData(it) }
                }
                else -> {
                    shimmerTopRatedMovie.makeGone()
                    Toast.makeText(requireContext(), data.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}