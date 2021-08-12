package com.btcsc.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity5 extends AppCompatActivity {
    BottomNavigationView  bottom;
    Toolbar toolbar;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        anhxa();
        setSupportActionBar(toolbar);
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                ShowFragment(item.getItemId());
                return true;
            }
        });
        ShowFragment(R.id.mnuPast);
    }

    private void ShowFragment(int itemId) {
        Fragment fragment = null;
        switch (itemId){
            case R.id.mnuPast:
                fragment = new PastFragment();
                break;
            case R.id.mnuToday:
                fragment = new TodayFragment();
                break;
            case R.id.mnuTomorrow:
                fragment = new TomorrowFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment,"TAG").commit();
    }

    void  anhxa()
    {
        bottom = findViewById(R.id.bottom);
        toolbar = findViewById(R.id.toolbarMain5);
        backButton = toolbar.findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}