package org.themoviedb.ui.home

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.item_movie.view.*
import org.themoviedb.R
import org.themoviedb.data.ConfigurationRepository
import org.themoviedb.data.model.MoviesRequest

class MovieListAdapter(
    private val values: MoviesRequest,
    private val listener: HomeFragment.OnMediaListInteractionListener?
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values.results[position]
        holder.mediaImageView.contentDescription = item.title

        holder.mediaImageView.layoutParams.width =
            holder.mView.context.resources.getDimensionPixelSize(R.dimen.portrait_image_width)
        holder.mediaImageView.layoutParams.height =
            holder.mView.context.resources.getDimensionPixelSize(R.dimen.portrait_image_height)

        Glide.with(holder.mView.context)
            .load(ConfigurationRepository.imageURL185 + item.poster_path)
            .error(R.drawable.ic_image_24)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?, target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(holder.mediaImageView)

        with(holder.mView) {
            setOnClickListener {
                listener?.onMediaItemClick(item, holder.mediaImageView)
            }
        }
    }

    override fun getItemCount(): Int = values.results.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val mediaImageView: ImageView = mView.mediaImage
        val progressBar: ProgressBar = mView.progressBar
    }
}