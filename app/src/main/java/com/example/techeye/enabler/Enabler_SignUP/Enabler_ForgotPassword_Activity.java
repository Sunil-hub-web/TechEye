package com.example.techeye.enabler.Enabler_SignUP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.example.techeye.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Enabler_ForgotPassword_Activity extends AppCompatActivity {

    Button btn_SendOtp, btn_Submit;
    EditText edit_Emailid, edit_OTP, edit_Password, edit_ConfirmPassword;
    String send_mail = "https://www.rentopool.com/Thirdeye/api/auth/send-mail";
    String forgotenablerpassword = "https://www.rentopool.com/Thirdeye/api/auth/forgotenablerpassword";
    String message,email, otp, password, confPassword,token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enabler_forgot_password);

        btn_SendOtp = findViewById (R.id.sendotp);
        btn_Submit = findViewById (R.id.submit);
        edit_Emailid = findViewById (R.id.emailid);
        edit_OTP = findViewById (R.id.otp);
        edit_Password = findViewById (R.id.password);
        edit_ConfirmPassword = findViewById (R.id.confirmPassword);

        btn_SendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edit_Emailid.getText().toString().trim();

                Send_Mail(email);
            }
        });

        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = edit_OTP.getText().toString();
                email = edit_Emailid.getText().toString().trim();
                password = edit_Password.getText().toString().trim();
                confPassword = edit_ConfirmPassword.getText().toString().trim();

                if (!isValidPassword(password)){
                    Toast.makeText(Enabler_ForgotPassword_Activity.this, "Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters", Toast.LENGTH_LONG).show();
                }else if (!otp.equals(token)){
                    Toast.makeText(Enabler_ForgotPassword_Activity.this, "OTP not matched", Toast.LENGTH_SHORT).show();
                }else if (!confPassword.equals(password)){
                    Toast.makeText(Enabler_ForgotPassword_Activity.this, "Password not matched", Toast.LENGTH_SHORT).show();
                }else {
                    Forgot_Password(email,password,otp);
                }
            }
        });


    }

    private void Send_Mail(String Email){

        ProgressDialog dialog = new ProgressDialog(Enabler_ForgotPassword_Activity.this);
        dialog.setMessage("Loading");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                send_mail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    message = jsonObject.getString("message");
                    String storetoken = jsonObject.getString("storetoken");
                    JSONObject object = new JSONObject(storetoken);

                    if (message.equals("Email has been sent by rashmi")){

                        Toast.makeText(Enabler_ForgotPassword_Activity.this, "Otp has been sent to "+Email, Toast.LENGTH_SHORT).show();
                        edit_OTP.setEnabled(true);
                        edit_Password.setEnabled(true);
                        edit_ConfirmPassword.setEnabled(true);
                        edit_OTP.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edit_OTP, InputMethodManager.SHOW_IMPLICIT);

                        token = object.getString("token");

                    }else {
                        edit_OTP.setEnabled(false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<> ();
                params.put("email",Email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Enabler_ForgotPassword_Activity.this);
        requestQueue.add(stringRequest);
    }

    private void Forgot_Password(String Email, String Password, String Token ){

        ProgressDialog dialog = new ProgressDialog(Enabler_ForgotPassword_Activity.this);
        dialog.setMessage("Changing Password Please Wait...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                forgotenablerpassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.getString("message");
                    if (msg.equals("password updated")){
                        Toast.makeText(Enabler_ForgotPassword_Activity.this, "Password updated successfully ", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(Enabler_ForgotPassword_Activity.this, "Check the fields and try again", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("email",Email);
                params.put("password",Password);
                params.put("verification_key",token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Enabler_ForgotPassword_Activity.this);
        requestQueue.add(stringRequest);
    }

    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}