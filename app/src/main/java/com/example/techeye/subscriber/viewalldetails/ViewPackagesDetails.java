package com.example.techeye.subscriber.viewalldetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.techeye.subscriber.SharedPrefManager;
import com.example.techeye.subscriber.Url.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewPackagesDetails extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<PackagesDetails_ModelClass> packagesDetails = new ArrayList<> (  );
    PackagesDetailsAdapter packagesDetailsAdapter;
    String url1 = "https://www.rentopool.com/Thirdeye/api/auth/getuserbyid";
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_packages_details);

        email = SharedPrefManager.getInstance (ViewPackagesDetails.this).getUser ().getEmail ();

        recyclerView = findViewById (R.id.packagesRecycler);

        linearLayoutManager = new LinearLayoutManager (ViewPackagesDetails.this);

        ProgressDialog dialog = new ProgressDialog (ViewPackagesDetails.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();

        StringRequest request1 = new StringRequest(Request.Method.POST, URLS.getuserdetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(AccountDetails.this, ""+response, Toast.LENGTH_SHORT).show();
                dialog.dismiss ();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String statues = jsonObject.getString ("Information");

                    JSONArray array = new JSONArray ( statues );

                    for(int i = 0;i<array.length ();i++){

                        JSONObject jsonObject1 = array.getJSONObject (i);

                        PackagesDetails_ModelClass packagesDetails_modelClass = new PackagesDetails_ModelClass (
                               jsonObject1.getString ("id"),
                               jsonObject1.getString ("paymentId"),
                               jsonObject1.getString ("email"),
                               jsonObject1.getString ("service_type"),
                               jsonObject1.getString ("subscription_for"),
                               jsonObject1.getString ("paymentStatus"),
                               jsonObject1.getString ("amount")

                        );

                        packagesDetails.add (packagesDetails_modelClass);

                    }
                    Log.d ("userdetails",packagesDetails.toString () );

                    packagesDetailsAdapter = new PackagesDetailsAdapter (ViewPackagesDetails.this,packagesDetails);
                    recyclerView.setLayoutManager (linearLayoutManager);
                    recyclerView.setHasFixedSize (true);
                    recyclerView.setAdapter (packagesDetailsAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ();
                Toast.makeText(ViewPackagesDetails.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> ();
                params.put("email",email);

                return params;
            }
        };
        request1.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ViewPackagesDetails.this);
        requestQueue.add(request1);
    }
}