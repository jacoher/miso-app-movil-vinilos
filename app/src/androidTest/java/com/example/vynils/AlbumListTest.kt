package com.example.vynils

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers.anyOf


@LargeTest
@RunWith(AndroidJUnit4::class)

class AlbumListTest{
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun AlbumCatalogValidateFirstItemCatalogTest(){
        //Prerrequesitos: Debe existir una item del catálogo del album
        Thread.sleep(5000);

        //Valida si la lista de albumes esta renderizada
        onView(withId(R.id.albumsRv)).check(matches(ViewMatchers.isDisplayed()))

        //Valida si el primer item del catálogo está renderizada y se muestra el nombre del álbum.
        onView(withId(R.id.albumsRv)).check(matches(UtilityTest.atPosition(0, hasDescendant(withText("Buscando América")))))
    }

    @Test
    fun SelectedOptionMenuToDisplayedAlbumCatalogTest(){

//        //abrir menu de opciones
//        onView(withId(R.id.nav_view))
//            .perform(DrawerActions.open());
//
//        //seleccionar la opción de menu Albumes
//        onView(withText("Álbumes")).perform(click());

        //Ingresar un tiempo de espera
        Thread.sleep(5000);

        //verificar que se despliegue la lista de Albumes
        onView(anyOf(withId(R.id.albumsRv)))
            .check(matches(isDisplayed()));

    }

}
