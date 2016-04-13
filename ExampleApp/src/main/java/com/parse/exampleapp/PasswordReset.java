package com.parse.exampleapp;

/**
 * Created by cmtholm on 2/8/16.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class PasswordReset extends Activity {

    Button submit;
    Button home;
    EditText email;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.reset);
        // Locate EditTexts in login.xml

        ExampleApp application = (ExampleApp) getApplication();
        Tracker mTracker = application.getDefaultTracker();

        ParseUser currentUser = ParseUser.getCurrentUser();

        if(currentUser != null) {
            Log.i("OBJECT ID", currentUser.getObjectId());
            mTracker.set("&uid",  currentUser.getObjectId() );

        }

        Log.i("Password Reset", "Not sure what this part is");
        mTracker.setScreenName("Password Reset");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        submit = (Button) findViewById(R.id.reset);
        home = (Button) findViewById(R.id.home);
        email = (EditText) findViewById(R.id.email);

        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user

                ParseUser.requestPasswordResetInBackground(email.getText().toString(), new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // An email was successfully sent with reset instructions.
                            Toast.makeText(getApplicationContext(),
                                    "An email has been sent to " + email.getText().toString() + " with reset instructions",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            // Something went wrong. Look at the ParseException to see what's up.
                            Toast.makeText(getApplicationContext(),
                                    "There was an error sending a reset email, please check the provided email.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


        home.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user
                Intent intent = new Intent(PasswordReset.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });






    }

}
