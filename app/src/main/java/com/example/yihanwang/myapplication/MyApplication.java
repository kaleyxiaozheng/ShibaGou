package com.example.yihanwang.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.yihanwang.myapplication.entities.DBMigration;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Required initialization logic here!
        try {
            copyBundledRealmFile(this.getResources().openRawResource(R.raw.image_info), "default0.realm");

            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .name("default0.realm").schemaVersion(2)
                    .migration(new DBMigration()).build();
            Realm.setDefaultConfiguration(config);
            String path = Realm.getDefaultInstance().getPath();
            Log.i("database", "db path:" + path);
            RealmResults<ImageInfo> allImages = Realm.getDefaultInstance().where(ImageInfo.class).findAll();
            Log.i("database", "all image count " + allImages.size());
            for (ImageInfo imageInfo : allImages) {
                ImageStorage.getInstance().addImage(imageInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Log.e("error", e.getMessage());
//            Realm.init(this);
//            RealmConfiguration config = new RealmConfiguration.Builder().name("default0.realm").schemaVersion(2).build();
//            Realm.deleteRealm(config);
//            Realm.setDefaultConfiguration(config);
//            String path = Realm.getDefaultInstance().getPath();
//            Log.i("database", "db path:" + path);
        }
    }

    private String copyBundledRealmFile(InputStream inputStream, String outFileName) {
        try {
            File file = new File(this.getFilesDir(), outFileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
