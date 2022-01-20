package com.jmdev.challengemovies.ui.activities


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import challengemovies.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher

import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MyTestUi {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun myTestUi() {
        val frameLayout = onView(
            allOf(
                withId(R.id.discoveryMoviesFragment), withContentDescription("Home"),
                withParent(withParent(withId(R.id.bottom_nav_bar))),
                isDisplayed()
            )
        )
        frameLayout.check(matches(isDisplayed()))

        val frameLayout2 = onView(
            allOf(
                withId(R.id.discoveryMoviesFragment), withContentDescription("Home"),
                withParent(withParent(withId(R.id.bottom_nav_bar))),
                isDisplayed()
            )
        )
        frameLayout2.check(matches(isDisplayed()))

        val frameLayout3 = onView(
            allOf(
                withId(R.id.moviesFragment), withContentDescription("Top-Popular"),
                withParent(withParent(withId(R.id.bottom_nav_bar))),
                isDisplayed()
            )
        )
        frameLayout3.check(matches(isDisplayed()))

        val frameLayout4 = onView(
            allOf(
                withId(R.id.moviesFragment), withContentDescription("Top-Popular"),
                withParent(withParent(withId(R.id.bottom_nav_bar))),
                isDisplayed()
            )
        )
        frameLayout4.check(matches(isDisplayed()))

        val recyclerView = onView(
            allOf(
                withId(R.id.rv_discovery_movies),
                childAtPosition(
                    withId(R.id.refresh_movies),
                    0
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val recyclerView2 = onView(
            allOf(
                withId(R.id.rv_detailmovie_trailers),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        recyclerView2.check(matches(isDisplayed()))

        val recyclerView3 = onView(
            allOf(
                withId(R.id.rv_detailmovie_trailers),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        recyclerView3.check(matches(isDisplayed()))

        val recyclerView4 = onView(
            allOf(
                withId(R.id.rv_detailmovie_trailers),
                childAtPosition(
                    withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                    6
                )
            )
        )
        recyclerView4.perform(actionOnItemAtPosition<ViewHolder>(1, click()))

        val appCompatImageView = onView(
            allOf(
                withId(R.id.videoplayer_back),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragmentContainerView),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.iv_detailmovie_isfavorite),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.appbar_detailmovie),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
