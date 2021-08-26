package com.gureev.omdbapp.repository

import com.gureev.omdbapp.repository.source.DiskDataSource
import com.gureev.omdbapp.repository.source.NetworkDataSource
import javax.inject.Inject

class MainRepository @Inject constructor(
    val diskDataSource: DiskDataSource,
    val networkDataSource: NetworkDataSource
)