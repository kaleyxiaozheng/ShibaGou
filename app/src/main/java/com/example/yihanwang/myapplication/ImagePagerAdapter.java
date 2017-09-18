package com.example.yihanwang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageGallery;
import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.entities.ScoreRecord;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ImagePagerAdapter extends PagerAdapter implements GestureDetector.OnGestureListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private final List<ImageInfo> images = new ArrayList<>();
    private final GestureDetector gestureDetector;
    private Context ctx;
    private LayoutInflater layoutInflater;
    private int currentSelectedGalaryIdx = -1;
    private ImageInfo selectedImage = null;
    private double imageId;

    public ImagePagerAdapter(Context c, List<ImageInfo> imagesFromLocation) {
        ctx = c;
        this.images.addAll(imagesFromLocation);
        gestureDetector = new GestureDetector(ctx, this);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = layoutInflater.inflate(R.layout.image_swipe, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.swip_image_view);

        final TextView textView = (TextView) itemView.findViewById(R.id.imageCount);
        textView.setText("Image :" + (position + 1) + "/" + images.size());
        textView.setText((position + 1) + "/" + images.size());

        final TextView count = (TextView) itemView.findViewById(R.id.imageCount);
        final TextView name = (TextView) itemView.findViewById(R.id.imageName);
        final ImageInfo imageInfo = images.get(position);

        imageId = imageInfo.getId();

        count.setText("Image :" + (position + 1) + "/" + images.size());
        count.setText((position + 1) + "/" + images.size());

        String commonName = imageInfo.getCommonName();
        String sciencename = imageInfo.getName();

        if (!commonName.isEmpty()) {
            name.setText(commonName + " (" + sciencename + ")");
        } else {
            name.setText(sciencename);
        }

        final ImageGallery imageGalery = ImageGalleryStorage.getInstance().getImageGallery(imageId);
        if (imageGalery != null) {
            int viewIds[] = {R.id.compare_image_view1, R.id.compare_image_view2, R.id.compare_image_view3};
            if (imageGalery.getImageCount() > 0) {
                View galeryContainer = itemView.findViewById(R.id.image_galery_container);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) galeryContainer.getLayoutParams();
                layoutParams.weight = 1;
            }
            for (int i = 0; i < viewIds.length; i++) {
                Bitmap image = imageGalery.getImage(i);
                if (image == null) {
                    break;
                }
                final int imageGalleryIdx = i;
                ImageView compare = (ImageView) itemView.findViewById(viewIds[i]);
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
        }
        imageView.setImageDrawable(ImageStorage.getInstance().getDrawable(ctx.getAssets(), imageInfo));
        container.addView(itemView);

        //AnimateHorizontalProgressBar progressBar = (AnimateHorizontalProgressBar)itemView.findViewById(R.id.progressBar);
        ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        //progressBar.setMax(1000);
        //progressBar.setProgress(400);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("view", "destory item");
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

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
//                            onSwipeRight();
                    } else {
//                            onSwipeLeft();
                    }
                    result = true;
                }
            } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
//                        onSwipeBottom();
                } else {
//                        onSwipeTop();
                    Log.i("guesture", "swipe top");
                    final ImageGallery imageGalery = ImageGalleryStorage.getInstance().getImageGallery(this.selectedImage.getId());
                    imageGalery.removeImage(this.currentSelectedGalaryIdx);


                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    RealmResults<ScoreRecord> results = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();

                    for (final ScoreRecord record : results) {
                        if (record.getImageId() == imageId) {
                            record.deleteFromRealm();
                            break;
                        }
                    }
                    realm.commitTransaction();

                    notifyDataSetChanged();
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    private void showComparisonActivity() {
        Intent intent = new Intent(this.ctx, ComparisonActivity.class);
        Bundle b = new Bundle();
        b.putDouble("firstImage", this.selectedImage.getId());
        b.putInt("secondImage", this.currentSelectedGalaryIdx);
        intent.putExtras(b);
        this.ctx.startActivity(intent);
    }



}