package com.implementsprint.mobile

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun validateReadiness_showsReadyStateForUatHttpsEndpoint() {
        activityScenarioRule.scenario.onActivity { activity ->
            val environmentInput = activity.findViewById<android.widget.EditText>(R.id.environmentInput)
            val apiBaseUrlInput = activity.findViewById<android.widget.EditText>(R.id.apiBaseUrlInput)
            val validateButton = activity.findViewById<android.widget.Button>(R.id.validateButton)
            val readinessStatusText = activity.findViewById<android.widget.TextView>(R.id.readinessStatusText)

            environmentInput.setText("uat")
            apiBaseUrlInput.setText("https://api.example.com")
            validateButton.performClick()

            assertEquals("Ready for deployment", readinessStatusText.text.toString())
        }
    }

    @Test
    fun validateReadiness_showsViolationStateForMainInsecureEndpoint() {
        activityScenarioRule.scenario.onActivity { activity ->
            val environmentInput = activity.findViewById<android.widget.EditText>(R.id.environmentInput)
            val apiBaseUrlInput = activity.findViewById<android.widget.EditText>(R.id.apiBaseUrlInput)
            val validateButton = activity.findViewById<android.widget.Button>(R.id.validateButton)
            val readinessStatusText = activity.findViewById<android.widget.TextView>(R.id.readinessStatusText)

            environmentInput.setText("main")
            apiBaseUrlInput.setText("http://localhost/mock")
            validateButton.performClick()

            assertTrue(readinessStatusText.text.toString().contains("Not ready"))
        }
    }
}
