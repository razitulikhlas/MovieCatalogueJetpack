package com.razit.moviecatalogue

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*

import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.razit.moviecatalogue.utils.DataFilm
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test



class HomeActivityTest {
    private val dummyMovie= DataFilm.generateDummyMovies()

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun loadCourses() {
        onView(allOf(isDisplayed(), withId(R.id.rcvFilm))).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovie.size))
        onView(allOf(isDisplayed(), withId(R.id.rcvFilm))).check(matches(isDisplayed()))
    }

    @Test
    fun loadDetailMovies() {
        onView(allOf(isDisplayed(),withId(R.id.rcvFilm))).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))
        onView(allOf(isDisplayed(),withId(R.id.tvTitle))).check(matches(isDisplayed()))
        onView(allOf(isDisplayed(),withId(R.id.tvTitle))).check(matches(withText(dummyMovie[0].title)))
        onView(allOf(isDisplayed(),withId(R.id.tvDate))).check(matches(isDisplayed()))
        onView(allOf(isDisplayed(),withId(R.id.tvDate))).check(matches(withText(dummyMovie[0].release)))
        onView(allOf(isDisplayed(),withId(R.id.tvDirector))).check(matches(isDisplayed()))
        onView(allOf(isDisplayed(),withId(R.id.tvDirector))).check(matches(withText(dummyMovie[0].director)))
        onView(allOf(isDisplayed(),withId(R.id.tvDesc))).check(matches(isDisplayed()))
        onView(allOf(isDisplayed(),withId(R.id.tvDesc))).check(matches(withText(dummyMovie[0].description)))
        onView(allOf(isDisplayed(),withId(R.id.tvGenre))).check(matches(isDisplayed()))
        onView(allOf(isDisplayed(),withId(R.id.tvGenre))).check(matches(withText(dummyMovie[0].genre)))
    }
}