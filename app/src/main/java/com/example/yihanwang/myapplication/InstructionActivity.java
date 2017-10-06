package com.example.yihanwang.myapplication;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by Kaley on 5/10/17.
 */

public class InstructionActivity extends Activity {

    private TextView mess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instruction_activity);

        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "teen.ttf");

        mess = (TextView) findViewById(R.id.message);

        mess.setText("➤ Click on a plant to take a photo \n\n ➤ Hold the plant to skip it");
        mess.setTypeface(font);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .4));

    }
}
