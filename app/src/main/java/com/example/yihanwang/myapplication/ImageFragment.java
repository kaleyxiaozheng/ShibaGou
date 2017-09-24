package com.example.yihanwang.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageInfo;
import com.example.yihanwang.myapplication.entities.ScoreRecord;
import com.example.yihanwang.myapplication.entities.TextProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class ImageFragment extends Fragment {
    public static int id = 1;
    private ViewPager viewPager;
    private Button picture;
    private Button infoBtn;
    private TextView score;
    private int currentPosition;
    ImagePagerAdapter customSwip;
    private ProgressBar bar;
    private int progress = 0;
    int myProgress = 0;
    TextProgressBar pb;
    private List<ImageInfo> oneImage = new ArrayList<>();

    View view;
    private List<ImageInfo> images = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.image_fragment, container, false);

        Number maxIdRecord = Realm.getDefaultInstance().where(ScoreRecord.class).max("id");
        if(maxIdRecord != null) {
            ImageFragment.id = maxIdRecord.intValue() + 1;
        }

        Bundle args = getArguments();
        double lat = args.getDouble("location_lat");
        double lon = args.getDouble("location_lon");

        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        this.images = ImageStorage.getInstance().getImagesFromLocation(lat, lon);

        double imageId = args.getDouble("selected_image_id");
        if (imageId != 0.0) {
            for(int i=0; i<this.images.size(); i++){
                if(this.images.get(i).getId() == imageId){
                    currentPosition = i;
                    oneImage.add(images.get(i));
                    Log.i("image", "show selected image " + imageId);
                    viewPager.setCurrentItem(currentPosition);
                    customSwip = new ImagePagerAdapter(getActivity(), oneImage);
                    break;
                }
            }
//            if(oneImage.isEmpty()){
//                viewPager.setCurrentItem(0);
//                customSwip = new ImagePagerAdapter(getActivity(), images);
//            }
        }

        viewPager.setAdapter(customSwip);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

        });

        Realm realm = Realm.getDefaultInstance();
        int total = 0;

        realm.beginTransaction();
        RealmResults<ScoreRecord> results = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();

        for (ScoreRecord score : results) {
            total = total + score.getScore();
        }
        realm.commitTransaction();

        //score = (TextView) view.findViewById(R.id.yourScore);

        //score.setText(String.valueOf(total));

       // pb = (TextProgressBar) view.findViewById(R.id.pb);
        pb = new TextProgressBar(getActivity());
        //pb = (TextProgressBar)view.findViewById(R.id.pb);
        int level = ScoreUtils.getCurrentLevel(total);
        int levelScore = ScoreUtils.getCurrentLevel(total);
        int displayProgress = total/ScoreUtils.getNextLevelImageNumber(total)*100;
        pb.setText(total + "/" + levelScore);
        pb.setProgress(total);
        pb.setMax(levelScore);
        picture = (Button) view.findViewById(R.id.takePhoto);
        picture.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });


        infoBtn = (Button) view.findViewById(R.id.plantInfo);
        infoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new InfoFragment();
                Bundle args = new Bundle();
                ImageInfo imageInfo = images.get(currentPosition);

                if (imageInfo != null) {
                    args.putDouble("id", imageInfo.getId());
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, fragment).addToBackStack(InfoFragment.class.getName()).commit();
                }
            }
        });
        return view;
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
        String root = getContext().getExternalCacheDir().getAbsolutePath();
        File myDir = new File(root, "saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            ImageInfo imageInfo = images.get(currentPosition);
            ImageGalleryStorage.getInstance().addImageGallery(imageInfo.getId(), file.getPath());
            viewPager.setAdapter(null);
            viewPager.setAdapter(customSwip);
            viewPager.setCurrentItem(currentPosition);

            int result = 0;
            RealmResults<ScoreRecord> total = Realm.getDefaultInstance().where(ScoreRecord.class).findAll();
            for (ScoreRecord score : total) {
                //Log.i("score", "score " + score.getCurrentScores());
                result += score.getScore();
            }

            score.setText(String.valueOf(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    public String getLevel(int total) {
//        String title = "";
//
//        for (int i = 0; i < 20; i++) {
//            if (total < las.LEVEL_SCORE[i]) {
//                if (i == 0) {
//                    title = "0";
//                    if (!title.isEmpty()) {
//                        return title;
//                    }
//                } else {
//                    title = las.LEVEL_TITLE[i - 1];
//                    if (!title.isEmpty()) {
//                        return title;
//                    }
//                }
//            }
//        }
//        return title;
//    }

}

