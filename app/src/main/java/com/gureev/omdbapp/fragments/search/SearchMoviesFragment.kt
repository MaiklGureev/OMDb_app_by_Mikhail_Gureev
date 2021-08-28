package com.gureev.omdbapp.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.gureev.omdbapp.R
import com.gureev.omdbapp.databinding.FragmentSearchMoviesBinding
import com.gureev.omdbapp.fragments.favorites.FavoriteMoviesAdapter
import com.gureev.omdbapp.utils.SupportFunctions
import com.jakewharton.rxbinding4.appcompat.queryTextChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit


class SearchMoviesFragment : Fragment() {

    private val TAG = "SearchMoviesFragment"

    companion object {
        fun newInstance() = SearchMoviesFragment()
    }

    private var _binding: FragmentSearchMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchMoviesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchMoviesBinding.inflate(inflater, container, false)
        val view = binding.root

        initSearchViewAndRecyclerView()

        return view
    }

    private fun initSearchViewAndRecyclerView() {

        binding.searchView.queryTextChanges()
            .map { text -> text.toString().lowercase(Locale.getDefault()).trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { text ->
                if (text.isEmpty() || text == null) return@doOnNext

                viewModel.search(text, 1)
                binding.recyclerView.visibility = View.INVISIBLE
                binding.progressBarLoading.visibility = View.VISIBLE
            }
            .subscribe()

        binding.imageButtonNextPage.setOnClickListener {
            viewModel.nextPage()
        }

        binding.imageButtonPrevPage.setOnClickListener {
            viewModel.prevPage()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteMoviesAdapter = FavoriteMoviesAdapter(emptyList())
        val rw = view.findViewById<RecyclerView>(R.id.recycler_view)
        val pb = view.findViewById<ProgressBar>(R.id.progress_bar_loading)

        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            adapter = favoriteMoviesAdapter
            layoutManager = SupportFunctions.checkOrientationAndGetLayoutManager(
                context,
                resources.configuration
            )
        }
        viewModel.movieShortListLiveData.observe(viewLifecycleOwner, { list ->
            favoriteMoviesAdapter.setNewDataAndUpdateRecycler(list)
            rw.visibility = View.VISIBLE
            pb.visibility = View.INVISIBLE
            Log.d(TAG, "movieShortListLiveData: $list")
        })
    }
}