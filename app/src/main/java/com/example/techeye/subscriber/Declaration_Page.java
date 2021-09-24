package com.example.techeye.subscriber;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.techeye.R;

public class Declaration_Page extends AppCompatActivity {

    CheckBox checkBox1, checkBox2,checkBox3,checkBox4,checkBox5;
    Button btn_yes, btn_no;
    String checkbox1, checkbox2, checkBox;
    Uri image_uri, image_uri1;
    String reasonoftecheye,property_size,Country,State,City,Pincode,Email,Password,Mobileno,Whatsappno,property_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declarationpage);


        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);


        btn_yes = findViewById(R.id.yes);
        btn_no = findViewById(R.id.no);

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
            property_type = getIntent().getStringExtra("property_type");

        }catch (Exception e){
            e.printStackTrace();
        }

        //btn_yes.setEnabled(false);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBox1.isChecked()) {


                    if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() &&
                            checkBox4.isChecked () && checkBox5.isChecked ()) {

                        btn_yes.setBackgroundResource(R.drawable.button_shap);
                        btn_yes.setTextColor(Color.BLACK);
                        btn_no.setBackgroundResource(R.drawable.button_back);

                    }

                    checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (checkBox2.isChecked()) {

                                if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() &&
                                        checkBox4.isChecked () && checkBox5.isChecked ()) {

                                    btn_yes.setBackgroundResource(R.drawable.button_shap);
                                    btn_yes.setTextColor(Color.BLACK);
                                    btn_no.setBackgroundResource(R.drawable.button_back);

                                }

                                checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                        if (checkBox3.isChecked()) {

                                            if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() &&
                                                    checkBox4.isChecked () && checkBox5.isChecked ()) {

                                                btn_yes.setBackgroundResource(R.drawable.button_shap);
                                                btn_yes.setTextColor(Color.BLACK);
                                                btn_no.setBackgroundResource(R.drawable.button_back);

                                            }

                                            checkBox4.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener ( ) {
                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                    if(checkBox4.isChecked ()){

                                                        if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() &&
                                                                checkBox4.isChecked () && checkBox5.isChecked ()) {

                                                            btn_yes.setBackgroundResource(R.drawable.button_shap);
                                                            btn_yes.setTextColor(Color.BLACK);
                                                            btn_no.setBackgroundResource(R.drawable.button_back);

                                                        }

                                                        checkBox5.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener ( ) {
                                                            @Override
                                                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                                if(checkBox5.isChecked ()){

                                                                    if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() &&
                                                                            checkBox4.isChecked () && checkBox5.isChecked ()) {

                                                                        btn_yes.setBackgroundResource(R.drawable.button_shap);
                                                                        btn_yes.setTextColor(Color.BLACK);
                                                                        btn_no.setBackgroundResource(R.drawable.button_back);

                                                                    }
                                                                }else{
                                                                    Toast.makeText(Declaration_Page.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();

                                                                    btn_yes.setBackgroundResource(R.drawable.button_back1);
                                                                    btn_no.setBackgroundResource(R.drawable.button_back);
                                                                    btn_yes.setTextColor(Color.WHITE);
                                                                }

                                                            }
                                                        });
                                                    }else{
                                                        Toast.makeText(Declaration_Page.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();

                                                        btn_yes.setBackgroundResource(R.drawable.button_back1);
                                                        btn_no.setBackgroundResource(R.drawable.button_back);
                                                        btn_yes.setTextColor(Color.WHITE);
                                                    }

                                                }
                                            });

                                        } else {
                                            Toast.makeText(Declaration_Page.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();

                                            btn_yes.setBackgroundResource(R.drawable.button_back1);
                                            btn_no.setBackgroundResource(R.drawable.button_back);
                                            btn_yes.setTextColor(Color.WHITE);

                                        }
                                    }
                                });

                            } else {

                                Toast.makeText(Declaration_Page.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();

                                btn_yes.setBackgroundResource(R.drawable.button_back1);
                                btn_no.setBackgroundResource(R.drawable.button_back);
                                btn_yes.setTextColor(Color.WHITE);
                            }

                        }
                    });

                } else {
                    Toast.makeText(Declaration_Page.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();

                    btn_yes.setBackgroundResource(R.drawable.button_back1);
                    btn_no.setBackgroundResource(R.drawable.button_back);
                    btn_yes.setTextColor(Color.WHITE);
                }

            }
        });


        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() &&
                        checkBox4.isChecked () && checkBox5.isChecked ()) {

                    btn_yes.setBackgroundResource(R.drawable.button_shap);
                    btn_yes.setTextColor(Color.BLACK);
                    btn_no.setBackgroundResource(R.drawable.button_back);

                } else {
                    Toast.makeText(Declaration_Page.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkBox1.isChecked() && checkBox2.isChecked() && checkBox3.isChecked() &&
                        checkBox4.isChecked () && checkBox5.isChecked ()) {

                    checkBox=checkBox1.getText().toString().trim()+checkBox2.getText().toString().trim()+
                            checkBox3.getText().toString().trim()+checkBox4.getText().toString().trim()+checkBox5.getText().toString().trim();

                    Intent intent = new Intent(Declaration_Page.this, BillingAddress.class);
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
                    intent.putExtra("Declaration",checkBox);
                    intent.putExtra("property_type",property_type);
                    startActivity(intent);

                } else {
                    Toast.makeText(Declaration_Page.this, "Select All The CheckBox", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}