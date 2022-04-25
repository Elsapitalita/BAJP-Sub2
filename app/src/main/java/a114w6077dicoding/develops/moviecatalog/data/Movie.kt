package a114w6077dicoding.develops.moviecatalog.data

import com.google.gson.annotations.SerializedName

data class Movie(
    @field:SerializedName("results")
    val results: List<MovieResults>? = null
)

data class MovieResults(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("poster_path")
    val poster_path: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("release_date")
    val releaseDate: String? = null,
)
