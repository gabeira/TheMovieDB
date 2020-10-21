package org.themoviedb.data

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.themoviedb.BuildConfig
import org.themoviedb.data.model.MovieDetail
import org.themoviedb.data.model.MoviesRequest
import org.themoviedb.data.retrofit.MovieRetrofitNetwork
import org.themoviedb.data.retrofit.MovieRetrofitService
import java.util.*
import kotlin.coroutines.CoroutineContext

class MoviesRepository(private val coroutineContext: CoroutineContext) {

    val latestLiveData: MutableLiveData<MovieDetail> = MutableLiveData()
    val nowPlayingLiveData: MutableLiveData<MoviesRequest> = MutableLiveData()
    val popularLiveData: MutableLiveData<MoviesRequest> = MutableLiveData()
    val topLiveData: MutableLiveData<MoviesRequest> = MutableLiveData()
    val upcomingLiveData: MutableLiveData<MoviesRequest> = MutableLiveData()
    val networkErrors = MutableLiveData<String>()

    private val apiKey = BuildConfig.API_KEY

    private val service: MovieRetrofitService by lazy {
        MovieRetrofitNetwork.makeRetrofitService().create(MovieRetrofitService::class.java)
    }

    fun loadMovieDetailFromServer(id: Int) {
        GlobalScope.launch(coroutineContext) {
            try {
                val request = service.requestMovieDetail(
                    id, apiKey, Locale.getDefault().toLanguageTag()
                )
                if (request.isSuccessful) {
                    latestLiveData.value = request.body()
                } else {
                    networkErrors.postValue("Could not load ${request.errorBody()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                networkErrors.postValue(e.message)
            }
        }
    }

    fun loadLatestMovieFromServer() {
        GlobalScope.launch(coroutineContext) {
            try {
                val request = service.requestLatestMovie(
                    apiKey, Locale.getDefault().toLanguageTag()
                )
                if (request.isSuccessful) {
                    latestLiveData.value = request.body()
                } else {
                    networkErrors.postValue("Could not load ${request.errorBody()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                networkErrors.postValue(e.message)
            }
        }
    }

    fun loadNowPlayingMoviesFromServer() {
        GlobalScope.launch(coroutineContext) {
            try {
                val request = service.requestNowPlayingMovies(
                    apiKey, Locale.getDefault().toLanguageTag(), 1
                )
                if (request.isSuccessful) {
                    nowPlayingLiveData.value = request.body()
                } else {
                    networkErrors.postValue("Could not load ${request.errorBody()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                networkErrors.postValue(e.message)
            }
        }
    }

    fun loadPopularMoviesFromServer() {
        GlobalScope.launch(coroutineContext) {
            try {
                val request = service.requestPopularMovies(
                    apiKey, Locale.getDefault().toLanguageTag(), 1
                )
                if (request.isSuccessful) {
                    popularLiveData.value = request.body()
                } else {
                    networkErrors.postValue("Could not load ${request.errorBody()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                networkErrors.postValue(e.message)
            }
        }
    }

    fun loadTopRatedMoviesFromServer() {
        GlobalScope.launch(coroutineContext) {
            try {
                val request = service.requestTopRatedMovies(
                    apiKey, Locale.getDefault().toLanguageTag(), 1
                )
                if (request.isSuccessful) {
                    topLiveData.value = request.body()
                } else {
                    networkErrors.postValue("Could not load ${request.errorBody()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                networkErrors.postValue(e.message)
            }
        }
    }

    fun loadUpcomingMoviesFromServer() {
        GlobalScope.launch(coroutineContext) {
            try {
                val request = service.requestUpcomingMovies(
                    apiKey, Locale.getDefault().toLanguageTag(), 1
                )
                if (request.isSuccessful) {
                    upcomingLiveData.value = request.body()
                } else {
                    networkErrors.postValue("Could not load ${request.errorBody()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                networkErrors.postValue(e.message)
            }
        }
    }
}