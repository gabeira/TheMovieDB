package org.themoviedb.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.themoviedb.data.MoviesRepository
import org.themoviedb.data.model.MovieDetail
import kotlin.coroutines.CoroutineContext

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val catalogDataRepository: MoviesRepository = MoviesRepository(coroutineContext)
    val movieLiveData: LiveData<MovieDetail>

    init {
        movieLiveData = catalogDataRepository.latestLiveData
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun loadMovieWith(id: Int) {
        catalogDataRepository.loadMovieDetailFromServer(id)
    }

    fun getNetworkErrors() = catalogDataRepository.networkErrors
}