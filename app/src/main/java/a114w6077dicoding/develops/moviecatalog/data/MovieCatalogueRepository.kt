package a114w6077dicoding.develops.moviecatalog.data

import a114w6077dicoding.develops.moviecatalog.data.source.MovieCatalogueDataSource
import a114w6077dicoding.develops.moviecatalog.data.source.remote.RemoteDataSource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MovieCatalogueRepository private constructor(private val remoteDataSource: RemoteDataSource) : MovieCatalogueDataSource{

    override fun getMovies(): LiveData<List<MovieResults>> {
        val movieResults = MutableLiveData<List<MovieResults>>()
        remoteDataSource.getMovies(object : RemoteDataSource.LoadMoviesCallback{
            override fun onMoviesLoaded(response: List<MovieResults>?) {
                val movieList = ArrayList<MovieResults>()
                if (response != null){
                    for (movieResponse in response){
                        val movie = MovieResults(
                            id = movieResponse.id,
                            releaseDate = movieResponse.releaseDate,
                            title = movieResponse.title,
                            poster_path = movieResponse.poster_path)
                        movieList.add(movie)
                    }
                    movieResults.postValue(movieList)
                }
            }
        })
        return movieResults
    }

    override fun getDetailMovie(movieId: Int): LiveData<MovieDetail> {
        val movieDetail = MutableLiveData<MovieDetail>()
        remoteDataSource.getDetailMovie(object : RemoteDataSource.LoadDetailMovieCallback{
            override fun onDetailMovieLoaded(response: MovieDetail?) {
                if (response != null){
                    val movie = MovieDetail(
                        id = response.id,
                        originalTitle = response.originalTitle,
                        releaseDate = response.releaseDate,
                        runtime = response.runtime,
                        poster_path = response.poster_path,
                        overview = response.overview,
                        vote_average = response.vote_average
                    )
                    movieDetail.postValue(movie)
                }
            }
        }, movieId)
        return movieDetail
    }

    override fun getTvShows(): LiveData<List<TvResults>> {
        val listTv = MutableLiveData<List<TvResults>>()
        remoteDataSource.getTvShows(object: RemoteDataSource.LoadTvShowsCallback {
            override fun onTvShowLoaded(response: List<TvResults>?) {
                val tvData = ArrayList<TvResults>()
                if (response != null){
                    for (tvResponse in response){
                        val tv = TvResults(
                            id = tvResponse.id,
                            original_name = tvResponse.original_name,
                            first_air_date = tvResponse.first_air_date,
                            poster_path = tvResponse.poster_path,
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

    companion object{
        @Volatile
        private var instance: MovieCatalogueRepository? = null

        fun getInstance(remoteData: RemoteDataSource) : MovieCatalogueRepository = instance ?: synchronized(this){
            instance ?: MovieCatalogueRepository(remoteData).apply {
                instance = this
            }
        }
    }
}