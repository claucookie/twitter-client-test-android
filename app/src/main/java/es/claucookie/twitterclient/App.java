package es.claucookie.twitterclient;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by claucookie on 03/10/15.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initFabricSDK();
    }

    private void initFabricSDK() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_CONSUMER_KEY,
                BuildConfig.TWITTER_CONSUMER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }
}
