package a114w6077dicoding.develops.moviecatalog.data.source

import a114w6077dicoding.develops.moviecatalog.data.MovieDetail
import a114w6077dicoding.develops.moviecatalog.data.MovieResults
import a114w6077dicoding.develops.moviecatalog.data.TvDetail
import a114w6077dicoding.develops.moviecatalog.data.TvResults
import androidx.lifecycle.LiveData

interface MovieCatalogueDataSource {
    fun getMovies() : LiveData<List<MovieResults>>
    fun getDetailMovie(movieId : Int): LiveData<MovieDetail>
    fun getTvShows() : LiveData<List<TvResults>>
    fun getDetailTvShow(tvShowId: Int) : LiveData<TvDetail>
}