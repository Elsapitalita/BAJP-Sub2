package a114w6077dicoding.develops.moviecatalog.adapter

import a114w6077dicoding.develops.moviecatalog.R
import a114w6077dicoding.develops.moviecatalog.data.MovieResults
import a114w6077dicoding.develops.moviecatalog.databinding.ItemListBinding
import a114w6077dicoding.develops.moviecatalog.ui.detail.MovieDetailActivity
import a114w6077dicoding.develops.moviecatalog.utils.Constanta
import a114w6077dicoding.develops.moviecatalog.utils.MovieDiffCallback
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MovieCatalogueAdapter : RecyclerView.Adapter<MovieCatalogueAdapter.MovieViewHolder>() {

    private var listMovie = ArrayList<MovieResults>()

    fun setMovie(listMovieCatalogue: List<MovieResults>){
        val diffCallback = MovieDiffCallback(this.listMovie, listMovieCatalogue)
        val diffResults = DiffUtil.calculateDiff(diffCallback)
        this.listMovie.clear()
        this.listMovie.addAll(listMovieCatalogue)
        diffResults.dispatchUpdatesTo(this)
    }

    class MovieViewHolder(var binding: ItemListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.binding.apply {
            tvItemTitle.text = movie.title
            tvItemReleasedDate.text = movie.releaseDate
            Glide.with(holder.itemView.context)
                .load(Constanta.IMAGE + movie.poster_path)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(imagePoster)
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.EXTRA_DATA_ID, movie.id)
            intent.putExtra(MovieDetailActivity.EXTRA_TITLE, movie.title)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listMovie.size
}