package com.example.techeye.enabler.Enabler_SignUP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techeye.R;

public class EmailOTPConfirmation_enabler extends AppCompatActivity {

    Button btn_Verifay;
    EditText edit_Otp;
    TextView txt_wrongotp;

    String name,mbileNo,emailId,dlno,bloodGroup,contactNo,token;
    Uri imageProfile,imageDl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_email_otpconfirmation_enabler);

        btn_Verifay = findViewById (R.id.verifay);
        edit_Otp = findViewById (R.id.otp);
        txt_wrongotp = findViewById (R.id.txt_wrongotp);

        try{

            name = getIntent ().getStringExtra ("name");
            mbileNo = getIntent ().getStringExtra ("mobile");
            emailId = getIntent ().getStringExtra ("email");
            dlno = getIntent ().getStringExtra ("dlnumber");
            bloodGroup = getIntent ().getStringExtra ("bloodgroup");
            contactNo = getIntent ().getStringExtra ("contact");
            imageProfile = Uri.parse (getIntent ( ).getExtras ( ).getString ("imageuri"));
            imageDl = Uri.parse (getIntent ( ).getExtras ( ).getString ("imageuri1"));
            token = getIntent().getStringExtra("token");

            edit_Otp.setText (token);

        }catch (Exception e){
            e.printStackTrace ();
        }

        btn_Verifay.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if(edit_Otp.getText ().toString ().trim().equals ("") &&
                        edit_Otp.getText ().toString ().trim ().length () != 6){

                    edit_Otp.setError ("Enter Valid otp");

                }else{
                    if(edit_Otp.getText ().toString ().trim ().equals (token)){

                        txt_wrongotp.setVisibility(View.GONE);

                        Toast.makeText (EmailOTPConfirmation_enabler.this, "Success", Toast.LENGTH_SHORT).show ( );
                        Intent intent = new Intent(EmailOTPConfirmation_enabler.this, Details.class);
                        intent.putExtra ("name",name);
                        intent.putExtra ("email",emailId);
                        intent.putExtra ("mobile",mbileNo);
                        intent.putExtra ("contact",contactNo);
                        intent.putExtra ("bloodgroup",bloodGroup);
                        intent.putExtra ("dlnumber",dlno);
                        intent.putExtra("imageuri", imageProfile.toString ());
                        intent.putExtra("imageuri1", imageDl.toString ());
                        startActivity(intent);

                    }else{

                        txt_wrongotp.setVisibility(View.VISIBLE);
                    }

                }
            }
        });

    }
}