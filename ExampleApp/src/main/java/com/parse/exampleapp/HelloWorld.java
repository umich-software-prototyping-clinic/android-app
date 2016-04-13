package com.parse.exampleapp;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cmtholm on 2/9/16.
 */
public class HelloWorld extends Activity {

    Button logout;
    Button changepass;
    Button userlist;
    Button printbtn;
    TextView txt;
    EditText inputtxt;
    Tracker mTracker;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from singleitemview.xml
        setContentView(R.layout.helloworld);

        ExampleApp application = (ExampleApp) getApplication();
        mTracker = application.getDefaultTracker();

        ParseUser currentUser = ParseUser.getCurrentUser();

        if(currentUser != null) {
            Log.i("OBJECT ID", currentUser.getObjectId());
            mTracker.set("&uid",  currentUser.getObjectId() );

        }

        Log.i("Hello World", "Not sure what this part is");
        mTracker.setScreenName("Hello World");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        printbtn = (Button) findViewById(R.id.button);
        txt = (TextView) findViewById(R.id.txt);
        inputtxt = (EditText) findViewById(R.id.editText);

        printbtn.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user

                String printedText = inputtxt.getText().toString();

                Map<String, String> dimensions = new HashMap<String, String>();

                // set-up the desired formatting
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Calendar cal = Calendar.getInstance();
                Date now = cal.getTime();
                String nowString = sdf.format(now);

                dimensions.put("date", nowString);
                dimensions.put("text", printedText);
                ParseAnalytics.trackEventInBackground("print", dimensions);

                txt.setText(printedText);
            }
        });


        logout = (Button) findViewById(R.id.logout);

        // Logout Button Click Listener
        logout.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user
                ParseUser.logOut();
                Intent intent = new Intent(HelloWorld.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        changepass = (Button) findViewById(R.id.changepassword);
        changepass.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(HelloWorld.this, PasswordReset.class);
                startActivity(intent);
                finish();
            }
        });

        userlist = (Button) findViewById(R.id.userlist);
        userlist.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Intent intent = new Intent(HelloWorld.this, Welcome.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
