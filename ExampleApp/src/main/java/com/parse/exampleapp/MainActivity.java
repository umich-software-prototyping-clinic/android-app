/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.exampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {


  private Tracker mTracker;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    ParseAnalytics.trackAppOpenedInBackground(getIntent());

    // Obtain the shared Tracker instance.
    ExampleApp application = (ExampleApp) getApplication();
//    mTracker = application.getDefaultTracker();
//
//    Log.i("Main Activity", "Not sure what this part is");
//    mTracker.setScreenName("Main Activity");
//    mTracker.send(new HitBuilders.ScreenViewBuilder().build());

    // Determine whether the current user is an anonymous user
    if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
      // If user is anonymous, send the user to Login.class
      Intent intent = new Intent(MainActivity.this,
              Login.class);
      startActivity(intent);
      finish();
    } else {
      // If current user is NOT anonymous user
      // Get current user data from Parse.com
      ParseUser currentUser = ParseUser.getCurrentUser();


      if ((currentUser != null)) {
          // Send logged in users to Welcome.class
        //boolean isEmailVerified = (boolean) currentUser.get("emailVerified");
       // if(isEmailVerified) {
          Intent intent = new Intent(MainActivity.this, Welcome.class);
          startActivity(intent);
          finish();
       // }
      }
      else {  // Send user to Login.class
        Intent intent = new Intent(MainActivity.this,
                Login.class);
        startActivity(intent);
        finish();
      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
