package a114w6077dicoding.develops.moviecatalog.ui.home

import a114w6077dicoding.develops.moviecatalog.R
import a114w6077dicoding.develops.moviecatalog.utils.DataDummy
import a114w6077dicoding.develops.moviecatalog.utils.EspressoIdlingResource
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    private val movieDummy = DataDummy.getDummyMovies()
    private val tvShowDummy = DataDummy.getDummyTv()


    @Before
    fun setUp(){
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.IdlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.IdlingResource)
    }

    @Test
    fun loadMovie(){
        onView(withId(R.id.rvMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.rvMovie)).perform(movieDummy?.let {
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(it.size)
        })
    }

    @Test
    fun loadDetailMovie(){
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_ItemTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_ItemReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_ItemScore)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_ItemOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_itemDuration)).check(matches(isDisplayed()))
        onView(withId(R.id.action_share)).check(matches(isDisplayed()))

    }

    @Test
    fun loadTVShow(){
        onView(withId(R.id.view_pager)).perform(swipeLeft())
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rvTvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rvTvShow)).perform(tvShowDummy?.let {
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(it.size)
        })
    }

    @Test
    fun loadDetailTVShow(){
        onView(withText("TV SHOW")).perform(click())
        onView(withId(R.id.rvTvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.image_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_ItemTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_ItemReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_ItemScore)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_ItemOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.tvNumberOfEpisode)).check(matches(isDisplayed()))
        onView(withId(R.id.action_share)).check(matches(isDisplayed()))
    }


}
