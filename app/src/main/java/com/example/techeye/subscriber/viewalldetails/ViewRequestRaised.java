package com.example.techeye.subscriber.viewalldetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.example.techeye.subscriber.SendMessage;
import com.example.techeye.subscriber.Url.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewRequestRaised extends AppCompatActivity {

    TextView text_Name,text_MobileNo,text_EmailId,text_Status,text_Subject,text_Description;
    String id,str_Name,str_MobileNo,str_EmailId,str_Status,str_Subject,str_Description,str_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_request_raised);

        text_Name = findViewById (R.id.raisedname);
        text_MobileNo = findViewById (R.id.raisedmobileno);
        text_EmailId = findViewById (R.id.raisedemailid);
        text_Status = findViewById (R.id.raisedstatues);
        text_Subject = findViewById (R.id.raisedsubject);
        text_Description = findViewById (R.id.raiseddescription);

        id = getIntent ( ).getStringExtra ("id");

        getRaisedRequest (id);


    }

    public void getRaisedRequest(String userId){

        ProgressDialog dialog = new ProgressDialog (ViewRequestRaised.this);
        dialog.setMessage ("Your Request is Being Raised...");
        dialog.show ( );

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.getServices, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                dialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );
                    String status = jsonObject.getString ("Information");
                    JSONArray array = new JSONArray ( status );

                    for(int i=0;i<array.length ();i++){

                    JSONObject jsonObject1 = array.getJSONObject (0);

                    str_id = jsonObject1.getString ("id");
                    str_Name = jsonObject1.getString ("name");
                    str_EmailId = jsonObject1.getString ("customer_email");
                    str_MobileNo = jsonObject1.getString ("customer_phone");
                    str_Description = jsonObject1.getString ("description");
                    str_Status = jsonObject1.getString ("status");
                    str_Subject = jsonObject1.getString ("subject");

                        text_Name.setText (str_Name);
                        text_EmailId.setText (str_EmailId);
                        text_MobileNo.setText (str_MobileNo);
                        text_Description.setText (str_Description);
                        text_Status.setText (str_Status);
                        text_Subject.setText (str_Subject);

                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss ();

                Toast.makeText (ViewRequestRaised.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );

                params.put ("user_id",id);

                return params;
            }
        };

        stringRequest.setRetryPolicy (new DefaultRetryPolicy ( 50000,100,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ));
        RequestQueue requestQueue = Volley.newRequestQueue (ViewRequestRaised.this);
        requestQueue.add (stringRequest);
    }
}