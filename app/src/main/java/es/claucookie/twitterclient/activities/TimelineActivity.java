package es.claucookie.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.claucookie.twitterclient.R;
import es.claucookie.twitterclient.adapters.RecyclerViewBaseAdapter;
import es.claucookie.twitterclient.fragments.CreateTweetDialogFragment;
import es.claucookie.twitterclient.views.BaseViewHolder;
import es.claucookie.twitterclient.views.TimelineRowView;

/**
 * Created by claucookie on 03/10/15.
 */
public class TimelineActivity extends AppCompatActivity implements BaseViewHolder.ListEventListener, CreateTweetDialogFragment.CreateTweetDialogListener {

    @Bind(R.id.timeline_list)
    RecyclerView timelineList;
    @Bind(R.id.root_layout)
    CoordinatorLayout rootLayout;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private static final Integer TWEET_COUNT = 20;
    private ArrayList<Tweet> tweetsArray = new ArrayList<Tweet>();
    private RecyclerViewBaseAdapter<Tweet, TimelineRowView> recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        initRecyclerView();
        initRefreshLayout();
        showLoadingView();
        requestTimelineTweets();
    }

    private void initRecyclerView() {
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        timelineList.setHasFixedSize(true);

        // Now with Recycler view needs a LinearLayoutManager
        timelineList.setLayoutManager(new LinearLayoutManager(this));
    }


    private void initRefreshLayout() {
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        requestTimelineTweets();
                    }
                }
        );
    }

    /**
     * Show and hide methods
     */
    private void showLoadingView() {
        refreshLayout.setRefreshing(true);
    }

    private void hideLoadingView() {
        refreshLayout.setRefreshing(false);
    }

    /**
     * Request and fill methods
     */

    private void requestTimelineTweets() {
        TwitterApiClient twitterApiClient = Twitter.getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();
        statusesService.homeTimeline(TWEET_COUNT, null, null, null, null, null, null, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                tweetsArray = new ArrayList<Tweet>(result.data);
                hideLoadingView();
                loadTweets();
            }

            @Override
            public void failure(TwitterException e) {
                hideLoadingView();
                Snackbar.make(rootLayout, e.getLocalizedMessage(), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void loadTweets() {
        if (recyclerViewAdapter == null) {
            recyclerViewAdapter = new RecyclerViewBaseAdapter<Tweet, TimelineRowView>(Tweet.class, TimelineRowView.class, TimelineRowView.LAYOUT_ID, tweetsArray);
            recyclerViewAdapter.setListEventListener(this);
            timelineList.setAdapter(recyclerViewAdapter);
        } else {
            recyclerViewAdapter.setItems(tweetsArray);
        }

    }

    /**
     * Click and Menu methods
     */

    @OnClick(R.id.create_tweet_button)
    public void loadTweetEditor() {
        CreateTweetDialogFragment dialog = new CreateTweetDialogFragment();
        dialog.show(getSupportFragmentManager(), "CreateTweetDialogFragment");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        Twitter.logOut();
        loadLoginActivity();
    }

    private void loadLoginActivity() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    /**
     * Listeners
     */

    @Override
    public void onListEvent(int actionId, Object item, View view) {
        // When a tweet is click the event is caught here
    }

    @Override
    public void onDialogPostClick(String tweetContent) {

        TwitterApiClient twitterApiClient = Twitter.getApiClient();
        twitterApiClient.getStatusesService().update(tweetContent, null, null, null, null, null, null, null, null, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                requestTimelineTweets();
            }

            @Override
            public void failure(TwitterException e) {
                Snackbar.make(rootLayout, e.getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
