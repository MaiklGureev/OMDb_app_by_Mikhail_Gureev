package com.gureev.omdbapp.fragments.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gureev.omdbapp.databinding.FragmentFavoriteMoviesBinding
import com.gureev.omdbapp.utils.SupportFunctions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FavoriteMoviesFragment : Fragment() {

    private var _binding: FragmentFavoriteMoviesBinding? = null
    private val binding get() = _binding!!

    private var disposable: Disposable? = null

    private val viewModel: FavoriteMoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteMoviesBinding.inflate(inflater, container, false)

        val favoriteMoviesAdapter = FavoriteMoviesAdapter(emptyList())
        binding.recyclerView.apply {
            adapter = favoriteMoviesAdapter
            layoutManager = SupportFunctions.checkOrientationAndGetLayoutManager(
                context,
                resources.configuration
            )
        }

        disposable = viewModel.getFromRoomListMovieShortObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { list ->
                favoriteMoviesAdapter.setNewDataAndUpdateRecycler(list)
            }
            .subscribeOn(Schedulers.io())
            .subscribe()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
        _binding = null
    }
}