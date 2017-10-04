package com.example.yihanwang.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageGallery;
import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.entities.ScoreRecord;

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
    private TextView tip;
    private ImageView background;
    private ImageView photo;
    private ImageView info;
    private ImageView plant;
    private List<ImageInfo> images = new ArrayList<>();
    private ImageInfo imageInfo;
    private int currentSelectedGalaryIdx = -1;
    private ImageInfo selectedImage = null;
    private ImageView house;
    private ImageView leaf;

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

        Typeface font = Typeface.createFromAsset(getAssets(), "teen.ttf");

        background = (ImageView) findViewById(R.id.userBackground);
        scoreTitle = (TextView) findViewById(R.id.scoreTitle);
        scoreTitle.setText("Score: " + total + "          Level: " + ScoreUtils.getCurrentLevel(total));
        scoreTitle.setTypeface(font);

        Bundle b = getIntent().getExtras();
        double imageId = b.getDouble("selected_image_id");
        imageInfo = ImageStorage.getInstance().getImageInfoById(imageId);
        if (imageInfo != null) {
            plant = (ImageView) findViewById(R.id.swip_image_view);
            Drawable d = ImageStorage.getInstance().getDrawable(this.getAssets(), imageInfo);
            plant.setImageDrawable(d);
        }
        photo = (ImageView) findViewById(R.id.camera);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        info = (ImageView) findViewById(R.id.book);
        info.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OneListActivity.class);
                Bundle args = new Bundle();
                args.putDouble("image_info_id", imageInfo.getId());
                intent.putExtras(args);
                startActivity(intent);
            }
        });

        house = (ImageView) findViewById(R.id.house);
        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        leaf = (ImageView) findViewById(R.id.yellow_leaf);
        leaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), TipActivity.class));
//                Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "teen.ttf");
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(PhotoImageActivity.this);
//                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tip_activity, null);
//                tip = (TextView) view.findViewById(R.id.tip);
//                tip.setTypeface(font);
//
//                    tip.setText("1. Clicking on a photo you just took to go to the comparison page \n 2. Swiping up the photo you just took to remove it");
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });
//                builder.setView(view);
//                builder.show();
            }
        });

        showPhotos(plant);
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

        ImageGallery imageGallery = ImageGalleryStorage.getInstance().getImageGallery(imageInfo.getId());
        if (imageGallery != null) {
            if (imageGallery.getImageCount() > 2) {
                ImageGalleryStorage.getInstance().removeImageGallery(imageGallery.getId());
            }
        }

        ImageGalleryStorage.getInstance().addImageGallery(imageInfo.getId(), file.getPath());
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            showPhotos(plant);
            int result = 0;
            RealmResults<ScoreRecord> total = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();
            for (ScoreRecord score : total) {
                //Log.i("score", "score " + score.getScore());
                result += score.getScore();
            }

            scoreTitle.setText("Score: " + result + "               Level: " + ScoreUtils.getCurrentLevel(result));

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
        startActivity(intent);
    }

    public void showPhotos(ImageView imageView) {
        final ImageGallery imageGalery = ImageGalleryStorage.getInstance().getImageGallery(imageInfo.getId());

        if (imageGalery != null) {
            int viewIds[] = {R.id.compare_image_view1, R.id.compare_image_view2, R.id.compare_image_view3};
            if (imageGalery.getImageCount() > 0) {
                View galeryContainer = findViewById(R.id.image_galery_container);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) galeryContainer.getLayoutParams();
                layoutParams.weight = 1;
            }

            for (int i = 0; i < viewIds.length; i++) {
                Bitmap image = imageGalery.getImage(i);
                if (image == null) {
                    break;
                }
                final int imageGalleryIdx = i;

                final int recordId = ImageGalleryStorage.getInstance().getImageRecordId(imageInfo.getId(), imageGalleryIdx);

                final ImageView compare = (ImageView) findViewById(viewIds[i]);
                final GestureDetector gestureDetector = new GestureDetector(getBaseContext(), new GestureDetector.OnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent motionEvent) {
                        return true;
                    }

                    @Override
                    public void onShowPress(MotionEvent motionEvent) {

                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent motionEvent) {
                        Log.i("tap", "single tap" + motionEvent.getAction());
                        switch (motionEvent.getAction()) {
                            case MotionEvent.ACTION_UP:
                                showComparisonActivity();
                        }
                        return false;
                    }


                    @Override
                    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent motionEvent) {
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        boolean result = false;
                        try {
                            float diffY = e2.getY() - e1.getY();
                            float diffX = e2.getX() - e1.getX();
                            if (Math.abs(diffX) > Math.abs(diffY)) {
                                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                                    if (diffX > 0) {
                                    } else {
                                    }
                                    result = true;
                                }
                            } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                                if (diffY > 0) {
                                } else {
                                    Log.i("guesture", "swipe top");

                                    ImageGalleryStorage.getInstance().removeGalleryImageById(recordId);
                                    compare.setImageDrawable(null);
                                }
                                result = true;
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        return result;
                    }
                });


                compare.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View view, MotionEvent event) {

                        selectedImage = imageInfo;
                        currentSelectedGalaryIdx = imageGalleryIdx;

                        return gestureDetector.onTouchEvent(event);
                    }
                });

                compare.setImageBitmap(image);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) compare.getLayoutParams();
                layoutParams.weight = 1;
            }
            imageView.setImageDrawable(ImageStorage.getInstance().getDrawable(getAssets(), imageInfo));
        }
    }

}

