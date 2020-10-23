package org.themoviedb.ui.home

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_home.*
import org.themoviedb.R
import org.themoviedb.data.ConfigurationRepository
import org.themoviedb.data.model.MovieDetail
import org.themoviedb.data.model.MovieResult
import org.themoviedb.data.model.MoviesRequest
import org.themoviedb.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private lateinit var moviesViewModel: MoviesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        moviesViewModel.reload()
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val swipeRefreshLayout: SwipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout)
        moviesViewModel.latestMovie.observe(viewLifecycleOwner, { movieDetail ->
            handleLatestMovie(movieDetail)
        })
        //TODO Add pagination
        moviesViewModel.nowPlayingMovies.observe(viewLifecycleOwner, {
            handleMovieRequest(it, Category.NowPlaying)
        })
        moviesViewModel.popularMovies.observe(viewLifecycleOwner, {
            handleMovieRequest(it, Category.Popular)
        })
        moviesViewModel.topRatedMovies.observe(viewLifecycleOwner, {
            handleMovieRequest(it, Category.TopRated)
        })
        moviesViewModel.upcomingMovies.observe(viewLifecycleOwner, {
            handleMovieRequest(it, Category.Upcoming)
        })
        swipeRefreshLayout.setOnRefreshListener {
            moviesViewModel.reload()
        }
        moviesViewModel.getNetworkErrors().observe(viewLifecycleOwner, {
            swipeRefreshLayout.isRefreshing = false
            showEmptyListMessage(true)
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
        return root
    }

    private fun handleMovieRequest(moviesRequest: MoviesRequest?, category: Category) {
        swipeRefreshLayout.isRefreshing = false
        moviesRequest?.let {
            showEmptyListMessage(it.results.isEmpty())
            when (category) {
                Category.NowPlaying -> {
                    nowPlayingCategory.visibility = View.VISIBLE
                    nowPlayingRecyclerView.adapter =
                        MovieListAdapter(it, onMediaListInteractionListener())
                }
                Category.Popular -> {
                    popularCategory.visibility = View.VISIBLE
                    popularRecyclerView.adapter =
                        MovieListAdapter(it, onMediaListInteractionListener())
                }
                Category.TopRated -> {
                    topRatedCategory.visibility = View.VISIBLE
                    topRatedRecyclerView.adapter =
                        MovieListAdapter(it, onMediaListInteractionListener())
                }
                Category.Upcoming -> {
                    upcomingCategory.visibility = View.VISIBLE
                    upcomingRecyclerView.adapter =
                        MovieListAdapter(it, onMediaListInteractionListener())
                }
            }
        }
    }

    private fun handleLatestMovie(movieDetail: MovieDetail?) {
        latestMovieImage.setOnClickListener {
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra(MOVIE_ID_KEY, movieDetail?.id)
            intent.putExtra(TITLE_KEY, movieDetail?.title)
            intent.putExtra(IMAGE_KEY, movieDetail?.poster_path)
            val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                    requireActivity(),
                    it,
                    getString(R.string.image_transition)
                )
            startActivity(intent, options.toBundle())
        }
        movieDetail?.backdrop_path?.let { backdropPathImageUrl ->
            latestMovieFrame.visibility = View.VISIBLE
            Glide.with(this)
                .load(ConfigurationRepository.imageURL780 + backdropPathImageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        latestMovieFrame.visibility = View.GONE
                        latestMovieProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?, target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        latestMovieProgressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(latestMovieImage)
        } ?: run {
            latestMovieFrame.visibility = View.GONE
            latestMovieProgressBar.visibility = View.GONE
        }
    }

    interface OnMediaListInteractionListener {
        fun onMediaItemClick(mediaItem: MovieResult, imageView: View)
    }

    private fun onMediaListInteractionListener(): OnMediaListInteractionListener {
        return object : OnMediaListInteractionListener {
            override fun onMediaItemClick(mediaItem: MovieResult, imageView: View) {
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra(MOVIE_ID_KEY, mediaItem.id)
                intent.putExtra(TITLE_KEY, mediaItem.title)
                intent.putExtra(IMAGE_KEY, mediaItem.poster_path)
                val options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(
                        requireActivity(),
                        imageView,
                        getString(R.string.image_transition)
                    )
                startActivity(intent, options.toBundle())
            }
        }
    }

    private fun showEmptyListMessage(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            nowPlayingRecyclerView.visibility = View.GONE
            popularRecyclerView.visibility = View.GONE
            topRatedRecyclerView.visibility = View.GONE
            upcomingRecyclerView.visibility = View.GONE
            nowPlayingCategory.visibility = View.INVISIBLE
            popularCategory.visibility = View.INVISIBLE
            topRatedCategory.visibility = View.INVISIBLE
            upcomingCategory.visibility = View.INVISIBLE
        } else {
            emptyList.visibility = View.GONE
            nowPlayingRecyclerView.visibility = View.VISIBLE
            popularRecyclerView.visibility = View.VISIBLE
            topRatedRecyclerView.visibility = View.VISIBLE
            upcomingRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (moviesViewModel.latestMovie.hasObservers()) {
            moviesViewModel.latestMovie.removeObservers(this)
        }
        if (moviesViewModel.nowPlayingMovies.hasObservers()) {
            moviesViewModel.nowPlayingMovies.removeObservers(this)
        }
        if (moviesViewModel.popularMovies.hasObservers()) {
            moviesViewModel.popularMovies.removeObservers(this)
        }
        if (moviesViewModel.topRatedMovies.hasObservers()) {
            moviesViewModel.topRatedMovies.removeObservers(this)
        }
        if (moviesViewModel.upcomingMovies.hasObservers()) {
            moviesViewModel.upcomingMovies.removeObservers(this)
        }
    }

    companion object {
        const val MOVIE_ID_KEY = "id"
        const val TITLE_KEY = "title"
        const val IMAGE_KEY = "image"
    }
}

enum class Category {
    NowPlaying,
    Popular,
    TopRated,
    Upcoming
}
