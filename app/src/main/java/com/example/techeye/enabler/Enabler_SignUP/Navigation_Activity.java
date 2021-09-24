package com.example.techeye.enabler.Enabler_SignUP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.techeye.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class Navigation_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mydrawer;
    private ActionBarDrawerToggle mytoggle;
    NavigationView navigationView;
    TextView nav_home,nav_profile,nav_setting,nav_logout,changepassword_submenu,viewuserprofile;
    LinearLayout linear_nav_profile,linear_nav_setting;
    Button btn_Request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_navigation);

        getSupportActionBar ().setTitle ("");

        mydrawer = (DrawerLayout) findViewById(R.id.mydrwaer);
        navigationView = findViewById(R.id.navigationview);
        btn_Request = findViewById (R.id.button2);

        navigationView.setNavigationItemSelectedListener(this);
        mytoggle = new ActionBarDrawerToggle(this, mydrawer, R.string.open, R.string.close);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable (Color.parseColor("#232227")));

        View header = navigationView.getHeaderView(0);
        nav_home = header.findViewById (R.id.nav_home);
        nav_profile = header.findViewById (R.id.nav_profile);
        nav_logout = header.findViewById (R.id.nav_logout);
        linear_nav_profile = header.findViewById (R.id.linear_nav_profile);
        nav_setting = header.findViewById (R.id.nav_setting);
        linear_nav_setting = header.findViewById (R.id.linear_nav_setting);
        changepassword_submenu = header.findViewById (R.id.changepassword_submenu);
        viewuserprofile = header.findViewById (R.id.viewuserprofile);

        changepassword_submenu.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                linear_nav_setting.setVisibility (View.GONE);
                nav_setting.setBackgroundColor (getResources ().getColor (R.color.nav_background));
                mydrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent ( Navigation_Activity.this,ChangePassword.class );
                startActivity (intent);

            }
        });

        viewuserprofile.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                linear_nav_profile.setVisibility (View.GONE);
                nav_profile.setBackgroundColor (getResources ().getColor (R.color.nav_background));
                mydrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent ( Navigation_Activity.this,UserProfileDetails.class );
                startActivity (intent);

            }
        });

        nav_profile.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (linear_nav_profile.getVisibility ()==View.VISIBLE){
                    linear_nav_profile.setVisibility (View.GONE);
                    nav_profile.setBackgroundColor (getResources ().getColor (R.color.nav_color));
                    nav_setting.setBackgroundColor (getResources ().getColor (R.color.nav_background));

                }else {
                    linear_nav_profile.setVisibility (View.VISIBLE);
                    linear_nav_setting.setVisibility (View.GONE);
                    nav_setting.setBackgroundColor (getResources ().getColor (R.color.nav_background));
                    nav_profile.setBackgroundResource(R.drawable.nav_round);
                }
            }
        });

        nav_setting.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (linear_nav_setting.getVisibility ()==View.VISIBLE){
                    linear_nav_setting.setVisibility (View.GONE);
                    nav_setting.setBackgroundColor (getResources ().getColor (R.color.nav_background));

                }else {
                    linear_nav_setting.setVisibility (View.VISIBLE);
                    linear_nav_profile.setVisibility (View.GONE);
                    nav_profile.setBackgroundColor (getResources ().getColor (R.color.nav_background));
                    //nav_setting.setBackgroundColor (getResources ().getColor (R.color.nav_color));
                    nav_setting.setBackgroundResource(R.drawable.nav_round);
                }
            }
        });

        nav_home.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                nav_home.setBackgroundResource(R.drawable.navigation);
            }
        });
        nav_logout.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                nav_logout.setBackgroundResource(R.drawable.navigation);

            }
        });


        btn_Request.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( Navigation_Activity.this,EnablerJobRequest.class );
                startActivity (intent);
            }
        });

        mydrawer.addDrawerListener(mytoggle);
        mytoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mytoggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (mytoggle.onOptionsItemSelected(item)) {
            return true;
        }




        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        mydrawer.closeDrawer(GravityCompat.START);

        return true;
    }
}