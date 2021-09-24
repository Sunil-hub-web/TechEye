package com.example.techeye.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.techeye.MainActivity;
import com.example.techeye.R;

public class BillingAddress extends AppCompatActivity {

    Button btn_Next;
    EditText edit_Address1,edit_Address2,edit_Country,edit_State,edit_City,edit_Pincode;
    private AwesomeValidation awesomeValidation;
    String reasonoftecheye,property_size,Country,State,City,Pincode,Email,Password,Mobileno,Whatsappno,Declaration,property_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_billing_address);

        btn_Next = findViewById (R.id.bnext);

        edit_Address1 = findViewById (R.id.address);
        edit_Address2 = findViewById (R.id.address1);
        edit_Country = findViewById (R.id.country);
        edit_State = findViewById (R.id.state);
        edit_City = findViewById (R.id.city);
        edit_Pincode = findViewById (R.id.pincode);

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
            Declaration = getIntent().getStringExtra("Declaration");
            property_type = getIntent().getStringExtra("property_type");

        }catch (Exception e){
            e.printStackTrace();
        }

//        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);
//
//        awesomeValidation.addValidation (BillingAddress.this,R.id.address,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.address);
//        awesomeValidation.addValidation (BillingAddress.this,R.id.address1,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.address);
//        awesomeValidation.addValidation (BillingAddress.this,R.id.country,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.country);
//        awesomeValidation.addValidation (BillingAddress.this,R.id.state,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.state);
//        awesomeValidation.addValidation (BillingAddress.this,R.id.city,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.city);

        btn_Next.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

//                if(awesomeValidation.validate ()){

                    if(edit_Pincode.getText ().toString ().trim ().length () == 6){

                        Toast.makeText (BillingAddress.this, "success", Toast.LENGTH_SHORT).show ( );

                        Intent intent = new Intent(BillingAddress.this, Subscription.class);
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
                        intent.putExtra("Declaration",Declaration);
                        intent.putExtra("address1",edit_Address1.getText().toString().trim());
                        intent.putExtra("billing_address1",edit_Address1.getText().toString().trim());
                        intent.putExtra("billing_address2",edit_Address2.getText().toString().trim());
                        intent.putExtra("billing_city",edit_City.getText().toString().trim());
                        intent.putExtra("billing_country",edit_Country.getText().toString().trim());
                        intent.putExtra("billing_pincode",edit_Pincode.getText().toString().trim());
                        intent.putExtra("billing_state",edit_State.getText().toString().trim());
                        intent.putExtra("property_type",property_type);
                        startActivity(intent);
                    }else{
                        edit_Pincode.setError ("Enter 6 Digit Number");
                    }
//                }else{
//                    Toast.makeText (BillingAddress.this, "not success", Toast.LENGTH_SHORT).show ( );
//                }

            }
        });
    }
}