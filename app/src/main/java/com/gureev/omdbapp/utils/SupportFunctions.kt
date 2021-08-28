package com.gureev.omdbapp.utils

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager

const val TAG = "SupportFunctions"

object SupportFunctions {

    fun checkOrientationAndGetLayoutManager(context: Context, config: Configuration) =
        when (config.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                GridLayoutManager(context, 1)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = wm.defaultDisplay
                Log.d(
                    TAG,
                    "checkOrientationAndGetLayoutManager: ${display.width}x${display.height}"
                )
                val columnCount = when {
                    display.width >= 900 -> 2
                    else -> 1
                }
                GridLayoutManager(context, columnCount)
            }
            Configuration.ORIENTATION_UNDEFINED -> {
                GridLayoutManager(context, 1)
            }
            else -> {
                GridLayoutManager(context, 1)
            }
        }
}