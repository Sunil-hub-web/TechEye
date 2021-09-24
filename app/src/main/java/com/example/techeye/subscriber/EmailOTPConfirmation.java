package com.example.techeye.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techeye.MainActivity;
import com.example.techeye.R;

public class EmailOTPConfirmation extends AppCompatActivity {

    Button btn_Verifay;
    EditText edit_Otp;
    TextView txt_wrongotp;
    String reasonoftecheye,property_size,Country,State,City,Pincode,Email,Password,Mobileno,Whatsappno,token,property_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_email_otpconfirmation);

        btn_Verifay = findViewById (R.id.verifay);
        edit_Otp = findViewById (R.id.otp);
        txt_wrongotp = findViewById (R.id.txt_wrongotp);

        try {

            reasonoftecheye = getIntent().getStringExtra("reasonoftecheye");
            property_size = getIntent().getStringExtra("property_size");
            Country = getIntent().getStringExtra("Country");
            State = getIntent().getStringExtra("State");
            City = getIntent().getStringExtra("City");
            Pincode = getIntent().getStringExtra("Pincode");
            Email = getIntent().getStringExtra("Email");
            Password = getIntent().getStringExtra("Password");
            Mobileno = getIntent().getStringExtra("Mobileno");
            Whatsappno = getIntent().getStringExtra("Whatsappno");
            token = getIntent().getStringExtra("token");
            property_type = getIntent().getStringExtra("property_type");

        }catch (Exception e){
            e.printStackTrace();
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

                        Toast.makeText (EmailOTPConfirmation.this, "Success", Toast.LENGTH_SHORT).show ( );
                        Intent intent = new Intent(EmailOTPConfirmation.this, Declaration_Page.class);
                        intent.putExtra("reasonoftecheye",reasonoftecheye);
                        intent.putExtra("property_size",property_size);
                        intent.putExtra("Country",Country);
                        intent.putExtra("State",State);
                        intent.putExtra("City",City);
                        intent.putExtra("Pincode",Pincode);
                        intent.putExtra("Email",Email);
                        intent.putExtra("Password",Password);
                        intent.putExtra("Mobileno",Mobileno);
                        intent.putExtra("Whatsappno",Whatsappno);
                        intent.putExtra("property_type",property_type);
                        startActivity(intent);

                    }else{

                        txt_wrongotp.setVisibility(View.VISIBLE);
                    }

                }

            }
        });


    }
}