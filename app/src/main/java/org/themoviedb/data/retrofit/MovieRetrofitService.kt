package org.themoviedb.data.retrofit

import org.themoviedb.data.model.MovieDetail
import org.themoviedb.data.model.MoviesRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRetrofitService {

    @GET("movie/{id}")
    suspend fun requestMovieDetail(
        @Path("id") id: Int,
        @Query("api_key") key: String,
        @Query("language") language: String
    ): Response<MovieDetail>

    @GET("movie/latest")
    suspend fun requestLatestMovie(
        @Query("api_key") key: String,
        @Query("language") language: String
    ): Response<MovieDetail>

    @GET("movie/now_playing")
    suspend fun requestNowPlayingMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesRequest>

    @GET("movie/popular")
    suspend fun requestPopularMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesRequest>

    @GET("movie/top_rated")
    suspend fun requestTopRatedMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesRequest>

    @GET("movie/upcoming")
    suspend fun requestUpcomingMovies(
        @Query("api_key") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesRequest>
}
