package com.gureev.omdbapp.utils

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

const val TAG = "SupportFunctions"

object SupportFunctions {

    fun checkOrientationAndGetLayoutManager(context: Context, config: Configuration) =
        when (config.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = wm.defaultDisplay
                Log.d(
                    TAG,
                    "checkOrientationAndGetLayoutManager: ${display.width}x${display.height}"
                )
                val columnCount = when {
                    display.width >= 1400 -> 2
                    else -> 1
                }
                GridLayoutManager(context, columnCount)
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val display = wm.defaultDisplay
                Log.d(
                    TAG,
                    "checkOrientationAndGetLayoutManager: ${display.width}x${display.height}"
                )
                val columnCount = when {
                    display.width >= 1000 -> 2
                    else -> 1
                }
                GridLayoutManager(context, columnCount)
            }
            Configuration.ORIENTATION_UNDEFINED -> {
                LinearLayoutManager(context)
            }
            else -> {
                LinearLayoutManager(context)
            }
        }
}