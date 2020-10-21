package org.themoviedb.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.themoviedb.data.MoviesRepository
import org.themoviedb.data.model.MovieDetail
import org.themoviedb.data.model.MoviesRequest
import kotlin.coroutines.CoroutineContext

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val catalogDataRepository: MoviesRepository = MoviesRepository(coroutineContext)
    val latestMovie: LiveData<MovieDetail>
    val nowPlayingMovies: LiveData<MoviesRequest>
    val popularMovies: LiveData<MoviesRequest>
    val topRatedMovies: LiveData<MoviesRequest>
    val upcomingMovies: LiveData<MoviesRequest>

    init {
        latestMovie = catalogDataRepository.latestLiveData
        nowPlayingMovies = catalogDataRepository.nowPlayingLiveData
        popularMovies = catalogDataRepository.popularLiveData
        topRatedMovies = catalogDataRepository.topLiveData
        upcomingMovies = catalogDataRepository.upcomingLiveData
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun reload() {
        catalogDataRepository.loadLatestMovieFromServer()
        catalogDataRepository.loadNowPlayingMoviesFromServer()
        catalogDataRepository.loadPopularMoviesFromServer()
        catalogDataRepository.loadTopRatedMoviesFromServer()
        catalogDataRepository.loadUpcomingMoviesFromServer()
    }

    fun getNetworkErrors() = catalogDataRepository.networkErrors
}