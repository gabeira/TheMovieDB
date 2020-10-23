package org.themoviedb.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_detail_content.*
import org.themoviedb.R
import org.themoviedb.data.ConfigurationRepository
import org.themoviedb.ui.home.HomeFragment
import java.util.concurrent.TimeUnit

class DetailActivity : AppCompatActivity() {

    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movieId = intent.getIntExtra(HomeFragment.MOVIE_ID_KEY, 0)
        val titleText = intent.getStringExtra(HomeFragment.TITLE_KEY)
        movieDetailViewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        movieDetailViewModel.loadMovieWith(movieId)
        movieDetailViewModel.movieLiveData.observe(this, { data ->
            data?.let { movieDetail ->
                title = titleText
                mediaTitle.text = movieDetail.title
                mediaImage.contentDescription = movieDetail.title
                genre.text = getString(R.string.genre, movieDetail.genres.joinToString { it.name })

                val hour = TimeUnit.MINUTES.toHours(movieDetail.runtime)
                val minute = movieDetail.runtime - TimeUnit.HOURS.toMinutes(hour)
                val timeText = hour.toString() + "h " + minute + "m"
                runningTime.text = getString(R.string.running_time, timeText)

                vote.text = getString(R.string.vote_average, movieDetail.vote_average.toString())

                val poster =
                    ConfigurationRepository.imageURL342 + intent.getStringExtra(HomeFragment.IMAGE_KEY)
                Glide.with(applicationContext)
                    .load(poster)
                    .error(R.drawable.ic_image_24)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    }).into(mediaImage)
            }
        })

        close.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
        super.onBackPressed()
    }
}
