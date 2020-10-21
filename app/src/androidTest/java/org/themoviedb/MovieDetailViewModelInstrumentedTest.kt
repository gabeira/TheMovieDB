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
import org.themoviedb.ui.detail.DetailActivity
import org.themoviedb.ui.detail.MovieDetailViewModel

@LargeTest
@RunWith(AndroidJUnit4::class)
class MovieDetailViewModelInstrumentedTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(DetailActivity::class.java)

    @Test
    fun upcomingMoviesViewModelTest() {
        Handler(Looper.getMainLooper()).post {

            val moviesViewModel = ViewModelProvider
                .AndroidViewModelFactory(mActivityTestRule.activity.application)
                .create(MovieDetailViewModel::class.java)
            moviesViewModel.loadMovieWith(755816)
            moviesViewModel.movieLiveData.observe(mActivityTestRule.activity, { data ->
                data?.let {
                    Assert.assertNotNull(it)
                    Assert.assertThat(it.title, Matchers.greaterThanOrEqualTo("Perif"))
                    Assert.assertThat(it.id.toString(), Matchers.greaterThanOrEqualTo("755816"))
                }
            })
        }
    }
}