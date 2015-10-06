package es.claucookie.twitterclient.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.claucookie.twitterclient.R;
import es.claucookie.twitterclient.util.DateHelper;
import es.claucookie.twitterclient.util.FontFactory;

/**
 * Created by claucookie on 03/10/15.
 */
public class TimelineRowView extends BaseViewHolder<Tweet> {

    private static final String TAG = TimelineRowView.class.getSimpleName();

    public static final int LAYOUT_ID = R.layout.view_timeline_row;
    public static final String SERVER_DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"; // Mon Oct 05 21:35:29 +0000 2015

    @Bind(R.id.username_label)
    TextView usernameLabel;
    @Bind(R.id.user_image)
    ImageView userImage;
    @Bind(R.id.content_label)
    TextView contentLabel;
    @Bind(R.id.date_label)
    TextView dateLabel;

    private Tweet tweet;

    public TimelineRowView(Context context, View view) {
        super(context, view);
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(final Tweet object) {
        this.tweet = object;

        // Customize UI
        setCustomFont(usernameLabel, "Roboto-Bold");
        setCustomFont(contentLabel, "Roboto-Light");
        setCustomFont(dateLabel, "Roboto-Light");

        // Fill with data
        if (tweet != null) {
            usernameLabel.setText(String.format("%s @%s", tweet.user.name, tweet.user.screenName));
            Picasso.with(context).load(tweet.user.profileImageUrl).placeholder(R.drawable.tw__ic_logo_default).into(userImage);
            contentLabel.setText(tweet.text);
            Linkify.addLinks(contentLabel, Linkify.WEB_URLS);
            dateLabel.setText(getReadableDate(tweet.createdAt));
        }

        // Setting click event
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListEventListener().onListEvent(0, tweet, v);
            }
        });

    }

    private String getReadableDate(String createdAt) {
        return DateHelper.getDatetimeAgo(createdAt, SERVER_DATE_FORMAT);
    }

    private void setCustomFont(TextView textView, String fontName) {
        Typeface typeface = FontFactory.getInstance().getTypeface(context, fontName);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
    }
}
