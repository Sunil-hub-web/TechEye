package com.example.techeye.subscriber.viewalldetails;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.techeye.R;
import com.example.techeye.subscriber.SharedPrefManager;
import com.example.techeye.subscriber.Url.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccountDetails extends AppCompatActivity {

    String email;
    String fname,lname,mobile,aadhar,passport,state,city,area,pincode,idproof_Image,image,profile_Image,image2;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<UserDetails_ModelClass> userDetails = new ArrayList<> (  );
    UserDetailsAdapter userDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_details);

        recyclerView = findViewById (R.id.userdetails_recycler);
        linearLayoutManager = new LinearLayoutManager (AccountDetails.this);

        email = SharedPrefManager.getInstance (AccountDetails.this).getUser ().getEmail ();

        ProgressDialog dialog = new ProgressDialog (AccountDetails.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();

        StringRequest request = new StringRequest(Request.Method.POST, URLS.getuserdetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(AccountDetails.this, ""+response, Toast.LENGTH_SHORT).show();
                            dialog.dismiss ();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String statues = jsonObject.getString ("Information");

                    JSONArray array = new JSONArray ( statues );

                    for(int i = 0;i<array.length ();i++){

                        JSONObject jsonObject1 = array.getJSONObject (0);

                        UserDetails_ModelClass userDetails_modelClass = new UserDetails_ModelClass (
                                jsonObject1.getString ("id"),
                                jsonObject1.getString ("name"),
                                jsonObject1.getString ("mobile_number"),
                                jsonObject1.getString ("email"),
                                jsonObject1.getString ("state"),
                                jsonObject1.getString ("city"),
                                jsonObject1.getString ("country"),
                                jsonObject1.getString ("pincode"),
                                jsonObject1.getString ("aadhar_number"),
                                jsonObject1.getString ("passport_number"),
                                jsonObject1.getString ("aadhar_image"),
                                jsonObject1.getString ("passport_image"),
                                jsonObject1.getString ("billing_address1"),
                                jsonObject1.getString ("billing_address2"),
                                jsonObject1.getString ("billing_country"),
                                jsonObject1.getString ("billing_state"),
                                jsonObject1.getString ("billing_city"),
                                jsonObject1.getString ("billing_pincode")

                        );

                        userDetails.add (userDetails_modelClass);

                        break;
                        //return;

                    }
                    Log.d ("userdetails",userDetails.toString () );

                    userDetailsAdapter = new UserDetailsAdapter (AccountDetails.this,userDetails);
                    recyclerView.setLayoutManager (linearLayoutManager);
                    recyclerView.setHasFixedSize (true);
                    recyclerView.setAdapter (userDetailsAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ();
                Toast.makeText(AccountDetails.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("email",email);

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(AccountDetails.this);
        requestQueue.add(request);



    }
}