package com.example.techeye.subscriber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.techeye.R;

import com.example.techeye.subscriber.viewalldetails.ViewPropertyDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage_Subscriber extends AppCompatActivity {

    Button button1,button2,button3,button4,button5,button6;
    BottomNavigationView bottomNavigationView;
    String password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page__subscriber);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);

        password = getIntent ().getStringExtra ("password");

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.profile :

                        startActivity(new Intent(getApplicationContext(),ProfilePage_Subscriber.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home :
                        return true;

                    case R.id.about :

                        startActivity(new Intent(getApplicationContext(), About_Us.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage_Subscriber.this,ProfilePage_Subscriber.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( HomePage_Subscriber.this, ViewPropertyDetails.class );
                startActivity (intent);

            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage_Subscriber.this, ViewPdfFile.class);
                startActivity(intent);

            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage_Subscriber.this, About_Us.class);
                startActivity(intent);

            }
        });

        button6.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomePage_Subscriber.this, ViewPropertyDetails.class);
                intent.putExtra ("message","ADDProperty");
                intent.putExtra ("password",password);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}