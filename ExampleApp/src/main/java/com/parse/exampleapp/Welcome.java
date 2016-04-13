package com.parse.exampleapp;

/**
 * Created by cmtholm on 2/8/16.
 */

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Welcome extends Activity {

    // Declare Variable
    Button logout;
    Button changepass;
    Button helloworld;
    TextView userlist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.welcome);

        ExampleApp application = (ExampleApp) getApplication();
        Tracker mTracker = application.getDefaultTracker();

        ParseUser currentUser = ParseUser.getCurrentUser();

        if(currentUser != null) {
            Log.i("OBJECT ID", currentUser.getObjectId());
            mTracker.set("&uid",  currentUser.getObjectId() );
        }

        Log.i("Welcome", "Not sure what this part is");
        mTracker.setScreenName("Welcome");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        // Retrieve current user from Parse.com

        // Convert currentUser into String
        String struser = currentUser.getUsername().toString();

        // Locate TextView in welcome.xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);
        userlist = (TextView) findViewById(R.id.userlist);
        userlist.setMovementMethod(new ScrollingMovementMethod());
        // Set the currentUser String into TextView
        txtuser.setText("You are logged in as " + struser);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    //
                    Log.d("User", "Retrieved User list");

//                    for(ParseUser user : objects) {
//                        userlist.append(user.getUsername().toString() + "\n");
//                    }

                } else {
                    // Something went wrong.
                    Toast.makeText(getApplicationContext(),
                            "There was an error receiving user list.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // Locate Button in welcome.xml
        logout = (Button) findViewById(R.id.logout);

        // Logout Button Click Listener
        logout.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user
                ParseUser.logOut();
                Intent intent = new Intent(Welcome.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        changepass = (Button) findViewById(R.id.changepassword);
        changepass.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(Welcome.this, PasswordReset.class);
                startActivity(intent);
                finish();
            }
        });

        helloworld = (Button) findViewById(R.id.printpage);
        helloworld.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(Welcome.this, HelloWorld.class);
                startActivity(intent);
                finish();
            }
        });
    }
}