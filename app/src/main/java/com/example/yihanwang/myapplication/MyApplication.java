package com.example.yihanwang.myapplication;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        try {
            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration.Builder().name("realmdb").schemaVersion(2).build();
            Realm.setDefaultConfiguration(config);
            String path = Realm.getDefaultInstance().getPath();
            Log.i("database", "db path:" + path);
        }catch(Exception e){
            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration.Builder().name("realmdb").schemaVersion(2).build();
            Realm.setDefaultConfiguration(config);
            Realm.deleteRealm(config);
            String path = Realm.getDefaultInstance().getPath();
            Log.i("database", "db path:" + path);
        }
    }
}
