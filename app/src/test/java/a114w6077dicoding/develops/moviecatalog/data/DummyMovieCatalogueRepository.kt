package a114w6077dicoding.develops.moviecatalog.data

import a114w6077dicoding.develops.moviecatalog.data.source.MovieCatalogueDataSource
import a114w6077dicoding.develops.moviecatalog.data.source.remote.RemoteDataSource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DummyMovieCatalogueRepository(private val remoteDataSource: RemoteDataSource) : MovieCatalogueDataSource {
    override fun getMovies(): LiveData<List<MovieResults>> {
        val movieResults = MutableLiveData<List<MovieResults>>()
        remoteDataSource.getMovies(object : RemoteDataSource.LoadMoviesCallback{
            override fun onMoviesLoaded(movieResponse: List<MovieResults>?){
                val listMovie = ArrayList<MovieResults>()
                if (movieResponse != null){
                    for (response in movieResponse){
                        val movie = MovieResults(
                            id = response.id,
                            releaseDate = response.releaseDate,
                            title = response.title,
                            overview = response.overview,
                            poster_path = response.poster_path
                        )
                        listMovie.add(movie)
                    }
                    movieResults.postValue(listMovie)
                }
            }
        })
        return movieResults
    }

    override fun getDetailMovie(movieId: Int): LiveData<MovieDetail> {
        val detailMovie = MutableLiveData<MovieDetail>()
        remoteDataSource.getDetailMovie(object : RemoteDataSource.LoadDetailMovieCallback {
            override fun onDetailMovieLoaded(response: MovieDetail?) {
                if (response != null) {
                    val movie = MovieDetail(
                        id = response.id,
                        originalTitle = response.originalTitle,
                        releaseDate = response.releaseDate,
                        runtime = response.runtime,
                        poster_path = response.poster_path,
                        overview = response.overview,
                        vote_average = response.vote_average,
                    )
                    detailMovie.postValue(movie)
                }
            }

        }, movieId)
        return detailMovie
    }

    override fun getTvShows(): LiveData<List<TvResults>> {
        val listTv = MutableLiveData<List<TvResults>>()
        remoteDataSource.getTvShows(object : RemoteDataSource.LoadTvShowsCallback {
            override fun onTvShowLoaded(response: List<TvResults>?) {
                val tvData = ArrayList<TvResults>()
                if (response != null) {
                    for (tvResponse in response) {
                        val tv = TvResults(
                            id = tvResponse.id,
                            poster_path = tvResponse.poster_path,
                            original_name = tvResponse.original_name,
                            overview = tvResponse.overview,
                            first_air_date = tvResponse.first_air_date,
                        )
                        tvData.add(tv)
                    }
                    listTv.postValue(tvData)
                }
            }

        })
        return listTv
    }

    override fun getDetailTvShow(tvShowId: Int): LiveData<TvDetail> {
        val detailTv = MutableLiveData<TvDetail>()
        remoteDataSource.getTvDetail(object : RemoteDataSource.LoadDetailTvShowCallback {
            override fun onDetailTvShowLoaded(response: TvDetail?) {
                if (response != null) {
                    val tv = TvDetail(
                        id = response.id,
                        vote_average = response.vote_average,
                        original_name = response.original_name,
                        poster_path = response.poster_path,
                        overview = response.overview,
                        first_air_date = response.first_air_date,
                        number_of_episode = response.number_of_episode
                    )
                    detailTv.postValue(tv)
                }
            }

        }, tvShowId)
        return detailTv
    }

}