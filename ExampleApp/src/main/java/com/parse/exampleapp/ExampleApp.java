
package com.parse.exampleapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;


public class ExampleApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(this, "9gVPgmfhQbcvkd5jwXdtuCmIjqXLiAFWkfBGPu9s", "hLcdFOqIAXKmSfrLkYBUS57cmYb2UkT27ZkXRu0H");
    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();

    // If you would like all objects to be private by default, remove this
    // line.
    defaultACL.setPublicReadAccess(true);

    ParseACL.setDefaultACL(defaultACL, true);

  }

  private Tracker mTracker;

  /**
   * Gets the default {@link Tracker} for this {@link Application}.
   * @return tracker
   */
  synchronized public Tracker getDefaultTracker() {
    if (mTracker == null) {
      GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
      // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
      mTracker = analytics.newTracker(R.xml.global_tracker);
    }
    return mTracker;
  }
}
