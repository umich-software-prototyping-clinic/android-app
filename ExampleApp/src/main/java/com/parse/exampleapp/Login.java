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


//TODO: check if user is authenticated
//TODO: add change password page
public class Login extends Activity {
    // Declare Variables
    Button loginbutton;
    Button signup;
    Button reset;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.login);
        // Locate EditTexts in login.xml

        ExampleApp application = (ExampleApp) getApplication();
        Tracker mTracker = application.getDefaultTracker();

        Log.i("Login", "Not sure what this part is");
        mTracker.setScreenName("Login");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        // Locate Buttons in login.xml
        loginbutton = (Button) findViewById(R.id.login);
        reset = (Button) findViewById(R.id.reset);
        signup = (Button) findViewById(R.id.signup);

        // Login Button Click Listener
        loginbutton.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {


                                if ((user != null)) {

                                    boolean isEmailVerified = (boolean) user.get("emailVerified");
                                    // If user exist and authenticated, send user to Welcome.class
                                    if(isEmailVerified) {
                                        Intent intent = new Intent(
                                                Login.this,
                                                MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),
                                                "Successfully Logged in",
                                                Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(
                                                getApplicationContext(),
                                                "Please authenticate email address",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Username or Password is incorrect",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Login.this, PasswordReset.class);
                startActivity(intent);
                finish();
            }
        });

        // Sign up Button Click Listener
        signup.setOnClickListener((new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
                finish();
            }
        }));

//        signup.setOnClickListener(new OnClickListener() {
//
//            public void onClick(View arg0) {
//                // Retrieve the text entered from the EditText
//                usernameTxt = username.getText().toString();
//                passwordtxt = password.getText().toString();
//
//                // Force user to fill up the form
//                if (usernameTxt.equals("") && passwordtxt.equals("")) {
//                    Toast.makeText(getApplicationContext(),
//                            "Please complete the sign up form",
//                            Toast.LENGTH_LONG).show();
//
//                } else {
//                    // Save new user data into Parse.com Data Storage
//                    ParseUser user = new ParseUser();
//                    user.setUsername(usernameTxt);
//                    user.setPassword(passwordtxt);
//                    user.signUpInBackground(new SignUpCallback() {
//                        public void done(ParseException e) {
//                            if (e == null) {
//                                // Show a simple Toast message upon successful registration
//                                Toast.makeText(getApplicationContext(),
//                                        "Successfully Signed up, please log in.",
//                                        Toast.LENGTH_LONG).show();
//                            } else {
//                                Toast.makeText(getApplicationContext(),
//                                        "Sign up Error", Toast.LENGTH_LONG)
//                                        .show();
//                            }
//                        }
//                    });
//                }
//
//            }
//        });

    }
}