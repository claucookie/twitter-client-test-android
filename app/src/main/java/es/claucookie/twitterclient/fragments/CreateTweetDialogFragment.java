package es.claucookie.twitterclient.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import es.claucookie.twitterclient.R;

/**
 * Created by claucookie on 07/10/15.
 */
public class CreateTweetDialogFragment extends DialogFragment {

    public interface CreateTweetDialogListener {
        public void onDialogPostClick(String tweetContent);
    }

    // Use this instance of the interface to deliver action events
    CreateTweetDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (CreateTweetDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement CreateTweetDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_create_tweet);
        builder.setPositiveButton(R.string.post_tweet, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    EditText inputField = (EditText) getDialog().findViewById(R.id.tweet_input);
                    listener.onDialogPostClick(inputField.getText().toString());
                }
            }
        });
        return builder.create();
    }
}
