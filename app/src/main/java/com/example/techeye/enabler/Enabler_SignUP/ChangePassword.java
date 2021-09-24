package com.example.techeye.enabler.Enabler_SignUP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.techeye.subscriber.Confirm;
import com.example.techeye.subscriber.Forgot_password;
import com.example.techeye.subscriber.Url.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    Button  btn_Submit;
    EditText edit_Emailid, edit_OldPassword, edit_Password, edit_ConfirmPassword;
    String email, oldPassword, password, confPassword;
    ImageView imageView_Back;

    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_change_password);



        btn_Submit = findViewById (R.id.submit);
        edit_Emailid = findViewById (R.id.emailid);
        edit_OldPassword = findViewById (R.id.otp);
        edit_Password = findViewById (R.id.password);
        edit_ConfirmPassword = findViewById (R.id.confirmPassword);
        imageView_Back = findViewById (R.id.back);

        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);

        awesomeValidation.addValidation (ChangePassword.this, R.id.emailid, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation (ChangePassword.this, R.id.password, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", R.string.password);
        awesomeValidation.addValidation (ChangePassword.this, R.id.confirmPassword, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", R.string.confirmPassword);

        btn_Submit.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if(awesomeValidation.validate ()){

                    Toast.makeText (ChangePassword.this, "Validation Complete", Toast.LENGTH_SHORT).show ( );

                    if(edit_Password.getText ().toString ().trim ().equals (edit_ConfirmPassword.getText ().toString ().trim ())) {

                        email = edit_Emailid.getText ( ).toString ( ).trim ( );
                        password = edit_Password.getText ( ).toString ( ).trim ( );
                        oldPassword = edit_OldPassword.getText ( ).toString ( ).trim ( );

                        ChangePassword (email, oldPassword, password);

                    }else{
                        edit_ConfirmPassword.setError ("Password NotMatch");
                    }
                }
            }
        });

        imageView_Back.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (ChangePassword.this,Navigation_Activity.class);
                startActivity (intent);
            }
        });

    }

    public void ChangePassword(String email,String oldPassword,String newPassword){

        ProgressDialog progressDialog = new ProgressDialog(ChangePassword.this);
        progressDialog.setMessage("Password changed...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.changePassword_enabler, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );
                    String status = jsonObject.getString ("message");
                    String status1 = jsonObject.getString ("details_verified");
                    if(status.equals ("Password updated")){
                        Toast.makeText (ChangePassword.this, status, Toast.LENGTH_SHORT).show ( );
                    }else if(status1.equals ("Failed")){
                        Toast.makeText (ChangePassword.this, status1, Toast.LENGTH_SHORT).show ( );
                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss ();

                Toast.makeText (ChangePassword.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put ("email",email);
                params.put ("oldpassword",oldPassword);
                params.put ("newpassword",newPassword);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (ChangePassword.this);
        requestQueue.add (stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed ( );
    }
}