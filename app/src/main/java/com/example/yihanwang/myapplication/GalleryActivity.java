package com.example.yihanwang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ScoreRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Kaley on 18/9/17.
 */

public class GalleryActivity extends AppCompatActivity {

    private int deviceWidth = 1024;
    private ImageAdapter adapter;
    private ImageView deleteBtn;
    private ImageView house;
    private ImageView mess;
    private TextView gallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceWidth = displayMetrics.widthPixels;

        final GridView gridView = (GridView) findViewById(R.id.gallery_view);

        RealmResults<ScoreRecord> allRecords = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();
        adapter = new ImageAdapter(this, allRecords, deviceWidth);
        gridView.setAdapter(adapter);

        this.deleteBtn = (ImageView) findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.deleteSelectedImages();
                RealmResults<ScoreRecord> records = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();
                adapter.loadImageData(records);
                gridView.setAdapter(null);
                gridView.setAdapter(adapter);
            }
        });

        Typeface font = Typeface.createFromAsset(getAssets(), "teen.ttf");

        gallery = (TextView) findViewById(R.id.gallery);
        gallery.setText("Your gallery");
        gallery.setTypeface(font);

        house = (ImageView) findViewById(R.id.nextPlant);
        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mess = (ImageView) findViewById(R.id.instruction);
        mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GalleryMessage.class));
            }
        });
    }


    class ImageAdapter extends BaseAdapter {

        private final int deviceWidth;
        private Context context;
        private List<Bitmap> images = new ArrayList<>();
        private List<Double> imageIds = new ArrayList<>();
        private List<Integer> imageGalleryIds = new ArrayList<>();

        private List<Integer> selectedImages = new ArrayList<>();

        public ImageAdapter(Context context, RealmResults<ScoreRecord> files, int deviceWidth) {
            this.context = context;
            this.deviceWidth = deviceWidth;
            loadImageData(files);
        }

        private void loadImageData(RealmResults<ScoreRecord> files) {
            images.clear();
            imageIds.clear();
            imageGalleryIds.clear();
            selectedImages.clear();
            for (ScoreRecord file : files) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getImagePath());
                    images.add(bitmap);
                    imageIds.add(file.getImageId());
                    imageGalleryIds.add(file.getId());
                    Log.i("db", "image gallery id " + file.getId());
                } catch (Exception e) {
                    Log.e("image", e.getMessage());
                }
            }
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public void deleteSelectedImages() {
            for (Integer pos : selectedImages) {
                deleteImage(imageGalleryIds.get(pos));
            }
        }

        public void deleteImage(int galleryId) {
            Realm realm = Realm.getDefaultInstance();
            ScoreRecord record = realm.where(ScoreRecord.class).equalTo("id", galleryId).findFirst();
            if (record != null) {
                String imagePath = record.getImagePath();
                boolean delete = new File(imagePath).delete();
                if (!delete) {
                    Log.e("image", "failed to delete image on " + imagePath);
                }
                ImageGalleryStorage.getInstance().removeImageGallery(galleryId);
            } else {
                Log.e("db", "cant find gallery to delete " + galleryId);
            }
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup viewGroup) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams((deviceWidth - 50) / 3, (deviceWidth - 50) / 3));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageBitmap(images.get(position));
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GalleryImageViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putDouble("image_id", imageIds.get(position));
                    bundle.putInt("image_gallery_id", imageGalleryIds.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });

            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (selectedImages.indexOf(position) >= 0) {
                        view.setAlpha(1f);
                        selectedImages.remove(new Integer(position));
                    } else {
                        selectedImages.add(position);
                        view.setAlpha(0.5f);
                    }
                    if (selectedImages.size() > 0) {
                        deleteBtn.setEnabled(true);
                    } else {
                        deleteBtn.setEnabled(false);
                    }
                    return true;
                }
            });
            return imageView;
        }
    }

}


