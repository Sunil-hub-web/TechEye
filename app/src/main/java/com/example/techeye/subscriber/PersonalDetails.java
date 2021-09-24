package com.example.techeye.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalDetails extends AppCompatActivity {

    Button btn_next;
    EditText edit_Password, edit_Confpassword, edit_Emailid, edit_Mobileno, edit_wNumber;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    private String password;
    //defining AwesomeValidation object
    private AwesomeValidation awesomeValidation;
    TextView text_check;
    String message = "Password Do not Match!";
    String message1 = "Password Match";
    private String mobile = "^[+1-9]{0,3}[0-9]{0,15}$";
    String reasonoftecheye,property_size,Country,State,City,Pincode,
            message_add,password_add,email_add,token,mobile_No,property_type;
    RelativeLayout rl_layout;
    String url = "https://www.rentopool.com/Thirdeye/api/auth/send-mail-trap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_personal_details);


        btn_next = findViewById (R.id.pnext);
        rl_layout = findViewById (R.id.rl_layout);

        edit_Password = findViewById (R.id.password);
        edit_Confpassword = findViewById (R.id.confpassword);
        edit_Mobileno = findViewById (R.id.pmobileno);
        edit_Emailid = findViewById (R.id.emailaddress);
        edit_wNumber = findViewById (R.id.whatesapp);
        text_check = findViewById (R.id.check);

        checkBox1 = findViewById (R.id.checkBox1);
        checkBox2 = findViewById (R.id.checkBox2);
        checkBox3 = findViewById (R.id.checkBox3);
        checkBox4 = findViewById (R.id.checkBox4);


        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);

        awesomeValidation.addValidation (PersonalDetails.this,R.id.pmobileno,"^[+]?[0-9]{5,15}$",R.string.mobilenoerror);
        //awesomeValidation.addValidation (PersonalDetails_Enabler.this,R.id.whatesapp,"^[+]?[0-9]{5,15}$",R.string.mobilenoerror);
        awesomeValidation.addValidation (PersonalDetails.this, R.id.emailaddress, Patterns.EMAIL_ADDRESS, R.string.emailerror);

        try {
            reasonoftecheye = getIntent().getStringExtra("reasonoftecheye");
            property_size = getIntent().getStringExtra("property_size");
            Country = getIntent().getStringExtra("Country");
            State = getIntent().getStringExtra("State");
            City = getIntent().getStringExtra("City");
            Pincode = getIntent().getStringExtra("Pincode");
            message_add = getIntent().getStringExtra("message");
            property_type = getIntent().getStringExtra("property_type");
        }catch (Exception e){
            e.printStackTrace();
        }

        if(message_add !=null){

            email_add = SharedPrefManager.getInstance (PersonalDetails.this).getUser ().getEmail ();
            mobile_No = SharedPrefManager.getInstance (PersonalDetails.this).getUser ().getMobileno ();
            password_add = SharedPrefManager.getInstance (PersonalDetails.this).getUser ().getPassword ();
            edit_Confpassword.setText (password_add);
            edit_Password.setText (password_add);
            edit_Emailid.setText (email_add);
            edit_Mobileno.setText (mobile_No);


            checkBox1.setChecked (true);
            checkBox2.setChecked (true);
            checkBox3.setChecked (true);
            checkBox4.setChecked (true);

        }else if(message_add == null){

            edit_Confpassword.setText ("");
            edit_Password.setText ("");
            edit_Emailid.setText ("");
        }

        edit_Password.addTextChangedListener (new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (edit_Password.getText ( ).toString ( ).trim ( ).length ( ) >= 8 ) {


                    checkBox3.setChecked (true);
                    text_check.setText ("");

                } else {

                    checkBox3.setChecked (false);
                    text_check.setText (message);
                    text_check.setTextColor (Color.RED);
                    Snackbar.make(findViewById(R.id.rl_layout), "Password must contain 8 characters", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();

                }

                if (isValidNumericPassword (edit_Password.getText ( ).toString ( ))) {

                    checkBox1.setChecked (true);
                    text_check.setText ("");

                } else {

                    checkBox1.setChecked (false);
                    text_check.setText (message);
                    text_check.setTextColor (Color.RED);
                }

                if (isValidUppercasePassword (edit_Password.getText ( ).toString ( )) &&
                        isValidLowercasePassword (edit_Password.getText ( ).toString ( ))) {

                    checkBox2.setChecked (true);
                    text_check.setText ("");

                } else {

                    checkBox2.setChecked (false);
                    text_check.setText (message);
                    text_check.setTextColor (Color.RED);
                }

                if (isValidSpeialePassword (edit_Password.getText ( ).toString ( ))) {

                    checkBox4.setChecked (true);
                    text_check.setText ("");

                } else {
                    checkBox4.setChecked (false);
                    text_check.setText (message);
                    text_check.setTextColor (Color.RED);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        edit_Confpassword.addTextChangedListener (new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (edit_Confpassword.getText ( ).toString ( ).trim().length ( ) >= 8 &&
                        edit_Confpassword.getText ( ).toString ( ).trim ( ).equals (edit_Password.getText ( ).toString ( ).trim ())) {

                    text_check.setText (message1);
                    text_check.setTextColor (Color.GREEN);

                } else {
                    if (edit_Confpassword.getText().toString().length()<=8){
                        text_check.setText ("Password must contain 8 charaters");
                        text_check.setTextColor (Color.RED);
                    }else {
                        text_check.setText (message);
                        text_check.setTextColor (Color.RED);
                    }

                }

            }
        });

        btn_next.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                String email = edit_Emailid.getText ().toString ().trim ();

                if (awesomeValidation.validate ( )) {

                    if (checkBox1.isChecked ( ) && checkBox2.isChecked ( ) && checkBox3.isChecked ( ) && checkBox4.isChecked ( ) &&
                        edit_Password.getText().toString().trim().equals(edit_Confpassword.getText().toString().trim())) {

                        if(message_add !=null){

                            Intent intent = new Intent ( PersonalDetails.this,Subscription.class );

                            intent.putExtra ("reasonoftecheye",reasonoftecheye);
                            intent.putExtra ("propertySize",property_size);
                            intent.putExtra ("country",Country);
                            intent.putExtra ("state",State);
                            intent.putExtra ("city",City);
                            intent.putExtra ("pincode",Pincode);
                            intent.putExtra ("password",password_add);
                            intent.putExtra ("email",email_add);
                            intent.putExtra ("message",message_add);
                            intent.putExtra ("property_type",property_type);

                            startActivity (intent);

                        }else{

                           emailVerification(email);

                           /* if(edit_wNumber.getText ().toString ().trim ().equals ("")){

                                Intent intent = new Intent (PersonalDetails.this, EmailOTPConfirmation.class);
                                intent.putExtra("reasonoftecheye",reasonoftecheye);
                                intent.putExtra("property_size",property_size);
                                intent.putExtra("Country",Country);
                                intent.putExtra("State",State);
                                intent.putExtra("City",City);
                                intent.putExtra("Pincode",Pincode);
                                intent.putExtra("token","225566");
                                intent.putExtra("Email",edit_Emailid.getText().toString().trim());
                                intent.putExtra("Password",edit_Password.getText().toString().trim());
                                intent.putExtra("Mobileno",edit_Mobileno.getText().toString().trim());
                                intent.putExtra("Whatsappno","null");
                                intent.putExtra("property_type",property_type);
                                startActivity (intent);

                            }else{

                                Intent intent = new Intent (PersonalDetails.this, EmailOTPConfirmation.class);
                                intent.putExtra("reasonoftecheye",reasonoftecheye);
                                intent.putExtra("property_size",property_size);
                                intent.putExtra("Country",Country);
                                intent.putExtra("State",State);
                                intent.putExtra("City",City);
                                intent.putExtra("Pincode",Pincode);
                                intent.putExtra("token","225566");
                                intent.putExtra("Email",edit_Emailid.getText().toString().trim());
                                intent.putExtra("Password",edit_Password.getText().toString().trim());
                                intent.putExtra("Mobileno",edit_Mobileno.getText().toString().trim());
                                intent.putExtra("Whatsappno",edit_wNumber.getText().toString().trim());
                                intent.putExtra("property_type",property_type);
                                startActivity (intent);
                            }*/

                        }

                    } else {

                        text_check.setText (message);
                        text_check.setTextColor (Color.RED);
                        Snackbar.make(findViewById(R.id.rl_layout), "Please fill all the fields", Snackbar.LENGTH_LONG)
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                .show();

                    }
                } else {
                    //Toast.makeText (PersonalDetails_Enabler.this, "validation not successfully", Toast.LENGTH_SHORT).show ( );

                    Snackbar.make(findViewById(R.id.rl_layout), "Please fill all the fields", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();

                }

            }
        });


    }

    public boolean isValidNumericPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9]).{4,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }

    public boolean isValidUppercasePassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[A-Z]).{1,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }

    public boolean isValidLowercasePassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[a-z]).{1,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }

    public boolean isValidSpeialePassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }

    public void emailVerification(String email){

        ProgressDialog dialog = new ProgressDialog (PersonalDetails.this);
        dialog.setMessage ("Sending OTP to your email ID");
        dialog.show ();

        StringRequest request = new StringRequest (Request.Method.POST, URLS.EmailOtp, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                try {

                    dialog.dismiss ();

                    JSONObject jsonObject = new JSONObject ( response );
                    String statues = jsonObject.getString ("message");
                    if(statues.equals ("Email has been sent by rashmi")){

                        String storetoken = jsonObject.getString ("storetoken");

                        JSONObject object = new JSONObject ( storetoken );

                        token = object.getString ("token");

                        Toast.makeText (PersonalDetails.this, "OTP has been sent to your email ID", Toast.LENGTH_SHORT).show ( );

                        if(edit_wNumber.getText ().toString ().trim ().equals ("")){

                            Intent intent = new Intent (PersonalDetails.this, EmailOTPConfirmation.class);
                            intent.putExtra("reasonoftecheye",reasonoftecheye);
                            intent.putExtra("property_size",property_size);
                            intent.putExtra("Country",Country);
                            intent.putExtra("State",State);
                            intent.putExtra("City",City);
                            intent.putExtra("Pincode",Pincode);
                            intent.putExtra("token",token);
                            intent.putExtra("Email",edit_Emailid.getText().toString().trim());
                            intent.putExtra("Password",edit_Password.getText().toString().trim());
                            intent.putExtra("Mobileno",edit_Mobileno.getText().toString().trim());
                            intent.putExtra("Whatsappno","null");
                            intent.putExtra("property_type",property_type);
                            startActivity (intent);

                        }else{

                            Intent intent = new Intent (PersonalDetails.this, EmailOTPConfirmation.class);
                            intent.putExtra("reasonoftecheye",reasonoftecheye);
                            intent.putExtra("property_size",property_size);
                            intent.putExtra("Country",Country);
                            intent.putExtra("State",State);
                            intent.putExtra("City",City);
                            intent.putExtra("Pincode",Pincode);
                            intent.putExtra("token",token);
                            intent.putExtra("Email",edit_Emailid.getText().toString().trim());
                            intent.putExtra("Password",edit_Password.getText().toString().trim());
                            intent.putExtra("Mobileno",edit_Mobileno.getText().toString().trim());
                            intent.putExtra("Whatsappno",edit_wNumber.getText().toString().trim());
                            intent.putExtra("property_type",property_type);
                            startActivity (intent);
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ();

                Toast.makeText (PersonalDetails.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put("email",email);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy ( 50000, 100, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (PersonalDetails.this);
        requestQueue.add (request);


    }


}