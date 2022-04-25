package a114w6077dicoding.develops.moviecatalog.ui.home

import a114w6077dicoding.develops.moviecatalog.R
import a114w6077dicoding.develops.moviecatalog.ui.movie.MovieFragment
import a114w6077dicoding.develops.moviecatalog.ui.tv.TvShowFragment
import androidx.fragment.app.Fragment
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class ActivityTest{

    private lateinit var activity: MainActivity
    private var movieFragment : Fragment? = null
    private var tvShowFragment : Fragment? = null

    @Before
    fun setUp(){
        activity = Robolectric.buildActivity(MainActivity::class.java)
            .create().resume().get()

        movieFragment = MovieFragment()
        tvShowFragment = TvShowFragment()
    }

    @After
    @Throws(Exception::class)
    fun tearDown(){
        movieFragment = null
        tvShowFragment = null
    }

    @Test
    @Throws(Exception::class)
    fun shouldHaveCorrectAppName(){
        val appName = activity.resources.getString(R.string.app_name)
        assertEquals(appName, "Movie Catalogue")
    }

    @Test
    @Throws(Exception::class)
    fun checkActivityNotNull(){
        assertNotNull(activity)
    }

    @Test
    @Throws(Exception::class)
    fun checkMovieFragmentNotNull(){
        assertNotNull(movieFragment)
    }

    @Test
    @Throws(Exception::class)
    fun checkTvShowFragmentNotNull(){
        assertNotNull(tvShowFragment)
    }

}