package a114w6077dicoding.develops.moviecatalog.ui.detail

import a114w6077dicoding.develops.moviecatalog.R
import a114w6077dicoding.develops.moviecatalog.ViewModelFactory
import a114w6077dicoding.develops.moviecatalog.databinding.ActivityMovieDetailBinding
import a114w6077dicoding.develops.moviecatalog.ui.movie.MovieViewModel
import a114w6077dicoding.develops.moviecatalog.utils.Constanta
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class MovieDetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataId = intent.getIntExtra(EXTRA_DATA_ID, 0)
        supportActionBar?.title = intent.getStringExtra(EXTRA_TITLE)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        binding.progressBar.visibility = View.VISIBLE

        viewModel.getMovieDetail(dataId).observe(this,{
                movie -> binding.apply {
                    progressBar.visibility = View.GONE
                    tvItemTitle.text = movie?.originalTitle
                    tvItemReleaseDate.text = movie?.releaseDate
                    tvItemDuration.text = "${movie.runtime} minutes"
                    tvItemScore.text = movie?.vote_average.toString()
                    tvItemOverview.text = movie?.overview
                    Glide.with(this@MovieDetailActivity)
                        .load(Constanta.IMAGE + movie?.poster_path)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                        .error(R.drawable.ic_error)
                        .into(imagePoster)
                }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_share ->{
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "This Film ${intent.getStringArrayExtra(EXTRA_TITLE)} is awesome and great!!"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Movie Catalogue")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Sharing with"))
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    companion object{
        var EXTRA_DATA_ID = "dataId"
        var EXTRA_TITLE = "extra_title"
    }
}