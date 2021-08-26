package com.gureev.omdbapp.fragments.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import coil.request.CachePolicy
import com.gureev.omdbapp.R
import com.gureev.omdbapp.databinding.FragmentMovieBinding
import com.gureev.omdbapp.repository.entities.MovieFull
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class MovieFragment() : Fragment() {

    private val viewModel: MovieViewModel by activityViewModels()
    private val TAG = "MovieFragment"

    var imdbID: String = ""
    val imdbIDKey: String = "imdbID"

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private var buttonAddToFavoriteDisposable: io.reactivex.rxjava3.disposables.Disposable? = null

    constructor(imdbID: String) : this() {
        this.imdbID = imdbID
    }

    companion object {
        fun newInstance(imdbID: String) = MovieFragment(imdbID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        initFields()
        return binding.root
    }

    private fun switchTextOnButton(isFavoriteMovie: Boolean) {
        if (isFavoriteMovie) {
            binding.buttonAddToFavorite?.text = getString(R.string.movie_in_favorites)
        } else {
            binding.buttonAddToFavorite?.text = getString(R.string.add_movie_to_favorites)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(imdbIDKey, imdbID)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            imdbID = savedInstanceState.getString(imdbIDKey, "")
        }
    }

    private fun initFields() {
        setDataToView(MovieFull())

        viewModel.movieFull.observe(viewLifecycleOwner, {
            setDataToView(it)
        })

        viewModel.isFavoriteMovie.observe(viewLifecycleOwner, {
            switchTextOnButton(it)
        })

        viewModel.movieFullSingle(imdbID)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { movieFull ->
                Log.d(TAG, "getCachedMovieFullSingle: read in cache ")
                Log.d(TAG, "getCachedMovieFullSingle: $movieFull")
                Log.d(TAG, "getCachedMovieFullSingle: Success")
            }
            .subscribe()
    }

    private fun setDataToView(movieFull: MovieFull) {
        if (movieFull.imdbID == "") {
            binding.mainConstraintLayout?.visibility = View.GONE
            binding.progressBarLoading?.visibility = View.VISIBLE
            return
        }

        binding.mainConstraintLayout?.visibility = View.VISIBLE
        binding.progressBarLoading?.visibility = View.GONE

        binding.textViewTitle.text = movieFull.title
        binding.textViewYear.text = movieFull.year
        binding.textViewPlot.text = movieFull.plot
        binding.textViewType.text = movieFull.type
        binding.textViewActors.text =
            getString(R.string.pattern_actors, movieFull.actors)
        binding.textViewCountry.text =
            getString(R.string.pattern_country, movieFull.country)
        binding.textViewGenre.text =
            getString(R.string.pattern_genre, movieFull.genre)
        binding.textViewLanguage.text =
            getString(R.string.pattern_language, movieFull.language)
        binding.textViewRating.text =
            getString(R.string.pattern_rating, movieFull.imdbRating.toString())
        binding.textViewReleaseDate.text =
            getString(R.string.pattern_release_date, movieFull.released)
        binding.imageViewPoster.load(movieFull.poster) {
            networkCachePolicy(CachePolicy.ENABLED)
            diskCachePolicy(CachePolicy.ENABLED)
            memoryCachePolicy(CachePolicy.ENABLED)
            error(R.drawable.empty_image)
        }
        buttonAddToFavoriteDisposable = binding.buttonAddToFavorite?.clicks()
            ?.doOnNext {
                Log.d(TAG, "buttonAddToFavorite: doOnNext")
                switchTextOnButton(viewModel.switchIsFavoriteMovieAndGetNewValue())
            }
            ?.debounce(250, TimeUnit.MILLISECONDS)
            ?.subscribe {
                Log.d(TAG, "buttonAddToFavorite: subscribe onNext")
                viewModel.addOrDeleteMovieInFavorites()
            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearLiveData()
        buttonAddToFavoriteDisposable?.dispose()
    }
}