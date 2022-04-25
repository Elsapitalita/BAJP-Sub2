package a114w6077dicoding.develops.moviecatalog.ui.tv

import a114w6077dicoding.develops.moviecatalog.data.MovieCatalogueRepository
import a114w6077dicoding.develops.moviecatalog.data.TvDetail
import a114w6077dicoding.develops.moviecatalog.data.TvResults
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class TvShowViewModel(private val movieCatalogueRepository: MovieCatalogueRepository) : ViewModel() {
    fun getTvShow() : LiveData<List<TvResults>> = movieCatalogueRepository.getTvShows()

    fun getTvDetail(TvId: Int): LiveData<TvDetail> = movieCatalogueRepository.getDetailTvShow(TvId)
}