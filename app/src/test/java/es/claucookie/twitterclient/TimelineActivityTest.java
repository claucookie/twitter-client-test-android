package es.claucookie.twitterclient;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import es.claucookie.twitterclient.activities.LoginActivity;
import es.claucookie.twitterclient.activities.TimelineActivity;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;


/**
 * Created by claucookie on 10/10/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class TimelineActivityTest {

    @Test
    public void onCreate_shouldInflateTheMenu() throws Exception {
        Activity activity = Robolectric.setupActivity(TimelineActivity.class);

        assertNotNull(activity);
        final Menu menu = Shadows.shadowOf(activity).getOptionsMenu();
        assertThat(menu.findItem(R.id.logout).getTitle()).isEqualTo(activity.getResources().getString(R.string.logout));
    }

    @Test
    public void logoutButton_shouldReturnToLoginActivity() {
        TimelineActivity activity = Robolectric.setupActivity(TimelineActivity.class);

        final Menu menu = Shadows.shadowOf(activity).getOptionsMenu();
        menu.findItem(R.id.logout).getActionView().performClick();

        Intent expectedIntent = new Intent(activity, LoginActivity.class);
        assertThat(Shadows.shadowOf(activity).getNextStartedActivity()).isEqualTo(expectedIntent);
    }
}
