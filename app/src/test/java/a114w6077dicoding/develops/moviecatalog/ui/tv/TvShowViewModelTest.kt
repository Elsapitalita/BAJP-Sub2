package a114w6077dicoding.develops.moviecatalog.ui.tv

import a114w6077dicoding.develops.moviecatalog.data.MovieCatalogueRepository
import a114w6077dicoding.develops.moviecatalog.data.TvDetail
import a114w6077dicoding.develops.moviecatalog.data.TvResults
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
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel
    private val tvDummy = DataDummy.getDummyTv()?.get(0)
    private val detailTvDummy = DataDummy.getDummyTvDetail()
    private val tvId = tvDummy?.id as Int
    private val dummyId = 1

    @Mock
    private var movieCatalogueRepository = Mockito.mock(MovieCatalogueRepository::class.java)

    @Mock
    private lateinit var observer: Observer<List<TvResults>>

    @Mock
    private lateinit var observerDetail: Observer<TvDetail>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        viewModel = TvShowViewModel(movieCatalogueRepository)
    }

    @Test
    fun getTvShow(){
        val tvDataDummy = DataDummy.getDummyTv()
        val tvShow = MutableLiveData<List<TvResults>>()
        tvShow.value = tvDataDummy

        `when`(movieCatalogueRepository.getTvShows()).thenReturn(tvShow)
        val tvData = viewModel.getTvShow().value
        verify(movieCatalogueRepository).getTvShows()
        assertNotNull(tvData)
        assertEquals(tvDataDummy?.size, tvData?.size)

        viewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(tvDataDummy)
    }

    @Test
    fun nullTvShow() {
        `when`(movieCatalogueRepository.getTvShows()).thenReturn(null)
        val exception = assertThrows(NullPointerException::class.java) {
            viewModel.getTvShow().value
        }
        assertEquals(null, exception.message)
    }

    @Test
    fun checkIndexOfBound(){
        val tvDataDummy = DataDummy.getDummyTv()
        val tvShow = MutableLiveData<List<TvResults>>()
        tvShow.value = tvDataDummy

        `when`(movieCatalogueRepository.getTvShows()).thenReturn(tvShow)
        val tvShowData = viewModel.getTvShow().value
        verify(movieCatalogueRepository).getTvShows()
        assertNotNull(tvShowData)

        val exception = assertThrows(IndexOutOfBoundsException::class.java) {
            tvShowData?.get(21)
        }
        assertEquals("Index 21 out of bounds for length 20", exception.message)

    }

    @Test
    fun getDetailTvShow(){
        val detailTvShow = MutableLiveData<TvDetail>()
        detailTvShow.value = detailTvDummy

        `when`(movieCatalogueRepository.getDetailTvShow(tvId)).thenReturn(detailTvShow)
        val tvData = viewModel.getTvDetail(tvId).value
        verify(movieCatalogueRepository).getDetailTvShow(tvId)
        assertNotNull(viewModel.getTvDetail(tvId))
        assertEquals(detailTvDummy.id, tvData?.id)
        assertEquals(detailTvDummy.poster_path, tvData?.poster_path)
        assertEquals(detailTvDummy.original_name, tvData?.original_name)
        assertEquals(detailTvDummy.first_air_date, tvData?.first_air_date)
        assertEquals(detailTvDummy.overview, tvData?.overview)
        assertEquals(detailTvDummy.vote_average, tvData?.vote_average)
        assertEquals(detailTvDummy.number_of_episode, tvData?.number_of_episode)

        viewModel.getTvDetail(tvId).observeForever(observerDetail)
        verify(observerDetail).onChanged(detailTvDummy)
    }

    @Test
    fun nullDataLoadDataDetail() {
        val exception = assertThrows(java.lang.NullPointerException::class.java) {
            viewModel.getTvDetail(dummyId).value
        }
        assertEquals(null, exception.message)
    }

}