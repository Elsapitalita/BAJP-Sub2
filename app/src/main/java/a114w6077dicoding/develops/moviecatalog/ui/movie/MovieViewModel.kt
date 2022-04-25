package a114w6077dicoding.develops.moviecatalog.ui.movie

import a114w6077dicoding.develops.moviecatalog.data.MovieCatalogueRepository
import a114w6077dicoding.develops.moviecatalog.data.MovieDetail
import a114w6077dicoding.develops.moviecatalog.data.MovieResults
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MovieViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) : ViewModel() {
    fun getMovie() : LiveData<List<MovieResults>> = movieCatalogueRepository.getMovies()

    fun getMovieDetail(movieId: Int): LiveData<MovieDetail> = movieCatalogueRepository.getDetailMovie(movieId)
}