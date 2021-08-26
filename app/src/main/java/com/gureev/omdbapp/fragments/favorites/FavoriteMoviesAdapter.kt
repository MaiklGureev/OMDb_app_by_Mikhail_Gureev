package com.gureev.omdbapp.fragments.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.gureev.omdbapp.R
import com.gureev.omdbapp.databinding.ItemMovieBinding
import com.gureev.omdbapp.fragments.movie.MovieFragment
import com.gureev.omdbapp.repository.entities.MovieShort


class FavoriteMoviesAdapter(
    private var movieList: List<MovieShort>
) : RecyclerView.Adapter<FavoriteMoviesAdapter.MovieHolder>() {

    fun setNewDataAndUpdateRecycler(newMovieList: List<MovieShort>) {
        val newsDiffUtilCallback = MovieDiffUtilCallback(movieList, newMovieList)
        val difResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(newsDiffUtilCallback)
        movieList = newMovieList
        difResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemBinding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movie = movieList[holder.bindingAdapterPosition]
        holder.bind(movie)
        holder.itemView.setOnClickListener { view ->
            (view.context as AppCompatActivity).supportFragmentManager.commit {
                val movieFragment = MovieFragment.newInstance(movie.imdbID)
                setReorderingAllowed(true)
                setCustomAnimations(
                    R.anim.slide_in,//enter
                    R.anim.fade_out,//exit
                    R.anim.fade_in,//popEnter
                    R.anim.slide_out//popExit
                )
                add(R.id.fragment_container_view, movieFragment, movieFragment.javaClass.name)
                replace(R.id.fragment_container_view, movieFragment)
                addToBackStack(movieFragment.javaClass.name)
            }
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieShort) {
            binding.textViewTitle.text = movie.title
            binding.textViewYear.text = movie.year
            binding.textViewType.text = movie.type
            binding.imageViewPoster.load(movie.poster) {
                networkCachePolicy(CachePolicy.ENABLED)
                diskCachePolicy(CachePolicy.ENABLED)
                memoryCachePolicy(CachePolicy.ENABLED)
                error(R.drawable.empty_image)
            }
        }
    }
}