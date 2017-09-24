package com.example.yihanwang.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.yihanwang.myapplication.entities.ImageGallery;
import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.entities.ScoreRecord;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ImageGalleryStorage {

    private static final ImageGalleryStorage instance = new ImageGalleryStorage();

    public static ImageGalleryStorage getInstance() {
        return instance;
    }

    public void addImageGallery(double imageId, String path) {
        Log.i("gallery", "save image gallery " + imageId);

        RealmResults<ScoreRecord> allImages = Realm.getDefaultInstance().where(ScoreRecord.class).equalTo("imageId", imageId).findAll();
        if (allImages == null || allImages.size() > 3) {
            return;
        }
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        ScoreRecord record = realm.createObject(ScoreRecord.class);
        Number maxId = realm.where(ScoreRecord.class).max("id");
        if (maxId != null) {
            record.setId(maxId.intValue() + 1);
        } else {
            record.setId(1);
        }

        record.setImageId(imageId);
        record.setScore(10);
        record.setImagePath(path);
        realm.commitTransaction();
    }

    public void removeImageGallery(int id) {
        Realm realm = Realm.getDefaultInstance();
        ScoreRecord score = realm.where(ScoreRecord.class).equalTo("id", id).findFirst();
        if(score != null){
            realm.beginTransaction();
            score.deleteFromRealm();
            realm.commitTransaction();
        }
    }

    public ImageGallery getImageGallery(double id) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ScoreRecord> recordList = realm.where(ScoreRecord.class).equalTo("imageId", id).findAll();
        ImageGallery gallery = null;
        if (recordList.size() > 0) {
            gallery = new ImageGallery(recordList.get(0).getImageId(), "");
            for (ScoreRecord record : recordList) {
                Bitmap bitmap = BitmapFactory.decodeFile(record.getImagePath());
                gallery.addImage(bitmap);
            }
        }
        return gallery;
    }

    public void removeGalleryImage(double imageId, int galleryIdx) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<ScoreRecord> recordList = realm.where(ScoreRecord.class).equalTo("imageId", imageId).findAll();
        if(recordList.size() > galleryIdx ){
            realm.beginTransaction();
            ScoreRecord scoreRecord = recordList.get(galleryIdx);
            scoreRecord.deleteFromRealm();
            realm.commitTransaction();
        }
    }
}
