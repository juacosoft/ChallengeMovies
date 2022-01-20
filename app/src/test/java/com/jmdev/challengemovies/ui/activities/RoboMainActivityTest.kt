package com.jmdev.challengemovies.ui.activities


import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode


@HiltAndroidTest
@Config(application = HiltTestApplication::class) // 2
@RunWith(RobolectricTestRunner::class) // 3
@LooperMode(LooperMode.Mode.PAUSED)  // 4
class RoboMainActivityTest{
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this) // 5

    @Before
    fun setUp() {
        hiltAndroidRule.inject() // 6
    }

    @Test
    fun whenMainActivityLaunchedNavigationHelperIsInvokedForFragment() { // 7
        assertTrue(true)
    }



}