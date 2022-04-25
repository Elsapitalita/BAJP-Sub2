package a114w6077dicoding.develops.moviecatalog.data.source.remote

import a114w6077dicoding.develops.moviecatalog.data.*
import a114w6077dicoding.develops.moviecatalog.network.ApiConfig
import a114w6077dicoding.develops.moviecatalog.utils.Constanta
import a114w6077dicoding.develops.moviecatalog.utils.EspressoIdlingResource
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    fun getMovies(callback: LoadMoviesCallback){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getMovies()
            .enqueue(object : Callback<Movie> {
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    if (response.isSuccessful){
                        callback.onMoviesLoaded(response.body()?.results)
                        Log.d(Constanta.SUCCESS, response.code().toString())
                        EspressoIdlingResource.decrement()
                    }
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.d(Constanta.FAILED, t.message.toString())
                    EspressoIdlingResource.decrement()
                }
            })

    }

    fun getDetailMovie(callback: LoadDetailMovieCallback, movieId: Int){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService()
            .getDetailMovie(movieId)
            .enqueue(object : Callback<MovieDetail> {
                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    callback.onDetailMovieLoaded(response.body())
                    Log.d(Constanta.SUCCESS, response.code().toString())
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    Log.d(Constanta.FAILED, t.message.toString())
                    EspressoIdlingResource.decrement()
                }
            } )
    }

    fun getTvShows(callback: LoadTvShowsCallback){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getTvShowData()
            .enqueue(object : Callback<Tv>{
                override fun onResponse(call: Call<Tv>, response: Response<Tv>) {
                    callback.onTvShowLoaded(response.body()?.results)
                    Log.d(Constanta.SUCCESS, response.code().toString())
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<Tv>, t: Throwable) {
                    Log.d(Constanta.FAILED, t.message.toString())
                    EspressoIdlingResource.decrement()
                }

            })
    }

    fun getTvDetail(callback: LoadDetailTvShowCallback, tvId: Int){
        EspressoIdlingResource.increment()
        ApiConfig.getApiService().getDetailTv(tvId)
            .enqueue(object : Callback<TvDetail>{
                override fun onResponse(call: Call<TvDetail>, response: Response<TvDetail>) {
                    callback.onDetailTvShowLoaded(response.body())
                    Log.d(Constanta.SUCCESS, response.code().toString())
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvDetail>, t: Throwable) {
                    Log.d(Constanta.FAILED, t.message.toString())
                    EspressoIdlingResource.decrement()
                }
            })
    }

    interface LoadMoviesCallback {
        fun onMoviesLoaded(response: List<MovieResults>?)
    }

    interface LoadDetailMovieCallback {
        fun onDetailMovieLoaded(response: MovieDetail?)
    }

    interface LoadTvShowsCallback{
        fun onTvShowLoaded(response: List<TvResults>?)
    }

    interface LoadDetailTvShowCallback{
        fun onDetailTvShowLoaded(response: TvDetail?)
    }

    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource = instance ?: synchronized(this){
            instance ?: RemoteDataSource()
        }
    }
}