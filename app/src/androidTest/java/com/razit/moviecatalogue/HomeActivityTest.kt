package com.razit.moviecatalogue

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.razit.moviecatalogue.utils.EspressoIdlingResource
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance()
            .register(EspressoIdlingResource.getEspressoIdlingResourceForMainActivity())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance()
            .unregister(EspressoIdlingResource.getEspressoIdlingResourceForMainActivity())
    }

    @Test
    fun loadMovies() {
        onView(allOf(isDisplayed(), withId(R.id.rcvFilm))).check(matches(isDisplayed()))
        onView(allOf(isDisplayed(), withId(R.id.rcvFilm))).perform()
    }

    @Test
    fun loadDetailMovies() {
        onView(allOf(isDisplayed(),withId(R.id.rcvFilm))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
    }

    @Test
    fun loadTvShow() {
        onView(withText(R.string.tvShow)).perform(ViewActions.click())
        onView(allOf(isDisplayed(), withId(R.id.rcvFilm))).check(matches(isDisplayed()))
        onView(allOf(isDisplayed(), withId(R.id.rcvFilm))).perform()
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText(R.string.tvShow)).perform(ViewActions.click())
        onView(allOf(isDisplayed(),withId(R.id.rcvFilm))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
    }
}