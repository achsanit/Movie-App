package com.achsanit.movieapp.ui.fragment.detailcast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.achsanit.movieapp.BuildConfig
import com.achsanit.movieapp.data.entity.DetailPersonEntity
import com.achsanit.movieapp.databinding.FragmentDetailCastBinding
import com.achsanit.movieapp.ui.adapter.ActingAdapter
import com.achsanit.movieapp.ui.adapter.PosterMovieAdapter
import com.achsanit.movieapp.utils.Resource
import com.achsanit.movieapp.utils.makeGone
import com.achsanit.movieapp.utils.makeVisible
import com.achsanit.movieapp.utils.setShimmerPlaceholder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCastFragment : Fragment() {

    private var _binding: FragmentDetailCastBinding? = null
    private val binding get() = _binding!!

    private val detailCastViewModel: DetailCastViewModel by viewModel()
    private val navArgs: DetailCastFragmentArgs by navArgs()

    private val knownMovieAdapter: PosterMovieAdapter by lazy {
        PosterMovieAdapter { }
    }
    private val actingAdapter: ActingAdapter by lazy {
        ActingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailCastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navArgs.personId.let {
            detailCastViewModel.getDetailPerson(it)
        }

        setupListener()

        initView()

    }

    private fun setupListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                detailCastViewModel.detailCast.collect(::detailPersonListener)
            }
        }
    }

    private fun detailPersonListener(data: Resource<DetailPersonEntity>) {
        with(binding) {
            when(data) {
                is Resource.Loading -> {
                    pbDetailCast.makeVisible()
                    rvKnownFor.makeGone()
                    rvActing.makeGone()
                }
                is Resource.Success -> {
                    pbDetailCast.makeGone()
                    rvKnownFor.makeVisible()
                    rvActing.makeVisible()

                    data.data?.let { setDataView(it) }
                }
                else -> {
                    pbDetailCast.makeGone()
                }
            }
        }
    }

    private fun setDataView(data: DetailPersonEntity) {
        with(binding) {
            tvPersonName.text = data.name
            tvBiography.text = data.biography
            tvGender.text = data.gender
            tvBirthday.text = data.birthday
            tvPlaceBirth.text = data.placeOfBirth

            if (data.dateOfDeath.isNotBlank()) {
                tvDateOfDeath.makeVisible()
                tvTitleDateOfDeath.makeVisible()
                tvDateOfDeath.text = data.dateOfDeath
            } else {
                tvDateOfDeath.makeGone()
                tvTitleDateOfDeath.makeGone()
            }

            val baseImageUrl = BuildConfig.BASE_IMAGE_URL
            data.profilePath.let {
                if (it.isNotBlank()) {
                    ivPerson.load("$baseImageUrl${it}") { setShimmerPlaceholder() }
                }
            }

            knownMovieAdapter.setData(data.knownMovies)
            actingAdapter.setData(data.acting.sortedByDescending { it.year })
        }
    }

    private fun initView() {
        with(binding) {
            rvKnownFor.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = knownMovieAdapter
            }
            rvActing.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = actingAdapter
            }

            ibBackToolbar.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}