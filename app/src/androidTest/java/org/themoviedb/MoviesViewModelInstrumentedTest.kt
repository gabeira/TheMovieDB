package org.themoviedb

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.themoviedb.ui.home.MoviesViewModel

@LargeTest
@RunWith(AndroidJUnit4::class)
class MoviesViewModelInstrumentedTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun nowPlayingMoviesViewModelTest() {
        Handler(Looper.getMainLooper()).post {

            val moviesViewModel = ViewModelProvider
                .AndroidViewModelFactory(mActivityTestRule.activity.application)
                .create(MoviesViewModel::class.java)
            moviesViewModel.reload()
            moviesViewModel.nowPlayingMovies.observe(mActivityTestRule.activity, { data ->
                data?.let {
                    Assert.assertNotNull(it)
                    Assert.assertThat(
                        it.results.size.toString(),
                        Matchers.greaterThanOrEqualTo("1")
                    )
                }
            })
        }
    }

    @Test
    fun popularMoviesViewModelTest() {
        Handler(Looper.getMainLooper()).post {

            val moviesViewModel = ViewModelProvider
                .AndroidViewModelFactory(mActivityTestRule.activity.application)
                .create(MoviesViewModel::class.java)
            moviesViewModel.reload()
            moviesViewModel.popularMovies.observe(mActivityTestRule.activity, { data ->
                data?.let {
                    Assert.assertNotNull(it)
                    Assert.assertThat(
                        it.results.size.toString(),
                        Matchers.greaterThanOrEqualTo("1")
                    )
                }
            })
        }
    }

    @Test
    fun topRatedMoviesViewModelTest() {
        Handler(Looper.getMainLooper()).post {

            val moviesViewModel = ViewModelProvider
                .AndroidViewModelFactory(mActivityTestRule.activity.application)
                .create(MoviesViewModel::class.java)
            moviesViewModel.reload()
            moviesViewModel.topRatedMovies.observe(mActivityTestRule.activity, { data ->
                data?.let {
                    Assert.assertNotNull(it)
                    Assert.assertThat(
                        it.results.size.toString(),
                        Matchers.greaterThanOrEqualTo("1")
                    )
                }
            })
        }
    }

    @Test
    fun upcomingMoviesViewModelTest() {
        Handler(Looper.getMainLooper()).post {

            val moviesViewModel = ViewModelProvider
                .AndroidViewModelFactory(mActivityTestRule.activity.application)
                .create(MoviesViewModel::class.java)
            moviesViewModel.reload()
            moviesViewModel.upcomingMovies.observe(mActivityTestRule.activity, { data ->
                data?.let {
                    Assert.assertNotNull(it)
                    Assert.assertThat(
                        it.results.size.toString(),
                        Matchers.greaterThanOrEqualTo("1")
                    )
                }
            })
        }
    }
}