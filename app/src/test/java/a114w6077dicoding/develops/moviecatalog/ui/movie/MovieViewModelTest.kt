package a114w6077dicoding.develops.moviecatalog.ui.movie


import a114w6077dicoding.develops.moviecatalog.data.MovieCatalogueRepository
import a114w6077dicoding.develops.moviecatalog.data.MovieDetail
import a114w6077dicoding.develops.moviecatalog.data.MovieResults
import a114w6077dicoding.develops.moviecatalog.utils.DataDummy
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest{

    private lateinit var viewModel: MovieViewModel
    private val movieDummy = DataDummy.getDummyMovies()?.get(0)
    private val detailMovieDummy = DataDummy.getDummyMoviesDetail()
    private val movieId = movieDummy?.id as Int
    private val dummyId = 1

    @Mock
    private var movieCatalogueRepository = mock(MovieCatalogueRepository::class.java)

    @Mock
    private lateinit var observer: Observer<List<MovieResults>>

    @Mock
    private lateinit var observerDetail: Observer<MovieDetail>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        viewModel = MovieViewModel(movieCatalogueRepository)
    }

    @Test
    fun getMovie(){
        val movieDataDummy = DataDummy.getDummyMovies()
        val movie = MutableLiveData<List<MovieResults>>()
        movie.value = movieDataDummy

        `when`(movieCatalogueRepository.getMovies()).thenReturn(movie)
        val movieData = viewModel.getMovie().value
        verify(movieCatalogueRepository).getMovies()
        assertNotNull(movieData)
        assertEquals(movieDataDummy?.size, movieData?.size)

        viewModel.getMovie().observeForever(observer)
        verify(observer).onChanged(movieDataDummy)
    }

    @Test
    fun nullMovie() {
        `when`(movieCatalogueRepository.getMovies()).thenReturn(null)
        val exception = assertThrows(NullPointerException::class.java) {
            viewModel.getMovie().value
        }
        assertEquals(null, exception.message)
    }

    @Test
    fun checkIndexOfBound(){
        val movieDataDummy = DataDummy.getDummyMovies()
        val movie = MutableLiveData<List<MovieResults>>()
        movie.value = movieDataDummy

        `when`(movieCatalogueRepository.getMovies()).thenReturn(movie)
        val movieData = viewModel.getMovie().value
        verify(movieCatalogueRepository).getMovies()
        assertNotNull(movieData)

        val exception = assertThrows(IndexOutOfBoundsException::class.java) {
            movieData?.get(21)
        }
        assertEquals("Index 21 out of bounds for length 20", exception.message)

    }

    @Test
    fun getDetailMovie(){
        val detailMovies = MutableLiveData<MovieDetail>()
        detailMovies.value = detailMovieDummy

        `when`(movieCatalogueRepository.getDetailMovie(movieId)).thenReturn(detailMovies)
        val movieData = viewModel.getMovieDetail(movieId).value
        verify(movieCatalogueRepository).getDetailMovie(movieId)
        assertNotNull(viewModel.getMovieDetail(movieId))
        assertEquals(detailMovieDummy.id, movieData?.id)
        assertEquals(detailMovieDummy.poster_path, movieData?.poster_path)
        assertEquals(detailMovieDummy.originalTitle, movieData?.originalTitle)
        assertEquals(detailMovieDummy.releaseDate, movieData?.releaseDate)
        assertEquals(detailMovieDummy.overview, movieData?.overview)
        assertEquals(detailMovieDummy.vote_average, movieData?.vote_average)
        assertEquals(detailMovieDummy.runtime, movieData?.runtime)

        viewModel.getMovieDetail(movieId).observeForever(observerDetail)
        com.nhaarman.mockitokotlin2.verify(observerDetail).onChanged(detailMovieDummy)
    }

    @Test
    fun nullDataLoadDataDetail() {
        val exception = assertThrows(java.lang.NullPointerException::class.java) {
            viewModel.getMovieDetail(dummyId).value
        }
        assertEquals(null, exception.message)
    }
}