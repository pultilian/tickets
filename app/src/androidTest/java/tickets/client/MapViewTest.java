package tickets.client;

import android.app.Activity;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.LayoutInflater;

import org.junit.*;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import tickets.client.gui.activities.GameActivity;
import tickets.client.gui.activities.LoginActivity;
import tickets.client.gui.activities.R;
import tickets.client.gui.fragments.MapFragment;
import tickets.client.gui.views.MapView;
import tickets.server.ServerCommunicator;
import tickets.server.ServerFacade;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class MapViewTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void resetGame() {
        //reset the espresso timeout counter

        //setup the game
        login();
        startGame();
        pickDestinations();

//        InstrumentationRegistry.



        //basic Espresso form
        onView(withId(R.id.gameMap));
    }


    @Test
    public void testInit() {
        assertNotNull(mActivityRule.getActivity());
//        assertEquals(MapStub.class, mActivityRule.getActivity().getClass());

    }

    private boolean login() {
        onView(withHint("IP Address")).perform(typeText("192.168.0.100"));
        onView(withId(R.id.username)).perform(typeText("www"));
        onView(withId(R.id.password)).perform(typeText("www"));
        onView(withId(R.id.login_button)).perform(click());

        while(true) {
            //check if the activity has changed?
        }
    }

    private boolean startGame() {
        //create a lobby
        //start the game
        return false;
    }

    private boolean pickDestinations() {
        //select starting destination cards
        return false;
    }
}
