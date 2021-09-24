package com.example.techeye.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.techeye.R;
import com.example.techeye.enabler.Enabler_SignUP.ChangePassword;
import com.example.techeye.subscriber.Url.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgot_password extends AppCompatActivity {

    Button btn_SendOtp, btn_Submit;
    EditText edit_Emailid, edit_OTP, edit_Password, edit_ConfirmPassword;
    String url = "https://rentopool.com/Thirdeye/api/auth/forgotpassword";
    String email, otp, password, confPassword;

    AwesomeValidation awesomeValidation, awesomeValidation1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_forgot_password);

        btn_SendOtp = findViewById (R.id.sendotp);
        btn_Submit = findViewById (R.id.submit);
        edit_Emailid = findViewById (R.id.emailid);
        edit_OTP = findViewById (R.id.otp);
        edit_Password = findViewById (R.id.password);
        edit_ConfirmPassword = findViewById (R.id.confirmPassword);

        edit_OTP.setEnabled (false);
        edit_Password.setVisibility (View.GONE);
        edit_ConfirmPassword.setVisibility (View.GONE);

        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);
        awesomeValidation1 = new AwesomeValidation (ValidationStyle.BASIC);

        awesomeValidation1.addValidation (Forgot_password.this, R.id.emailid, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation (Forgot_password.this, R.id.password, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", R.string.password);
        awesomeValidation.addValidation (Forgot_password.this, R.id.confirmPassword, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", R.string.confirmPassword);

        btn_SendOtp.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (awesomeValidation1.validate ( )) {

                    email = edit_Emailid.getText ( ).toString ( ).trim ( );

                    emailVerification (email);


                }
            }
        });

        btn_Submit.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {


                    if (edit_OTP.getText ( ).toString ( ).trim ( ).length ( ) == 6) {

                        Toast.makeText (Forgot_password.this, "Validation Success", Toast.LENGTH_SHORT).show ( );

                        if(edit_Password.getText ().toString ().trim ().equals (edit_ConfirmPassword.getText ().toString ().trim ())){

                            email = edit_Emailid.getText ( ).toString ( ).trim ( );
                            otp = edit_OTP.getText ( ).toString ( ).trim ( );
                            password = edit_Password.getText ( ).toString ( ).trim ( );
                            confPassword = edit_ConfirmPassword.getText ( ).toString ( ).trim ( );

                            Toast.makeText (Forgot_password.this, "Pasword Match", Toast.LENGTH_SHORT).show ( );

                            forgotPassword (email,password,otp);
                        }else{
                            Toast.makeText (Forgot_password.this, "Pasword Not Match", Toast.LENGTH_SHORT).show ( );
                        }

                    } else {
                        Toast.makeText (Forgot_password.this, "Validation not success", Toast.LENGTH_SHORT).show ( );
                    }
                }
        });
    }

    public void forgotPassword(String email, String password, String verification_key) {

        ProgressDialog dialog = new ProgressDialog (Forgot_password.this);
        dialog.setMessage ("Update...");
        dialog.show ( );

        StringRequest request = new StringRequest (Request.Method.POST, url, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                dialog.dismiss ( );

                try {
                    JSONObject jsonObject = new JSONObject ( response );
                    String statues = jsonObject.getString ("message");

                    if(statues.equals ("password updated")){
                        Toast.makeText (Forgot_password.this, "Password Update Successfully", Toast.LENGTH_SHORT).show ( );
                    }else{}

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }


                Toast.makeText (Forgot_password.this, "" + response, Toast.LENGTH_LONG).show ( );

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss ( );

                Toast.makeText (Forgot_password.this, "" + error, Toast.LENGTH_LONG).show ( );

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<> ( );
                params.put ("email", email);
                params.put ("password", password);
                params.put ("verification_key", verification_key);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue (Forgot_password.this);
        requestQueue.add (request);
    }

    public void emailVerification(String email){

        ProgressDialog dialog = new ProgressDialog (Forgot_password.this);
        dialog.setMessage ("Otp Send Youe Email...");
        dialog.show ();

        StringRequest request = new StringRequest (Request.Method.POST, URLS.EmailOtp, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {


                dialog.dismiss ( );
                try{
                JSONObject jsonObject = new JSONObject ( response );
                String statues = jsonObject.getString ("message");
                if(statues.equals ("Email has been sent by rashmi")){

                    String storetoken = jsonObject.getString ("storetoken");

                    JSONObject object = new JSONObject ( storetoken );

                    String token = object.getString ("token");

                    edit_OTP.setText (token);
                    edit_Password.setVisibility (View.VISIBLE);
                    edit_ConfirmPassword.setVisibility (View.VISIBLE);

                    Toast.makeText (Forgot_password.this, "Otp Send SuccessFully : "+response, Toast.LENGTH_LONG).show ( );


                }
            } catch (JSONException e) {
                e.printStackTrace ( );
            }



            }

        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ();

                Toast.makeText (Forgot_password.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put("email",email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue (Forgot_password.this);
        requestQueue.add (request);


    }

}