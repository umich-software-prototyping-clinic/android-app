package com.parse.exampleapp;

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
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by cmtholm on 2/8/16.
 */
public class Signup extends Activity {


    Button submit;
    Button home; //TODO: add a return home button
    String nameTxt;
    String emailTxt;
    String usernameTxt;
    String passwordTxt;
    String passConfirmTxt;
    EditText name;
    EditText email;
    EditText passConfirm;
    EditText password;
    EditText username;


    //private method to check if all of the provided text fields contain some text
    private boolean checkFieldsFilled(String name, String email, String un, String pw, String pwc) {
        if(name.equals(""))
            return false;
        else if(email.equals(""))
            return false;
        else if(un.equals(""))
            return false;
        else if(pw.equals(""))
            return false;
        else if(pwc.equals(""))
            return false;

        return true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from main.xml
        setContentView(R.layout.signup);
        // Locate EditTexts in login.xml


        // GOOGLE ANALYTICS CODE
        ExampleApp application = (ExampleApp) getApplication();
        Tracker mTracker = application.getDefaultTracker();

        ParseUser currentUser = ParseUser.getCurrentUser();

        Log.i("Signup", "Not sure what this part is");
        mTracker.setScreenName("Signup");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // END GOOGLE ANALYTICS CODE

        submit = (Button) findViewById(R.id.submit);
        home = (Button) findViewById(R.id.home);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        passConfirm = (EditText) findViewById(R.id.confirm);

        submit.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Retrieve the text entered from the EditText
                nameTxt = name.getText().toString();
                emailTxt = email.getText().toString();
                usernameTxt = username.getText().toString();
                passwordTxt = password.getText().toString();
                passConfirmTxt = passConfirm.getText().toString();


                // Force user to fill up the form
                // Also should validate email address but leave that for later
                if (!checkFieldsFilled(nameTxt, emailTxt, usernameTxt, passwordTxt, passConfirmTxt)) {
                    //toast puts a small notification on the user's screen
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();
                }
                else if(!passwordTxt.equals(passConfirmTxt)) {
                        Toast.makeText(getApplicationContext(),
                                "Passwords Don't Match!",
                                Toast.LENGTH_LONG).show();

                    //clear the password confirm field
                    passConfirm.clearComposingText();
                }
                 else {
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    user.put("name", nameTxt);
                    user.setEmail(emailTxt);
                    user.setUsername(usernameTxt);
                    user.setPassword(passwordTxt);

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up, please log in.",
                                        Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(Signup.this, SignupConfirm.class);
                                intent.putExtra("Name", nameTxt);
                                intent.putExtra("Email", emailTxt);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }

            }
        });

        home.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user

                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
