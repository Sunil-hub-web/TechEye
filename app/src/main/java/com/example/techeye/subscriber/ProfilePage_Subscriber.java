package com.example.techeye.subscriber;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.techeye.R;
import com.example.techeye.subscriber.Url.URLS;
import com.example.techeye.subscriber.viewalldetails.AccountDetails;
import com.example.techeye.subscriber.viewalldetails.ViewPackagesDetails;
import com.example.techeye.subscriber.viewalldetails.ViewUserPropertyDetails;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import de.hdodenhof.circleimageview.CircleImageView; 


public class ProfilePage_Subscriber extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    CircleImageView circleImageView;
    public static final int IMAGE_CODE = 1;
    Uri imageUri;
    ImageView imageView;
    TextView text_UserDetails, text_PackagesDetails, text_UserName, text_MobileNumber, text_PropertyDetails,text_Logout,text_Setting,text_Notifaction;
    String name, email, mobile,id;
    String profile_Image,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_profile_page__subscriber);

        bottomNavigationView = findViewById (R.id.bottomNavigation);
        circleImageView = findViewById (R.id.profile_image);
        imageView = findViewById (R.id.imageview);
        text_UserDetails = findViewById (R.id.accountdetails);
        text_PackagesDetails = findViewById (R.id.packages);
        text_UserName = findViewById (R.id.username);
        text_MobileNumber = findViewById (R.id.mobilenumber);
        text_PropertyDetails = findViewById (R.id.property);
        text_Logout = findViewById (R.id.logout);
        text_Setting = findViewById (R.id.setting);
        text_Notifaction = findViewById (R.id.notifaction);

        //email = "sunil95dash@gmail.com";

        getProfileImage();

        name = SharedPrefManager.getInstance (ProfilePage_Subscriber.this).getUser ( ).getName ( );
        mobile = SharedPrefManager.getInstance (ProfilePage_Subscriber.this).getUser ( ).getMobileno ( );
        email = SharedPrefManager.getInstance (ProfilePage_Subscriber.this).getUser ( ).getEmail ( );
        id = SharedPrefManager.getInstance (ProfilePage_Subscriber.this).getUser ().getId ();
        text_UserName.setText (name);
        text_MobileNumber.setText (email);

        bottomNavigationView.setSelectedItemId (R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener ( ) {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId ( )) {

                    case R.id.profile:

                        //startActivity(new Intent(getApplicationContext(),DashBaord.class));
                        // overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity (new Intent (getApplicationContext ( ), HomePage_Subscriber.class));
                        overridePendingTransition (0, 0);
                        return true;

                    case R.id.about:

                        startActivity (new Intent (getApplicationContext ( ), About_Us.class));
                        overridePendingTransition (0, 0);
                        return true;
                }
                return false;
            }
        });

        circleImageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult (intent, IMAGE_CODE);
            }
        });

        imageView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult (intent, IMAGE_CODE);
            }
        });

        text_UserDetails.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (ProfilePage_Subscriber.this, AccountDetails.class);
                startActivity (intent);
            }
        });

        text_PackagesDetails.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (ProfilePage_Subscriber.this, ViewPackagesDetails.class);
                startActivity (intent);
            }
        });

        text_PropertyDetails.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (ProfilePage_Subscriber.this, ViewUserPropertyDetails.class);
                startActivity (intent);

            }
        });

        text_Logout.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                SharedPrefManager.getInstance (ProfilePage_Subscriber.this).logout ();
            }
        });

        text_Setting.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent ( ProfilePage_Subscriber.this, SettingActivity.class);
                startActivity (intent);*/
            }
        });
        text_Notifaction.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent ( ProfilePage_Subscriber.this, Notification_Subscriber.class);
                startActivity (intent);*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK &&
                data != null && data.getData ( ) != null) {

            imageUri = data.getData ( );
            circleImageView.setImageURI (imageUri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap (this.getContentResolver ( ), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream ( );
                bitmap = Bitmap.createScaledBitmap (bitmap, 500, 750, true);
                bitmap.compress (Bitmap.CompressFormat.PNG, 80, baos); //bm is the bitmap object
                byte[] img = baos.toByteArray ( );
                profile_Image = Base64.encodeToString (img, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace ( );
            }

            checkEmail (email);

        }
    }

    public void storeProfileImage(){

        ProgressDialog dialog = new ProgressDialog (ProfilePage_Subscriber.this);
        dialog.setMessage ("Storing...");
        dialog.show ( );

        StringRequest request = new StringRequest (Request.Method.POST, URLS.storeProfileDetails, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(AccountDetails.this, ""+response, Toast.LENGTH_SHORT).show();

                dialog.dismiss ( );

                try {

                    JSONObject jsonObject = new JSONObject (response);
                    String Status = jsonObject.getString ("message");

                    if(Status.equals ("Profile created")){

                        Toast.makeText (ProfilePage_Subscriber.this, "Profile Created Success Fully", Toast.LENGTH_SHORT).show ( );
                    }

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ( );
                Toast.makeText (ProfilePage_Subscriber.this, "" + error, Toast.LENGTH_SHORT).show ( );

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<> ( );
                params.put ("email",email);
                params.put ("profile_image",profile_Image);
                params.put ("user_id",id);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue (ProfilePage_Subscriber.this);
        requestQueue.add (request);

    }

    public void getProfileImage(){

        ProgressDialog dialog = new ProgressDialog (ProfilePage_Subscriber.this);
        dialog.setMessage ("Retriving...");
        dialog.show ( );

        StringRequest request = new StringRequest (Request.Method.POST, URLS.getProfileDetails, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(AccountDetails.this, ""+response, Toast.LENGTH_SHORT).show();

                dialog.dismiss ( );

                try {

                    JSONObject jsonObject = new JSONObject (response);
                    String Status = jsonObject.getString ("userInformation");

                    JSONArray array = new JSONArray ( Status );

                    for (int i = 0 ; i < array.length () ; i++){

                        JSONObject object = array.getJSONObject (0);

                        image = object.getString ("profile_image");
                    String url = "https://rentopool.com/Thirdeye/"+image;
                        Picasso.with (ProfilePage_Subscriber.this)
                                .load (url)
                                .placeholder (R.drawable.profileimage)
                                .into (circleImageView);

                    }

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ( );
                Toast.makeText (ProfilePage_Subscriber.this, "" + error, Toast.LENGTH_SHORT).show ( );

            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<> ( );
                params.put ("email",email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue (ProfilePage_Subscriber.this);
        requestQueue.add (request);

    }

    public void checkEmail(String email){

        ProgressDialog progressDialog = new ProgressDialog(ProfilePage_Subscriber.this);
        progressDialog.setMessage("Email is Avilable or Not...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.profileCheckMail, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );
                    String ststues = jsonObject.getString ("email");

                    if(ststues.equals ("1")){

                        updateProfileimage();

                    }else if(ststues.equals ("0")){

                        //Toast.makeText (ProfilePage_Subscriber.this, "Email Id is Not Avilable", Toast.LENGTH_SHORT).show ( );

                        storeProfileImage();

                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace ();
                progressDialog.dismiss ();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put ("email",email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue (ProfilePage_Subscriber.this);
        requestQueue.add (stringRequest);
    }

    public void updateProfileimage(){

        ProgressDialog progressDialog = new ProgressDialog(ProfilePage_Subscriber.this);
        progressDialog.setMessage("UpdateProfile...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.updateProfile, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );

                    String statues = jsonObject.getString ("1");
                    //String statues = jsonObject.getString ("1");

                    if(statues.equals ("You have successfully updated the details.")){

                            Toast.makeText (ProfilePage_Subscriber.this, "profile Image update Successfully", Toast.LENGTH_SHORT).show ( );
                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace ();
                progressDialog.dismiss ();

                Toast.makeText (ProfilePage_Subscriber.this, "not update", Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put ("email",email);
                params.put ("profile_image",profile_Image);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (ProfilePage_Subscriber.this);
        requestQueue.add (stringRequest);

    }

    @Override
    public void onBackPressed() {

    }
}