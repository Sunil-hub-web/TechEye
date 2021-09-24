package com.example.techeye.subscriber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.techeye.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.joanzapata.pdfview.PDFView;

public class ViewPdfFile extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf_file);

        pdfView = findViewById(R.id.pdf);
        pdfView.fromAsset ("Android.pdf")
                .showMinimap(false)
                .enableSwipe(true)
                .load();

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setSelectedItemId(R.id.pdf);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.click :
                        //startActivity(new Intent(getApplicationContext(),DashBaord.class));
                        //overridePendingTransition(0,0);
                        //return true;

                    case R.id.home :
                        startActivity(new Intent(getApplicationContext(), HomePage_Subscriber.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.pdf :
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}