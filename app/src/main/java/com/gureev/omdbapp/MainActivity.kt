package com.gureev.omdbapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.gureev.omdbapp.fragments.favorites.FavoriteMoviesFragment
import com.gureev.omdbapp.fragments.profile.ProfileFragment
import com.gureev.omdbapp.fragments.search.SearchMoviesFragment
import com.gureev.omdbapp.repository.MainRepository
import com.gureev.omdbapp.utils.UtilVariables
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var repository: MainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            switchFragment(FavoriteMoviesFragment())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_item_favorite -> {
            switchFragment(FavoriteMoviesFragment())
            true
        }
        R.id.menu_item_search -> {
            switchFragment(SearchMoviesFragment())
            true
        }
        R.id.menu_item_profile -> {
            switchFragment(ProfileFragment())
            true
        }
        R.id.menu_item_clear_cache -> {
            removeAllCachedMovies()
            true
        }
        R.id.menu_item_clear_favorite -> {
            removeAllFavoriteMovies()
            true
        }
        R.id.menu_item_load_test_data -> {
            loadToRoomTestData()
            true
        }
        R.id.menu_item_exit -> {
            finish()
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun switchFragment(newFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(newFragment.javaClass.name)
        fragmentManager.commit {
            setReorderingAllowed(true)
            setCustomAnimations(
                R.anim.slide_in,//enter
                R.anim.fade_out,//exit
                R.anim.fade_in,//popEnter
                R.anim.slide_out//popExit
            )
            if (fragment != null) {
                Log.d(
                    TAG,
                    "switchFragment: get in backstack and replace fragment ${fragment.javaClass.name}"
                )
                replace(R.id.fragment_container_view, fragment)
                addToBackStack(null)
            } else {
                Log.d(TAG, "switchFragment: add new fragment ${newFragment.javaClass.name}")
                add(R.id.fragment_container_view, newFragment, newFragment.javaClass.name)
                replace(R.id.fragment_container_view, newFragment)
                addToBackStack(newFragment.javaClass.name)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun loadToRoomTestData() {

        repository.diskDataSource.insertMoviesToFavorite(UtilVariables.testFavoriteList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorComplete {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                Log.d(TAG, "insertMoviesToFavorite onErrorComplete: $it")
                true
            }.subscribe {
                Log.d(TAG, "insertMoviesToFavorite subscribe")
            }
    }

    private fun removeAllFavoriteMovies() {
        repository.diskDataSource.removeAllFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorComplete {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                Log.d(TAG, "removeAllFavoriteMovies onErrorComplete: $it")
                true
            }
            .subscribe {
                Log.d(TAG, "removeAllFavoriteMovies subscribe")
            }
    }

    private fun removeAllCachedMovies() {
        repository.diskDataSource.removeAllCachedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorComplete {
                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                Log.d(TAG, "removeAllCachedMovies onErrorComplete: $it")
                true
            }
            .subscribe {
                Log.d(TAG, "removeAllCachedMovies subscribe")
            }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}