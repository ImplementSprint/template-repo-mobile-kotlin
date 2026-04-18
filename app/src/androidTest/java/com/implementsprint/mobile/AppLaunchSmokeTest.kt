package com.implementsprint.mobile

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppLaunchSmokeTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun appLaunch_displaysReadinessScreen() {
        activityScenarioRule.scenario.onActivity { activity ->
            assertEquals(android.view.View.VISIBLE, activity.findViewById<android.widget.TextView>(R.id.screenTitleText).visibility)
            assertEquals(android.view.View.VISIBLE, activity.findViewById<android.widget.Button>(R.id.validateButton).visibility)
        }
    }
}
