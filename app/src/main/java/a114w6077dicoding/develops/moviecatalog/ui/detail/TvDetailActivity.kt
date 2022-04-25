package a114w6077dicoding.develops.moviecatalog.ui.detail

import a114w6077dicoding.develops.moviecatalog.R
import a114w6077dicoding.develops.moviecatalog.ViewModelFactory
import a114w6077dicoding.develops.moviecatalog.databinding.ActivityTvDetailBinding

import a114w6077dicoding.develops.moviecatalog.ui.tv.TvShowViewModel
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


class TvDetailActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTvDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataId = intent.getIntExtra(EXTRA_DATA_ID, 0)
        supportActionBar?.title = intent.getStringExtra(EXTRA_TITLE)

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]
        binding.progressBar.visibility = View.VISIBLE

        viewModel.getTvDetail(dataId).observe(this,{
                tv -> binding.apply {
                    progressBar.visibility = View.GONE
                    tvItemTitle.text = tv?.original_name
                    tvNumberOfEpisode.text = "${tv.number_of_episode.toString()} episodes"
                    tvItemScore.text = tv?.vote_average.toString()
                    tvItemReleaseDate.text = tv.first_air_date
                    tvItemOverview.text = tv?.overview
                    Glide.with(this@TvDetailActivity)
                        .load(Constanta.IMAGE + tv?.poster_path)
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

        R.id.action_share -> {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            val shareBody = "This Film ${intent.getStringExtra(EXTRA_TITLE)} is Awesome and Great!!"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Movie Catalogue")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(sharingIntent, "Share With"))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    companion object {
        var EXTRA_DATA_ID = "dataId"
        var EXTRA_TITLE = "extra_title"
    }
}