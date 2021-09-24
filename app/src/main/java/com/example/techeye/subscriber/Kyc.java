package com.example.techeye.subscriber;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.example.techeye.subscriber.Url.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kyc extends AppCompatActivity {

    Button btn_Next, btn_ChooseAadhar, btn_ChoosePassport, btn_Quote1, btn_Quote2;
    EditText edit_Name, edit_Aadhar, edit_passport;
    private AwesomeValidation awesomeValidation;
    public static final int REQUEST_GET_SINGLE_IMAGE = 1;
    public static final int REQUEST_GET_SINGLE_IMAGE1 = 2;

    TextView text_Chooser, text_Chooser1;

    Bitmap bitmap, bitmap1;

    Uri imageUri1, imageUri2;
    String reasonoftecheye, property_size, Country, State, City, Pincode, Email, Password, Mobileno, Whatsappno, Declaration,
            address1, Servicetype, ServiceDuration, total, price,billing_address1,
            billing_address2, billing_country,billing_state,
            billing_city,billing_pincode,message,property_type;

    TextView text_Value1, text_Value2, text_Message,text_Message1;
    Button btn_Ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_kyc);

        message = getIntent ( ).getStringExtra ("message");

        btn_Next = findViewById (R.id.knext);
        btn_ChooseAadhar = findViewById (R.id.btnaadhar);
        btn_ChoosePassport = findViewById (R.id.btnpassport);
        edit_Name = findViewById (R.id.name);
        edit_Aadhar = findViewById (R.id.aadhar);
        edit_passport = findViewById (R.id.passport);
        text_Chooser = findViewById (R.id.choose);
        text_Chooser1 = findViewById (R.id.choose1);
        btn_Quote1 = findViewById (R.id.getquote);
        btn_Quote2 = findViewById (R.id.quote);

        btn_Next.setEnabled (false);


        try {

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
            Servicetype = getIntent ( ).getStringExtra ("ServiceType");
            ServiceDuration = getIntent ( ).getStringExtra ("ServiceDuration");
            total = getIntent ( ).getStringExtra ("total");
            property_type = getIntent ( ).getStringExtra ("property_type");

        } catch (Exception e) {
            e.printStackTrace ( );
        }

        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);

        awesomeValidation.addValidation (Kyc.this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.name);
        //awesomeValidation.addValidation (Kyc.this, R.id.passport, "^[a-zA-Z]{2}[0-9]{7}$", R.string.passport);




        btn_ChooseAadhar.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_IMAGE);
            }
        });

        btn_ChoosePassport.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult (intent, REQUEST_GET_SINGLE_IMAGE1);

            }
        });

        btn_Quote1.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {


                Dialog dialog = new Dialog (Kyc.this);
                dialog.setContentView (R.layout.quote);
                dialog.setCancelable (false);

                text_Value1 = dialog.findViewById (R.id.editText);
                text_Value2 = dialog.findViewById (R.id.editText1);
                text_Message = dialog.findViewById (R.id.editText2);
                text_Message1 = dialog.findViewById (R.id.editText3);
                btn_Ok = dialog.findViewById (R.id.button);

                double total_price = Double.parseDouble (total) * 73;
                price = String.valueOf (total_price);
                float IntValue = (float) Float.parseFloat (price);

                text_Value1.setText ("Amount is $"+total);
                text_Value2.setText ("Value In INR : Rs "+String.valueOf (IntValue));
                text_Message.setText ("A geofenceing link will be generated once the payment is made , please " +
                        "use the link to geofence the property. The service will start after 7 days of geofencing the property");
                text_Message1.setText ("Thank You");

                btn_Next.setEnabled (true);

                btn_Ok.setOnClickListener (new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss ( );
                        admininquriData();
                    }
                });
                dialog.show ( );
                Window window = dialog.getWindow ( );
                window.setLayout (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            }
        });

        btn_Quote2.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog (Kyc.this);
                dialog.setContentView (R.layout.quote);
                dialog.setCancelable (false);

                text_Value1 = dialog.findViewById (R.id.editText);
                text_Value2 = dialog.findViewById (R.id.editText1);
                text_Message = dialog.findViewById (R.id.editText2);
                text_Message1 = dialog.findViewById (R.id.editText3);
                btn_Ok = dialog.findViewById (R.id.button);

                double total_price = Double.parseDouble (total) * 73;
                price = String.valueOf (total_price);
                float IntValue = (float) Float.parseFloat (price);

                text_Value1.setText ("Amount is $"+total);
                text_Value2.setText ("Value In INR : Rs "+String.valueOf (IntValue));
                text_Message.setText ("Your Quotation Details Will Be Shared to Your WhatsApp And E-mail id !!");
                text_Message1.setText ("Thank You");

                btn_Next.setEnabled (true);

                btn_Ok.setOnClickListener (new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss ( );
                        admininquriData();
                    }
                });
                dialog.show ( );
                Window window = dialog.getWindow ( );
                window.setLayout (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            }
        });

        btn_Next.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate ( )) {

                    if (edit_Aadhar.getText ( ).toString ( ).trim ( ).length ( ) == 12) {

                        if (imageUri1 != null && imageUri2 != null) {

                            Toast.makeText (Kyc.this, "Success", Toast.LENGTH_SHORT).show ( );

                            Intent intent = new Intent (Kyc.this, Confirm.class);
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
                            intent.putExtra("billing_address1",billing_address1);
                            intent.putExtra("billing_address2",billing_address2);
                            intent.putExtra("billing_city",billing_city);
                            intent.putExtra("billing_country",billing_country);
                            intent.putExtra("billing_pincode",billing_pincode);
                            intent.putExtra("billing_state",billing_state);
                            intent.putExtra ("ServiceType", Servicetype);
                            intent.putExtra ("ServiceDuration", ServiceDuration);
                            intent.putExtra ("Name", edit_Name.getText ( ).toString ( ).trim ( ));
                            intent.putExtra ("AadharNo", edit_Aadhar.getText ( ).toString ( ).trim ( ));
                            intent.putExtra ("PassportNo", edit_passport.getText ( ).toString ( ).trim ( ));
                            intent.putExtra("imageuri", imageUri1.toString());
                            intent.putExtra("imageuri1", imageUri2.toString());
                            intent.putExtra ("total", price);
                            intent.putExtra ("property_type", property_type);
                            startActivity (intent);

                        } else {
                            Toast.makeText (Kyc.this, "Please fill all fields", Toast.LENGTH_SHORT).show ( );
                        }
                    } else {
                        edit_Aadhar.setError ("Enter valid Aadhar Number");
                    }



                } else {
                    Toast.makeText (Kyc.this, "Validation Un SuccessFull", Toast.LENGTH_SHORT).show ( );
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == REQUEST_GET_SINGLE_IMAGE && resultCode == RESULT_OK &&
                data != null && data.getData ( ) != null) {

            imageUri1 = data.getData ( );
            try {


                bitmap = MediaStore.Images.Media.getBitmap (this.getContentResolver ( ), imageUri1);
                text_Chooser.setText (bitmap.toString ());
            } catch (IOException e) {
                e.printStackTrace ( );
            }


        } else if (requestCode == REQUEST_GET_SINGLE_IMAGE1 && resultCode == RESULT_OK &&
                data != null && data.getData ( ) != null) {

            imageUri2 = data.getData ( );

            try {
                bitmap1 = MediaStore.Images.Media.getBitmap (this.getContentResolver ( ), imageUri2);
            } catch (IOException e) {
                e.printStackTrace ( );
            }
            text_Chooser1.setText (bitmap1.toString ( ));

        } else {
            Toast.makeText (this, "Select Image", Toast.LENGTH_SHORT).show ( );
        }
    }

    public boolean isValidPassportPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^[A-PR-WYa-pr-wy][1-9]\\\\d\\\\s?\\\\d{4}[1-9]$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }

    public boolean isValidNamePassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^[A-Za-z\\\\s]{1,}[\\\\.]{0,1}[A-Za-z\\\\s]{0,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }

    public void admininquriData() {

        ProgressDialog dialog = new ProgressDialog (Kyc.this);
        dialog.setMessage ("Storing Data...");
        dialog.show ( );

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.admininquriUrl, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                dialog.dismiss ( );

                try {
                    JSONObject jsonObject = new JSONObject (response);

                    String statues = jsonObject.getString ("message");
                    if (statues.equals ("Data stored")) {
                        Toast.makeText (Kyc.this, "Data Stored SuccessFully", Toast.LENGTH_SHORT).show ( );

                        String information = jsonObject.getString ("Information");
                        JSONObject jsonObject1 = new JSONObject (information);
                        String Email = jsonObject1.getString ("email");

                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss ( );

                Toast.makeText (Kyc.this, "" + error, Toast.LENGTH_SHORT).show ( );

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<> ( );
                params.put ("name", edit_Name.getText ( ).toString ( ).trim ( ));
                params.put ("city", City);
                params.put ("state", State);
                params.put ("country", Country);
                params.put ("pincode", Pincode);
                params.put ("email", Email);
                params.put ("plotSize", property_size);
                params.put ("plotType", reasonoftecheye);
                params.put ("service", Servicetype);
                params.put ("subscription", ServiceDuration);
                params.put ("phone", Mobileno);
                params.put ("whatsapp", Whatsappno);
                params.put ("quote", price);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue (Kyc.this);
        requestQueue.add (stringRequest);

    }


}