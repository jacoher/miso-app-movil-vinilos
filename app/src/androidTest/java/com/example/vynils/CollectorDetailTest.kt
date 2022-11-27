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
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test

class CollectorDetailTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testDetailColeccionista(){
        //Validar que hay al menos un registro con los datos completos
        //Prerrequesitos: Se asume que existe al menos un registro en la lista de albums

        Thread.sleep(1000);

        //Va a la lista de coleccionistas
        Espresso.onView(withId(R.id.navigation_collectors)).perform(ViewActions.click())
        Thread.sleep(5000)

        //Valida que existe la lista de coleccionistas
        Espresso.onView(withId(R.id.collectorRv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.collectorRv)).check(
            ViewAssertions.matches(
                UtilityTest.atPosition(
                    0,
                    ViewMatchers.hasDescendant(ViewMatchers.withText("Manolo Bellon"))
                )
            )
        )

        //Va al detalle del coleccionista
        Espresso.onView(withId(R.id.collectorRv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        Thread.sleep(3000)
        Espresso.onView(withId(R.id.textViewCollectorName))
            .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString("Manolo Bellon"))))
        Espresso.onView(withId(R.id.scrollAlbumList))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.scrollPerformerList))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}