package com.m.shasho.mygymgroup;

import android.app.Application;

import com.m.shasho.mygymgroup.DatabaseParse.DatabaseParse;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //App application = this;
      //  ParseObject.registerSubclass(DatabaseParse.NAME_DATABASE);// for example
      //  ParseObject.registerSubclass(PersonGym.class);

        //Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}