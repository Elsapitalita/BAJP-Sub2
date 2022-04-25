@file:Suppress("unused")

package a114w6077dicoding.develops.moviecatalog.utils

import a114w6077dicoding.develops.moviecatalog.data.Movie
import a114w6077dicoding.develops.moviecatalog.data.MovieDetail
import a114w6077dicoding.develops.moviecatalog.data.Tv
import a114w6077dicoding.develops.moviecatalog.data.TvDetail
import android.util.Log
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

object DataDummy {

    private var data = Gson()
    fun getDummyMovies() = data.fromJson(loadJSON("movie.json"), Movie::class.java).results
    fun getDummyMoviesDetail(): MovieDetail = data.fromJson(loadJSON("movie_detail.json"), MovieDetail::class.java)
    fun getDummyTv() = data.fromJson(loadJSON("tv.json"), Tv::class.java).results
    fun getDummyTvDetail(): TvDetail = data.fromJson(loadJSON("tv_detail.json"), TvDetail::class.java)
    private fun loadJSON(filename: String): String?{
        var json: String? = null
        try {
            val input : InputStream = this.javaClass.classLoader!!.getResourceAsStream(filename)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            json = String(buffer, charset("UTF-8"))
        }catch (ex: IOException){
            Log.e("Dummy", ex.toString())
        }
        return json
    }
}