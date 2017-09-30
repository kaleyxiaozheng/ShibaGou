package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageGallery;
import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.entities.ScoreRecord;
import com.example.yihanwang.myapplication.entities.TextProgressBar;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class PhotoImageActivity extends AppCompatActivity {
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private TextView scoreTitle;
    private ImageView background;
    private ImageView photo;
    private ImageView info;
    private ImageView plant;
    private List<ImageInfo> images = new ArrayList<>();
    private ImageInfo imageInfo;
    private int currentSelectedGalaryIdx = -1;
    private ImageInfo selectedImage = null;
    private ImageView image_1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_image_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Realm realm = Realm.getDefaultInstance();

        int total = 0;

        realm.beginTransaction();
        RealmResults<ScoreRecord> results = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();

        for (ScoreRecord score : results) {
            total = total + score.getScore();
        }
        realm.commitTransaction();

        Typeface font = Typeface.createFromAsset(getAssets(), "retganon.ttf");

        background = (ImageView) findViewById(R.id.userBackground);
        scoreTitle = (TextView) findViewById(R.id.scoreTitle);
        scoreTitle.setText("Score: " + total + "               Level: " + ScoreUtils.getCurrentLevel(total));
        scoreTitle.setTypeface(font);

        Bundle b = getIntent().getExtras();
        double imageId = b.getDouble("selected_image_id");
        imageInfo = ImageStorage.getInstance().getImageInfoById(imageId);
        if (imageInfo != null) {
            plant = (ImageView) findViewById(R.id.swip_image_view);
            Drawable d = ImageStorage.getInstance().getDrawable(this.getAssets(), imageInfo) ;
            plant.setImageDrawable(d);
        }
        photo = (ImageView) findViewById(R.id.takePhoto);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        info = (ImageView) findViewById(R.id.imageInfo);
        info.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OneListActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && data.getExtras() != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            SaveImage(bitmap);
        }
    }

    private void SaveImage(Bitmap finalBitmap) {
        String root = getApplicationContext().getExternalCacheDir().getAbsolutePath();
        File myDir = new File(root, "saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        ImageGalleryStorage.getInstance().addImageGallery(imageInfo.getId(), file.getPath());
        final ImageGallery imageGalery = ImageGalleryStorage.getInstance().getImageGallery(imageInfo.getId());

        image_1 = (ImageView)findViewById(R.id.compare_image_view1);


        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showComparisonActivity() {
        Intent intent = new Intent(this.getApplicationContext(), ComparisonActivity.class);
        Bundle b = new Bundle();
        b.putDouble("firstImage", this.selectedImage.getId());
        b.putInt("secondImage", this.currentSelectedGalaryIdx);
        intent.putExtras(b);
        this.getApplicationContext().startActivity(intent);
    }

}

