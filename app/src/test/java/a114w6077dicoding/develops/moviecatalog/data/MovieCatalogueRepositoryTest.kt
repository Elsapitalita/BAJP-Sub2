package a114w6077dicoding.develops.moviecatalog.data

import a114w6077dicoding.develops.moviecatalog.data.source.remote.RemoteDataSource
import a114w6077dicoding.develops.moviecatalog.utils.DataDummy
import a114w6077dicoding.develops.moviecatalog.utils.LiveDataTestUtil
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MovieCatalogueRepositoryTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val movieTvRepository = DummyMovieCatalogueRepository(remote)

    private val movieDummyData = DataDummy.getDummyMovies()
    private val movieId = movieDummyData?.get(0)?.id as Int

    private val tvDummyData = DataDummy.getDummyTv()
    private val tvId = tvDummyData?.get(0)?.id as Int

    private val dummyMovieDetail = DataDummy.getDummyMoviesDetail()
    private val dummyTvDetail = DataDummy.getDummyTvDetail()

    @Test
    fun getListMovie(){
        doAnswer {
            (it.arguments[0] as RemoteDataSource.LoadMoviesCallback).onMoviesLoaded(movieDummyData)
            null
        }.`when`(remote).getMovies(any())

        val movieEntity = LiveDataTestUtil.getValue(movieTvRepository.getMovies())
        verify(remote).getMovies(any())
        Assert.assertNotNull(movieEntity)
        assertEquals(movieDummyData?.size, movieEntity.size)
    }

    @Test
    fun getMovieDetails(){
        doAnswer { (it.arguments[0] as RemoteDataSource.LoadDetailMovieCallback)
            .onDetailMovieLoaded(dummyMovieDetail)
            null
        }.`when`(remote).getDetailMovie(any(), eq(movieId))
        val movieDetails = LiveDataTestUtil.getValue(movieTvRepository.getDetailMovie(movieId))
        verify(remote).getDetailMovie(any(), eq(movieId))
        Assert.assertNotNull(movieDetails)
        assertEquals(dummyMovieDetail.id, movieDetails.id)
        assertEquals(dummyMovieDetail.originalTitle, movieDetails.originalTitle)
        assertEquals(dummyMovieDetail.poster_path, movieDetails.poster_path)
        assertEquals(dummyMovieDetail.overview, movieDetails.overview)
        assertEquals(dummyMovieDetail.releaseDate, movieDetails.releaseDate)
        assertEquals(dummyMovieDetail.vote_average, movieDetails.vote_average)
    }

    @Test
    fun getTvShowList(){
        doAnswer {
            (it.arguments[0] as RemoteDataSource.LoadTvShowsCallback)
                .onTvShowLoaded(tvDummyData)
            null
        }.`when`(remote).getTvShows(any())
        val tvEntities = LiveDataTestUtil.getValue(movieTvRepository.getTvShows())
        verify(remote).getTvShows(any())
        Assert.assertNotNull(tvEntities)
        assertEquals(tvDummyData?.size, tvEntities.size)
    }

    @Test
    fun getTvDetails(){
        doAnswer { (it.arguments[0] as RemoteDataSource.LoadDetailTvShowCallback)
            .onDetailTvShowLoaded(dummyTvDetail)
            null
        }.`when`(remote).getTvDetail(any(), eq(tvId))
        val tvDetails = LiveDataTestUtil.getValue(movieTvRepository.getDetailTvShow(tvId))
        verify(remote).getTvDetail(any(), eq(tvId))
        Assert.assertNotNull(tvDetails)
        assertEquals(dummyTvDetail.id, tvDetails.id)
        assertEquals(dummyTvDetail.original_name, tvDetails.original_name)
        assertEquals(dummyTvDetail.overview, tvDetails.overview)
        assertEquals(dummyTvDetail.poster_path, tvDetails.poster_path)
        assertEquals(dummyTvDetail.first_air_date, tvDetails.first_air_date)
    }
}