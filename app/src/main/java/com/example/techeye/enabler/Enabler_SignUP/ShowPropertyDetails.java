package com.example.techeye.enabler.Enabler_SignUP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowPropertyDetails extends AppCompatActivity {

    TextView text_UserId,text_Propertyid,text_Address,text_Coordinetes,text_EnablerJobId;
    String str_UserId,str_Propertyid,str_Address,str_Coordinetes,propertyId,str_EnablerJobId;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_show_property_details);

        propertyId = getIntent ().getStringExtra ("propertyid");
        str_EnablerJobId = getIntent ().getStringExtra ("id");

        text_UserId = findViewById (R.id.viewuserid);
        text_Propertyid = findViewById (R.id.viewpropertyid);
        text_Address = findViewById (R.id.viewaddress);
        text_Coordinetes = findViewById (R.id.viewcoordinetes);
        linearLayout = findViewById (R.id.geofence);
        text_EnablerJobId = findViewById (R.id.jobid);

        getPropertyDetails (propertyId);

        linearLayout.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( ShowPropertyDetails.this,ShowPropertyCoordinetes.class );
                intent.putExtra ("coordinete",str_Coordinetes);
                intent.putExtra ("id",str_EnablerJobId);
                intent.putExtra ("str_UserId",str_UserId);
                startActivity (intent);

            }
        });
    }

    public void getPropertyDetails(String propertyid){

        ProgressDialog dialog = new ProgressDialog (ShowPropertyDetails.this);
        dialog.setMessage ("Retriving...");
        dialog.show ( );

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.getEnablerPropertyDetails, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                dialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );
                    String statues = jsonObject.getString ("allProperty");
                    JSONArray array = new JSONArray ( statues );

                    for(int i=0;i<array.length ();i++){

                        JSONObject jsonObject1 = array.getJSONObject (0);

                        str_UserId = jsonObject1.getString ("user_id");
                        str_Propertyid = jsonObject1.getString ("id");
                        str_Address = jsonObject1.getString ("address");
                        str_Coordinetes = jsonObject1.getString ("co_ordinates");

                        text_UserId.setText (str_UserId);
                        text_Propertyid.setText (str_Propertyid);
                        text_Address.setText (str_Address);
                        text_Coordinetes.setText (str_Coordinetes);
                        text_EnablerJobId.setText (str_EnablerJobId);

                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

               dialog.dismiss ();
                Toast.makeText (ShowPropertyDetails.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );

                params.put ("property_id",propertyid);
                return params;
            }
        };

        stringRequest.setRetryPolicy (new DefaultRetryPolicy ( 50000,100,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ));
        RequestQueue requestQueue = Volley.newRequestQueue (ShowPropertyDetails.this);
        requestQueue.add (stringRequest);

    }
}