package com.example.techeye.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.techeye.R;
import com.example.techeye.subscriber.Url.URLS;
import com.example.techeye.subscriber.viewalldetails.ViewPropertyDetails;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendMessage extends AppCompatActivity {

    Spinner spinner_subject;
    String[] subject = {"Select Subject","Technical Issue","Land Issue","Application Issue","Others"};

    EditText edit_Name,edit_MobileNo,edit_message;
    TextView edit_email;
    String str_subject,id,emailid;
    Button btn_SupportRequest;
    RelativeLayout rl_layout;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_send_message);

        edit_Name = findViewById (R.id.subname);
        edit_MobileNo = findViewById (R.id.submobileno);
        edit_email = findViewById (R.id.subemailid);
        edit_message = findViewById (R.id.submessage);
        btn_SupportRequest = findViewById (R.id.request);
        rl_layout = findViewById (R.id.rl_layout);

        spinner_subject = findViewById (R.id.spinner_subject);

        id = getIntent ( ).getStringExtra ("id");
        emailid = SharedPrefManager.getInstance (SendMessage.this).getUser ().getEmail ();

        edit_email.setText (emailid);


        ArrayAdapter subject_adapter = new ArrayAdapter(this,R.layout.spiner_text,subject);
        subject_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subject.setAdapter(subject_adapter);

        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);

        awesomeValidation.addValidation (SendMessage.this, R.id.subname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.name);
        awesomeValidation.addValidation (SendMessage.this,R.id.submobileno,"^[+]?[0-9]{5,15}$",R.string.mobilenoerror);
        //awesomeValidation.addValidation (SendMessage.this, R.id.subemailid, Patterns.EMAIL_ADDRESS, R.string.emailerror);

      spinner_subject.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener ( ) {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              Object item = parent.getItemAtPosition(position);
              str_subject = item.toString();

              Toast.makeText (SendMessage.this, str_subject, Toast.LENGTH_SHORT).show ( );
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });


      btn_SupportRequest.setOnClickListener (new View.OnClickListener ( ) {
          @Override
          public void onClick(View v) {

              if(!str_subject.equals ("Select Subject")){

                  sendMessage();


              }else{
                  Snackbar.make(rl_layout, "Please fill all the fields", Snackbar.LENGTH_LONG)
                          .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                          .show();
              }
          }
      });

    }

    public void sendMessage(){

        ProgressDialog dialog = new ProgressDialog (SendMessage.this);
        dialog.setMessage ("Your Request is Being Raised...");
        dialog.show ( );

        StringRequest request = new StringRequest (Request.Method.POST, URLS.storeServices, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                dialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );

                    String status = jsonObject.getString ("message");
                    if(status.equals ("Service stored")){

                        Toast.makeText (SendMessage.this, status, Toast.LENGTH_SHORT).show ( );

                        updateRequest();
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

                Toast.makeText (SendMessage.this, ""+error, Toast.LENGTH_SHORT).show ( );
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );

                params.put ("description",edit_message.getText ().toString ().trim ());
                params.put ("name",edit_Name.getText ().toString ().trim ());
                params.put ("subject",str_subject);
                params.put ("customer_phone",edit_MobileNo.getText ().toString ().trim ());
                params.put ("customer_email",emailid);
                params.put ("user_id",id);
                params.put ("status","raised");

                return params;
            }
        };
        request.setRetryPolicy (new DefaultRetryPolicy ( 50000,100,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ));
        RequestQueue requestQueue = Volley.newRequestQueue (SendMessage.this);
        requestQueue.add (request);

    }

    public void updateRequest(){

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.updateUserDetails, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject ( response );
                    String success = object.getString ("1");

                    Toast.makeText (SendMessage.this, success, Toast.LENGTH_SHORT).show ( );

                    startActivity (new Intent ( SendMessage.this, ViewPropertyDetails.class));

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText (SendMessage.this, "Update error", Toast.LENGTH_SHORT).show ( );
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );

                params.put ("id",id);
                params.put ("function","raised");

                return params;
            }
        };
        stringRequest.setRetryPolicy (new DefaultRetryPolicy ( 50000,100,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ));
        RequestQueue requestQueue = Volley.newRequestQueue (SendMessage.this);
        requestQueue.add (stringRequest);

    }
}