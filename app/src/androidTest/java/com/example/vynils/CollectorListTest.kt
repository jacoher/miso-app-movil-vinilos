package com.example.vynils

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class CollectorListTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun testColeccionistaLis(){
        //Validar que hay al menos un registro con los datos completos
        //Prerrequesitos: Se asume que existe al menos un registro en la lista de albums

        Thread.sleep(1000);

        Espresso.onView(withId(R.id.navigation_collectors)).perform(ViewActions.click())
        Thread.sleep(1000)

        //Valida que existe la lista de coleccionistas
        Espresso.onView(withId(R.id.collectorRv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Validamos items de la lista
        Espresso.onView(withId(R.id.collectorRv)).check(
            ViewAssertions.matches(
                UtilityTest.atPosition(
                    0,
                    ViewMatchers.hasDescendant(ViewMatchers.withText("Manolo Bellon"))
                )
            )
        )
        Espresso.onView(withId(R.id.collectorRv)).check(
            ViewAssertions.matches(
                UtilityTest.atPosition(
                    1,
                    ViewMatchers.hasDescendant(ViewMatchers.withText("Jaime Monsalve"))
                )
            )
        )
    }
}