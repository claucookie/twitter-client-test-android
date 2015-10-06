package es.claucookie.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.claucookie.twitterclient.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.twitter_login_button) TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        initTwitterButton();
        checkTwitterSession();
    }

    private void checkTwitterSession() {
        // If user already authenticated with twitter, show timeline
        SessionManager<TwitterSession> sessionManager =  Twitter.getSessionManager();
        if (sessionManager.getActiveSession() != null) {
            loadTimelineActivity();
        }
    }

    private void initTwitterButton() {
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                loadTimelineActivity();
            }

            @Override
            public void failure(TwitterException exception) {
                // An exception is shown already in case of error
            }
        });
    }

    private void loadTimelineActivity() {
        Intent timelineIntent = new Intent(this, TimelineActivity.class);
        startActivity(timelineIntent);
        finish();
    }

    /**
     * Listeners and callbacks
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}

