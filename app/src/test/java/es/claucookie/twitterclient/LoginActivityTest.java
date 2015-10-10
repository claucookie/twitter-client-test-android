package es.claucookie.twitterclient;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import es.claucookie.twitterclient.activities.LoginActivity;
import es.claucookie.twitterclient.activities.TimelineActivity;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    @Test
    public void clickingLogin_shouldStartTwitterActivity() {
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        activity.findViewById(R.id.twitter_login_button).performClick();

        Intent expectedIntent = new Intent(activity, TimelineActivity.class);
        assertThat(Shadows.shadowOf(activity).getNextStartedActivity()).isEqualTo(expectedIntent);
    }
}