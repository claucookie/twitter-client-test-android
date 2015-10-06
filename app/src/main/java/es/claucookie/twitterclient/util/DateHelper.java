package es.claucookie.twitterclient.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by claucookie on 06/10/15.
 */
public class DateHelper {

    private static final String TAG = DateHelper.class.getSimpleName();

    public static final String HUMAN_DATE_FORMAT = "dd MMM"; // 05 oct
    public static final String HUMAN_NOW_FORMAT = "now"; // 3h ago
    public static final String HUMAN_HOURS_AGO_FORMAT = "%sh"; // 3h ago
    public static final String HUMAN_MIN_AGO_FORMAT = "%smin"; // 3min ago
    public static final long ONE_MIN_IN_MIN = 1;
    public static final long ONE_HOUR_AGO_IN_MIN = 60;
    public static final long ONE_DAY_AGO_IN_MIN = 24 * 50;


    public static String getDatetimeAgo(String createdAtString, String createdAtPattern) {
        String createdAtReadable = "";
        SimpleDateFormat inFormat = new SimpleDateFormat(createdAtPattern, Locale.US);
        try {
            Date createdDate = inFormat.parse(createdAtString);
            createdAtReadable = getReadableFormat(createdDate);
        } catch (ParseException e) {
            Log.d(TAG, e.getLocalizedMessage());
        }
        return createdAtReadable;
    }

    private static String getReadableFormat(Date createdDate) {
        Date nowDate = new Date();
        long minsAgo = (nowDate.getTime() - createdDate.getTime()) / 1000 / 60;

        // Find a good format to show the createdAt time
        if (minsAgo < ONE_MIN_IN_MIN) {
            return HUMAN_NOW_FORMAT;
        } else if ( minsAgo < ONE_HOUR_AGO_IN_MIN ) {
            return String.format(Locale.US, HUMAN_MIN_AGO_FORMAT, minsAgo);
        } else if ( minsAgo  < ONE_DAY_AGO_IN_MIN) {
            return String.format(Locale.US, HUMAN_HOURS_AGO_FORMAT, minsAgo);
        } else {
            SimpleDateFormat outFormat = new SimpleDateFormat(HUMAN_DATE_FORMAT, Locale.US);
            return outFormat.format(createdDate);
        }
    }
}
