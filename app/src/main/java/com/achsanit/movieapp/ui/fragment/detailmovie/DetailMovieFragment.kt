package com.achsanit.movieapp.ui.fragment.detailmovie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.achsanit.movieapp.BuildConfig
import com.achsanit.movieapp.R
import com.achsanit.movieapp.data.entity.DetailMovieEntity
import com.achsanit.movieapp.data.entity.GenreEntity
import com.achsanit.movieapp.data.entity.MoviePoster
import com.achsanit.movieapp.databinding.FragmentDetailMovieBinding
import com.achsanit.movieapp.ui.adapter.CastMovieAdapter
import com.achsanit.movieapp.ui.adapter.PosterMovieAdapter
import com.achsanit.movieapp.utils.Resource
import com.achsanit.movieapp.utils.makeGone
import com.achsanit.movieapp.utils.makeVisible
import com.achsanit.movieapp.utils.setShimmerPlaceholder
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val navArgs: DetailMovieFragmentArgs by navArgs()
    private val detailMovViewModel: DetailMovieViewModel by viewModel()

    private val castAdapter: CastMovieAdapter by lazy {
        CastMovieAdapter {
            val action =
                DetailMovieFragmentDirections.actionDetailMovieFragmentToDetailCastFragment(it.id)
            findNavController().navigate(action)
        }
    }
    private val similarMovieAdapter: PosterMovieAdapter by lazy {
        PosterMovieAdapter {
            detailMovViewModel.refreshPage(it.movieId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navArgs.movieId.let {
            detailMovViewModel.getDetailMovie(it)
            detailMovViewModel.getSimilarMovies(it)
        }

        initView()

        setUpListener()
    }

    private fun setUpListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                detailMovViewModel.detailMovie.collect(::detailMovieListener)
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                detailMovViewModel.similarMovies.collect(::similarMovieListener)
            }
        }
    }

    private fun similarMovieListener(data: Resource<List<MoviePoster>>) {
        with(binding) {
            when(data) {
                is Resource.Loading -> {
                    shimmerSimilarMovie.makeVisible()
                    rvSimilarMovie.makeGone()
                }
                is Resource.Success -> {
                    shimmerSimilarMovie.makeGone()
                    rvSimilarMovie.makeVisible()
                    data.data?.let { similarMovieAdapter.setData(it) }
                }
                else -> {
                    shimmerSimilarMovie.makeGone()
                    Toast.makeText(requireContext(), data.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun detailMovieListener(data: Resource<DetailMovieEntity>) {
        with(binding) {
            when (data) {
                is Resource.Loading -> {
                    pbDetail.makeVisible()
                }

                is Resource.Success -> {
                    pbDetail.makeGone()
                    data.data?.let {
                        val baseImageUrl = BuildConfig.BASE_IMAGE_URL
                        castAdapter.setData(it.cast)
                        ivBackdrop.load("$baseImageUrl${it.backdropPath}") {
                            setShimmerPlaceholder()
                        }
                        ivPoster.load("$baseImageUrl${it.posterPath}") {
                            setShimmerPlaceholder()
                        }
                        tvOverview.text = it.overview
                        tvTitleMovie.text = it.title
                        tvReleasePlayedTime.text = resources.getString(
                            R.string.release_duration_time_placeholder,
                            it.releaseDate,
                            it.durationTime
                        )

                        addGenreChip(it.genres)
                    }
                }

                is Resource.Error -> {
                    pbDetail.makeGone()
                }

                else -> {
                    pbDetail.makeGone()
                }
            }
        }
    }

    private fun initView() {
        with(binding) {
            rvActors.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = castAdapter
            }

            rvSimilarMovie.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = similarMovieAdapter
            }

            buttonBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun addGenreChip(data: List<GenreEntity>) {
        with(binding) {
            cgGenre.removeAllViews()

            data.forEach { genreItem ->
                val newChip = Chip(requireContext())
                newChip.text = genreItem.name

                cgGenre.addView(newChip)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}