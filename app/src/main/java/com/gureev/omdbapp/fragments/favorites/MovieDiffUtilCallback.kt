package com.gureev.omdbapp.fragments.favorites

import androidx.recyclerview.widget.DiffUtil
import com.gureev.omdbapp.repository.entities.MovieShort

class MovieDiffUtilCallback(
    private val oldList: List<MovieShort>,
    private val newList: List<MovieShort>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = oldList[oldItemPosition]
        val newMovie = newList[newItemPosition]
        return oldMovie.imdbID.equals(newMovie.imdbID)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = oldList[oldItemPosition]
        val newMovie = newList[newItemPosition]
        return oldMovie.imdbID.equals(newMovie.imdbID) &&
                oldMovie.poster.equals(newMovie.poster) &&
                oldMovie.title.equals(newMovie.title) &&
                oldMovie.type.equals(newMovie.type) &&
                oldMovie.year.equals(newMovie.year)

    }
}