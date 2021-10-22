package com.example.techeye.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.techeye.R;
import com.example.techeye.subscriber.Url.URLS;
import com.example.techeye.subscriber.personal.Login_ModelClass;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signin extends AppCompatActivity {

    EditText edit_EmailAddress, edit_Password;
    TextView text_ForGotPassword,text_Message;
    Button btn_Submit;

    String url = "https://rentopool.com/Thirdeye/api/auth/login";
    String str_name,str_email,str_mobile,str_id,str_Aadhar_Number,str_Passport_Number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_signin);

        text_ForGotPassword = findViewById (R.id.forget);
        edit_Password = findViewById (R.id.password);
        edit_EmailAddress = findViewById (R.id.email);
        btn_Submit = findViewById (R.id.submit);
        text_Message = findViewById (R.id.message);

        btn_Submit.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (edit_EmailAddress.getText ( ).toString ( ).trim ( ).equals ("") &&
                        edit_Password.getText ( ).toString ( ).trim ( ).equals ("")) {

                    edit_EmailAddress.setError ("Enter Your Email Id");
                    edit_Password.setError ("Enter Your Password");

                } else {

                    String email = edit_EmailAddress.getText ( ).toString ( ).trim ( );
                    String password = edit_Password.getText ( ).toString ( ).trim ( );

                    userLogin (email, password);

                }

            }
        });

        text_ForGotPassword.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (Signin.this, Forgot_password.class);
                startActivity (intent);
            }
        });
    }

    public void userLogin(String email, String password) {

        ProgressDialog dialog = new ProgressDialog (Signin.this);
        dialog.setMessage ("Verify...");
        dialog.show ( );

        StringRequest request = new StringRequest (Request.Method.POST, URLS.login, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                dialog.dismiss ( );

                try {
                    JSONObject jsonObject = new JSONObject ( response );

                    String token = jsonObject.getString ("access_token");
                    String user = jsonObject.getString ("user");

                    if(token == null){

                        String error = jsonObject.getString ("error");

                        if(error.equals ("Unauthorized")){

                            Toast.makeText (Signin.this, "Emailid & Password in not Valide", Toast.LENGTH_SHORT).show ( );
                            text_Message.setText ("Emailid & Password in not Valide");

                        }

                    }else{

                       JSONObject jsonObject1 = new JSONObject ( user );

                       str_id = jsonObject1.getString ("id");
                       str_name = jsonObject1.getString ("name");
                       str_email = jsonObject1.getString ("email");
                       str_mobile = jsonObject1.getString ("mobile_number");
                       str_Aadhar_Number = jsonObject1.getString ("aadhar_number");
                       str_Passport_Number = jsonObject1.getString ("passport_number");
                       String password = edit_Password.getText ().toString ().trim ();

                        Login_ModelClass login_modelClass = new Login_ModelClass (str_id,password,str_name,str_email,str_mobile,
                                                                                    str_Aadhar_Number,str_Passport_Number);

                        SharedPrefManager.getInstance (Signin.this).userLogin (login_modelClass);

                        Toast.makeText (Signin.this, "success", Toast.LENGTH_LONG).show ( );
                        text_Message.setTextColor (Color.GREEN);
                        text_Message.setText ("Emailid & Password Valide");

                        Intent intent = new Intent(Signin.this,HomePage_Subscriber.class);
                        intent.putExtra ("password",password);
                        startActivity (intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss ( );

                //Toast.makeText (Signin.this, "error"+error, Toast.LENGTH_LONG).show ( );
                text_Message.setText ("Emailid & Password in not Valide");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<> ( );

                params.put ("email", email);
                params.put ("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue (Signin.this);
        requestQueue.add (request);

    }
}