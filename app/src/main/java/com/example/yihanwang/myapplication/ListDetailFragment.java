package com.example.yihanwang.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ListDetailFragment extends AppCompatActivity {
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_detail_fragment);
        TextView tv = (TextView)findViewById(R.id.plantText);
        Bundle mBundle = getIntent().getExtras();
        mToolbar = (Toolbar)findViewById(R.id.toolbar2);

        if (mBundle != null)
        {
            mToolbar.setTitle("The Information of " + mBundle.getString("PlantName"));
            tv.setText( mBundle.getString("Description"));
        }

    }
}
