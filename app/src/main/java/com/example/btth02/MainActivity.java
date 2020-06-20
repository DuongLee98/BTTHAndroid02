package com.example.btth02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public ViewPager vp;
    public TabLayout tl;
    public PageAdapter pa;
    public static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pa = new PageAdapter(getSupportFragmentManager(), 3);

        vp = findViewById(R.id.viewPager);
        vp.setAdapter(pa);

        tl = findViewById(R.id.tabLayout);
        tl.setupWithViewPager(vp);

        db = new DBHelper(getBaseContext()).getReadableDatabase();
    }
}
