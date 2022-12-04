package com.example.vynils

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test

class PerformerDetailTest{
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testDetailPerformer(){
        //Validar que hay al menos un registro con los datos completos
        //Prerrequesitos: Se asume que existe al menos un registro en la lista de artistas

        Thread.sleep(1000);
        Espresso.onView(withId(R.id.navigation_performers)).perform(ViewActions.click())

        Thread.sleep(1000);
        //Valida si la lista de artistas esta renderizada
        Espresso.onView(withId(R.id.performersRv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.textViewPerformerName))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.textViewCreationDate))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.scrollAlbumList))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
        Espresso.onView(withId(R.id.performersRv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}