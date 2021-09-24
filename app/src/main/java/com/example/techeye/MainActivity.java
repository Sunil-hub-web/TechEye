package com.example.techeye;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.techeye.enabler.Enabler_SignUP.PersonalDetails_Enabler;
import com.example.techeye.enabler.Enabler_SignUP.Signin_Enabler;
import com.example.techeye.subscriber.HomePage_Subscriber;

import com.example.techeye.subscriber.SharedPrefManager;
import com.example.techeye.subscriber.Signin;
import com.example.techeye.subscriber.personal.PropertyDetails;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    RadioButton radio_Subscriber, radio_Enabler;

    Button btn_SignIn, btn_CreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createViewPager();

        btn_SignIn = findViewById(R.id.signin);
        btn_CreateAccount = findViewById(R.id.createAccount);
        radio_Subscriber = findViewById(R.id.subscriber);
        radio_Enabler = findViewById(R.id.enabler);

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radio_Subscriber.isChecked()) {

                    Intent intent = new Intent(MainActivity.this, Signin.class);
                    startActivity(intent);

                } else if (radio_Enabler.isChecked()) {

                    Intent intent = new Intent(MainActivity.this, Signin_Enabler.class);
                    startActivity(intent);

                } else {

                    Toast.makeText(MainActivity.this, "Please select radio button", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radio_Subscriber.isChecked()) {

                    Intent intent = new Intent(MainActivity.this, PropertyDetails.class);
                    startActivity(intent);

                } else if (radio_Enabler.isChecked()) {

                    Intent intent = new Intent(MainActivity.this, PersonalDetails_Enabler.class);
                    startActivity(intent);

                } else {

                    Toast.makeText(MainActivity.this, "Please select radio button", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void createViewPager() {


        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 8, 8, 8);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

   /* @Override
    protected void onStart() {
        super.onStart ( );
        if (SharedPrefManager.getInstance (MainActivity.this).isLoggedIn ()){
            Intent intent = new Intent ( MainActivity.this, HomePage_Subscriber.class );
            startActivity (intent);
        }
    }*/
}