package com.example.techeye.subscriber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.techeye.R;
import com.google.android.material.snackbar.Snackbar;

public class Subscription extends AppCompatActivity {

    Spinner services,subscription;
    Button btn_Verifay;

    String [] subsc = {"6 Months","12 Months","24 Months"};
    String [] service = {"Onace In a Week","Once in Every 15 Days","Monthly"};
    String reasonoftecheye,property_size,Country,State,City,Pincode,Email,Password,Mobileno,Whatsappno,Declaration,
            address1,Servicetype,ServiceDuration,billing_address1,billing_address2, billing_country,billing_state,
            billing_city,billing_pincode,message,property_type;
    RelativeLayout rl_layout;
    int duration,default_price,weekly,fotnightly,monthly;
    double total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_subscription);

         subscription = findViewById (R.id.subscription);
         services = findViewById (R.id.services);
        rl_layout = findViewById(R.id.rl_layout);
         btn_Verifay = findViewById (R.id.verifay);

        message = getIntent ( ).getStringExtra ("message");

        try {

            if(message != null){

                reasonoftecheye = getIntent ( ).getStringExtra ("reasonoftecheye");
                property_size = getIntent ( ).getStringExtra ("propertySize");
                Country = getIntent ( ).getStringExtra ("country");
                State = getIntent ( ).getStringExtra ("state");
                City = getIntent ( ).getStringExtra ("city");
                Pincode = getIntent ( ).getStringExtra ("pincode");
                Email = getIntent ( ).getStringExtra ("email");
                Password = getIntent ( ).getStringExtra ("password");
                property_type = getIntent ( ).getStringExtra ("property_type");

            }else {
                reasonoftecheye = getIntent ( ).getStringExtra ("reasonoftecheye");
                property_size = getIntent ( ).getStringExtra ("property_size");
                Country = getIntent ( ).getStringExtra ("Country");
                State = getIntent ( ).getStringExtra ("State");
                City = getIntent ( ).getStringExtra ("City");
                Pincode = getIntent ( ).getStringExtra ("Pincode");
                Email = getIntent ( ).getStringExtra ("Email");
                Password = getIntent ( ).getStringExtra ("Password");
                Mobileno = getIntent ( ).getStringExtra ("Mobileno");
                Whatsappno = getIntent ( ).getStringExtra ("Whatsappno");
                Declaration = getIntent ( ).getStringExtra ("Declaration");
                address1 = getIntent ( ).getStringExtra ("address1");
                billing_address1 = getIntent ( ).getStringExtra ("billing_address1");
                billing_address2 = getIntent ( ).getStringExtra ("billing_address2");
                billing_country = getIntent ( ).getStringExtra ("billing_country");
                billing_state = getIntent ( ).getStringExtra ("billing_state");
                billing_city = getIntent ( ).getStringExtra ("billing_city");
                billing_pincode = getIntent ( ).getStringExtra ("billing_pincode");
                property_type = getIntent ( ).getStringExtra ("property_type");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        ArrayAdapter subscAdapter = new ArrayAdapter(this,R.layout.spiner_text,subsc);
        subscAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subscription.setAdapter(subscAdapter);

        subscription.setSelection(-1,true);

        ArrayAdapter serviceAdapter = new ArrayAdapter(this,R.layout.spiner_text,service);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        services.setAdapter(serviceAdapter);

        services.setSelection(-1,true);

        subscription.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


                        Object item = parent.getItemAtPosition(pos);
                        ServiceDuration = item.toString();
                        if (ServiceDuration.equals ("6 Months")){

                            duration = 6;
                            default_price = 7;
                        }else if (ServiceDuration.equals ("12 Months")){

                            duration = 12;
                            default_price = 6;
                        }else {

                            duration=24;
                            default_price=5;
                        }

                        Log.d ("duration",String.valueOf (duration));
                        Log.d ("price",String.valueOf (default_price));

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        services.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


                                    Object item = parent.getItemAtPosition(pos);
                                    Servicetype = item.toString();

                                    if (Servicetype.equals ("Onace In a Week")){

                                        weekly = (duration*default_price*4)+5;
                                        total = Double.parseDouble (String.valueOf (weekly))*1.18;

                                    }else if (Servicetype.equals ("Once in Every 15 Days")){

                                        fotnightly = (duration*default_price*2)+5;
                                        total = Double.parseDouble (String.valueOf (fotnightly))*1.18;
                                    }else {
                                        monthly = (duration*default_price*1)+5;
                                        total = Double.parseDouble (String.valueOf (monthly))*1.18;
                                    }

                                    Log.d ("total",String.valueOf (total));
                                    Log.d ("weekly",String.valueOf (weekly));
                                    Log.d ("fotnightly",String.valueOf (fotnightly));
                                    Log.d ("monthly",String.valueOf (monthly));

                                    Toast.makeText (Subscription.this, String.valueOf (total), Toast.LENGTH_SHORT).show ( );

                                }
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });


        btn_Verifay.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (Servicetype!=null && ServiceDuration!=null){

                    if(message!=null){

                        Intent intent = new Intent ( Subscription.this,Confirm.class );
                        intent.putExtra("reasonoftecheye",reasonoftecheye);
                        intent.putExtra("property_size",property_size);
                        intent.putExtra("Country",Country);
                        intent.putExtra("State",State);
                        intent.putExtra("City",City);
                        intent.putExtra("Pincode",Pincode);
                        intent.putExtra("Email",Email);
                        intent.putExtra("Password",Password);
                        intent.putExtra("total",String.valueOf (total));
                        intent.putExtra("message",message);
                        intent.putExtra ("ServiceType", Servicetype);
                        intent.putExtra ("ServiceDuration", ServiceDuration);
                        intent.putExtra ("property_type", property_type);
                        startActivity (intent);

                    }else {
                        Intent intent = new Intent (Subscription.this, Kyc.class);
                        intent.putExtra ("reasonoftecheye", reasonoftecheye);
                        intent.putExtra ("property_size", property_size);
                        intent.putExtra ("Country", Country);
                        intent.putExtra ("State", State);
                        intent.putExtra ("City", City);
                        intent.putExtra ("Pincode", Pincode);
                        intent.putExtra ("Email", Email);
                        intent.putExtra ("Password", Password);
                        intent.putExtra ("Mobileno", Mobileno);
                        intent.putExtra ("Whatsappno", Whatsappno);
                        intent.putExtra ("Declaration", Declaration);
                        intent.putExtra ("address1", address1);
                        intent.putExtra ("billing_address1", billing_address1);
                        intent.putExtra ("billing_address2", billing_address2);
                        intent.putExtra ("billing_city", billing_city);
                        intent.putExtra ("billing_country", billing_country);
                        intent.putExtra ("billing_pincode", billing_pincode);
                        intent.putExtra ("billing_state", billing_state);
                        intent.putExtra ("ServiceType", Servicetype);
                        intent.putExtra ("ServiceDuration", ServiceDuration);
                        intent.putExtra ("total", String.valueOf (total));
                        intent.putExtra ("message", message);
                        intent.putExtra ("property_type", property_type);
                        startActivity (intent);
                    }

                }else {
                    Snackbar.make(rl_layout, "Please fill all the fields", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }


            }
        });
    }
}