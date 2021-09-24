package com.example.techeye.subscriber.viewalldetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.techeye.R;
import com.example.techeye.subscriber.SharedPrefManager;
import com.example.techeye.subscriber.Url.URLS;
import com.example.techeye.subscriber.personal.PropertyDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewPropertyDetails extends AppCompatActivity {

    RecyclerView recycler;
    ArrayList<Property_ModelClass> propertyList;
    String email,message,password;

    Button btn_AddProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
           super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_property_details);

        recycler = findViewById (R.id.recyclerProperty);

        btn_AddProperty = findViewById (R.id.propertyadd);

        Intent intent = getIntent ();
        message = intent.getStringExtra ("message");
        password = intent.getStringExtra ("password");

        if(message != null){
            btn_AddProperty.setVisibility (View.VISIBLE);
        }else{
            btn_AddProperty.setVisibility (View.INVISIBLE);
        }

        btn_AddProperty.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent ( ViewPropertyDetails.this, PropertyDetails.class );
                intent1.putExtra ("message","AddProperty");
                intent1.putExtra ("password",password);
                startActivity (intent1);
            }
        });

        email = SharedPrefManager.getInstance (ViewPropertyDetails.this).getUser ( ).getEmail ( );

        recycler.setHasFixedSize (true);
        recycler.setLayoutManager (new LinearLayoutManager (ViewPropertyDetails.this));
        propertyList = new ArrayList<> ( );
        GetProperty ( );

    }

    private void GetProperty() {

        ProgressDialog dialog = new ProgressDialog (ViewPropertyDetails.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.getuserbyid + email,
                new Response.Listener<String> ( ) {
                    @Override
                    public void onResponse(String response) {

                        dialog.dismiss ();

                        try {
                            JSONObject jsonObject = new JSONObject (response);
                            String Information = jsonObject.getString ("Information");
                            JSONArray array = new JSONArray (Information);
                            for (int i = 0; i < array.length ( ); i++) {

                                JSONObject object = array.getJSONObject (i);

                                Property_ModelClass property_modelClass = new Property_ModelClass (

                                        object.getString ("id"),
                                        object.getString ("name"),
                                        object.getString ("mobile_number"),
                                        object.getString ("mobile1"),
                                        object.getString ("email"),
                                        object.getString ("reason_for_third_eye"),
                                        object.getString ("plot_size"),
                                        object.getString ("country"),
                                        object.getString ("state"),
                                        object.getString ("city"),
                                        object.getString ("pincode"),
                                        object.getString ("paymentId"),
                                        object.getString ("paymentStatus"),
                                        object.getString ("aadhar_number"),
                                        object.getString ("passport_number"),
                                        object.getString ("billing_address1"),
                                        object.getString ("billing_address2"),
                                        object.getString ("billing_country"),
                                        object.getString ("billing_state"),
                                        object.getString ("billing_city"),
                                        object.getString ("billing_pincode"),
                                        object.getString ("service_type"),
                                        object.getString ("subscription_for"),
                                        object.getString ("declaration"),
                                        object.getString ("address1"),
                                        object.getString ("amount"),
                                        object.getString ("carpet_area"),
                                        object.getString ("function")

                                );



                                propertyList.add (property_modelClass);
                            }

                            if(message != null){
                                ViewPropertyAdapter viewadapter = new ViewPropertyAdapter (ViewPropertyDetails.this, propertyList);
                                recycler.setAdapter (viewadapter);
                            }else{
                                PropertyAdapter adapter = new PropertyAdapter (ViewPropertyDetails.this, propertyList);
                                recycler.setAdapter (adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace ( );
                        }

                    }
                }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ();
                 error.printStackTrace ();
                Toast.makeText (ViewPropertyDetails.this, "Property Details not Retrive", Toast.LENGTH_SHORT).show ( );
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue (ViewPropertyDetails.this);
        requestQueue.add (stringRequest);
    }

}