package com.parse.exampleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.ParseUser;

/**
 * Created by cmtholm on 2/8/16.
 */
public class SignupConfirm extends Activity {

    // Declare Variable
    Button login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.signupconfirm);

        ExampleApp application = (ExampleApp) getApplication();
        Tracker mTracker = application.getDefaultTracker();

        Log.i("Signup Confirm", "Not sure what this part is");
        mTracker.setScreenName("Signup Confirm");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());

        Intent intent = getIntent();
        String nametxt = intent.getStringExtra("Name");
        String emailtxt = intent.getStringExtra("Email");

        TextView name = (TextView) findViewById(R.id.namemsg);
        TextView email = (TextView) findViewById(R.id.txtemail);

        name.append(nametxt + "!");
        email.setText(emailtxt);

        // Locate TextView in welcome.xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);

        // Locate Button in welcome.xml
        login = (Button) findViewById(R.id.login);

        // Logout Button Click Listener
        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user
                Intent intent = new Intent(SignupConfirm.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
