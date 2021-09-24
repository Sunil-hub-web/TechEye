package com.example.techeye.subscriber;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.example.techeye.MainActivity;
import com.example.techeye.R;
import com.example.techeye.subscriber.Url.URLS;
import com.example.techeye.subscriber.viewalldetails.ViewPropertyDetails;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Confirm extends AppCompatActivity implements PaymentResultListener {

    TextView text_Name,text_Mobileno,text_EmailId,text_Aadhar,text_Passport,text_PropertyType,
            text_PropertySize,text_frequency,text_Duration,txtamount;

    String reasonoftecheye,property_size,Country,State,City,Pincode,Email,Password,Mobileno,Whatsappno,Declaration,
            address1,Servicetype,ServiceDuration,Name,AadharNo,PassportNo,total,AadharPhoto,PassportPhoto,pricevalue,property_type,
            billing_address1,billing_address2, billing_country,billing_state,billing_city,billing_pincode,message,message1,id,date;

    int amount;

    Button payment;

    Uri Aadhar_Photo,Passport_Photo;

    int duration,default_price,weekly,fotnightly,monthly,current_month;
    double totalPrice;
    LocalDate newDate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_confirm);

        message = getIntent().getStringExtra("message");
        message1 = getIntent().getStringExtra("message1");

        text_Name = findViewById (R.id.txtname);
        text_Mobileno = findViewById (R.id.txtmobilenumber);
        text_EmailId = findViewById (R.id.txtemailid);
        text_Aadhar = findViewById (R.id.aadharno);
        text_Passport = findViewById (R.id.passportno);
        text_PropertyType = findViewById (R.id.propertytype);
        text_PropertySize = findViewById (R.id.propertysize);
        text_frequency = findViewById (R.id.frequency);
        text_Duration = findViewById (R.id.duration);
        payment = findViewById (R.id.payment);
        txtamount = findViewById (R.id.amount);

        try {

            if(message !=null){

                reasonoftecheye = getIntent().getStringExtra("reasonoftecheye");
                property_size = getIntent().getStringExtra("property_size");
                Country = getIntent().getStringExtra("Country");
                State = getIntent().getStringExtra("State");
                City = getIntent().getStringExtra("City");
                Pincode = getIntent().getStringExtra("Pincode");
                Email = getIntent().getStringExtra("Email");
                total = getIntent().getStringExtra("total");
                Servicetype = getIntent().getStringExtra("ServiceType");
                ServiceDuration = getIntent().getStringExtra("ServiceDuration");
                Password = getIntent ( ).getStringExtra ("Password");
                property_type = getIntent ( ).getStringExtra ("property_type");

                Name = SharedPrefManager.getInstance (Confirm.this).getUser ().getName ();
                Mobileno = SharedPrefManager.getInstance  (Confirm.this).getUser ().getMobileno ();
                AadharNo = SharedPrefManager.getInstance (Confirm.this).getUser ().getAadhar ();
                Email = SharedPrefManager.getInstance (Confirm.this).getUser ().getEmail ();
                PassportNo = SharedPrefManager.getInstance (Confirm.this).getUser ().getPassport ();




                text_Name.setText(Name);
                text_Mobileno.setText(Mobileno);
                text_EmailId.setText(Email);
                text_Aadhar.setText( AadharNo);
                text_Passport.setText(PassportNo);
                text_PropertyType.setText(reasonoftecheye);
                text_PropertySize.setText(property_size);
                text_frequency.setText(Servicetype);
                text_Duration.setText(ServiceDuration);
                txtamount.setText("Rs "+total);

                payment.setText ("Add Proprty");

            }
            else if(message1 != null) {

                id = getIntent ( ).getStringExtra ("id");
                Name = getIntent ( ).getStringExtra ("name");
                Email = getIntent ( ).getStringExtra ("email");
                Mobileno = getIntent ( ).getStringExtra ("mobileNumber");
                Whatsappno = getIntent ( ).getStringExtra ("mobileNumber1");
                reasonoftecheye = getIntent ( ).getStringExtra ("Reason_for_third_eye");
                property_size = getIntent ( ).getStringExtra ("Plot_size");
                Country = getIntent ( ).getStringExtra ("Country");
                State = getIntent ( ).getStringExtra ("State");
                City = getIntent ( ).getStringExtra ("City");
                Pincode = getIntent ( ).getStringExtra ("Pincode");
                AadharNo = getIntent ( ).getStringExtra ("AadharNumber");
                PassportNo = getIntent ( ).getStringExtra ("PassportNumber");
                Declaration = getIntent ( ).getStringExtra ("Declaration");
                address1 = getIntent ( ).getStringExtra ("Address1");
                Servicetype = getIntent ( ).getStringExtra ("Frequency");
                ServiceDuration = getIntent ( ).getStringExtra ("Duration");
                billing_address1 = getIntent ( ).getStringExtra ("BillingAddress1");
                billing_address2 = getIntent ( ).getStringExtra ("BillingAddress2");
                billing_country = getIntent ( ).getStringExtra ("BillingCountry");
                billing_state = getIntent ( ).getStringExtra ("BillingState");
                billing_city = getIntent ( ).getStringExtra ("BillingCity");
                billing_pincode = getIntent ( ).getStringExtra ("BillingPincode");
                Servicetype = getIntent ( ).getStringExtra ("Frequency");
                ServiceDuration = getIntent ( ).getStringExtra ("Duration");
                property_type = getIntent ( ).getStringExtra ("property_type");
                total = getIntent ( ).getStringExtra ("amount");

                payment.setText ("Make Payment");

                text_Name.setText(Name);
                text_Mobileno.setText(Mobileno);
                text_EmailId.setText(Email);
                text_Aadhar.setText( AadharNo);
                text_Passport.setText(PassportNo);
                text_PropertyType.setText(reasonoftecheye);
                text_PropertySize.setText(property_size);
                text_frequency.setText(Servicetype);
                text_Duration.setText(ServiceDuration);
                txtamount.setText("Rs "+total);

                //startPayment (Integer.parseInt (pricevalue));

            }
            else{
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
                Name = getIntent ( ).getStringExtra ("Name");
                AadharNo = getIntent ( ).getStringExtra ("AadharNo");
                PassportNo = getIntent ( ).getStringExtra ("PassportNo");
                Aadhar_Photo = Uri.parse (getIntent ( ).getExtras ( ).getString ("imageuri"));
                Passport_Photo = Uri.parse (getIntent ( ).getExtras ( ).getString ("imageuri1"));
                total = getIntent ( ).getStringExtra ("total");
                property_type = getIntent ( ).getStringExtra ("property_type");


                text_Name.setText (Name);
                text_Mobileno.setText (Mobileno);
                text_EmailId.setText (Email);
                text_Aadhar.setText (AadharNo);
                text_Passport.setText (PassportNo);
                text_PropertyType.setText (reasonoftecheye);
                text_PropertySize.setText (property_size);
                text_frequency.setText (Servicetype);
                text_Duration.setText (ServiceDuration);
                txtamount.setText("Rs "+total);



                Bitmap bitmap = MediaStore.Images.Media.getBitmap (this.getContentResolver ( ), Aadhar_Photo);
                ByteArrayOutputStream baos = new ByteArrayOutputStream ( );
                bitmap = Bitmap.createScaledBitmap (bitmap, 500, 750, true);
                bitmap.compress (Bitmap.CompressFormat.PNG, 80, baos); //bm is the bitmap object
                byte[] img = baos.toByteArray ( );
                AadharPhoto = Base64.encodeToString (img, Base64.DEFAULT);

                Bitmap bitmap1 = MediaStore.Images.Media.getBitmap (this.getContentResolver ( ), Passport_Photo);
                ByteArrayOutputStream baos1 = new ByteArrayOutputStream ( );
                bitmap1 = Bitmap.createScaledBitmap (bitmap1, 500, 750, true);
                bitmap1.compress (Bitmap.CompressFormat.PNG, 80, baos1); //bm is the bitmap object
                byte[] img1 = baos1.toByteArray ( );
                PassportPhoto = Base64.encodeToString (img1, Base64.DEFAULT);

            }

            if (ServiceDuration.equals ("6 Months")){
                DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
                date = df.format(Calendar.getInstance().getTime());

                LocalDate dates = LocalDate.parse (date);
                 newDate = dates.plusMonths (6);
            }else if (ServiceDuration.equals ("12 Months")){
                DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
                date = df.format(Calendar.getInstance().getTime());

                LocalDate dates = LocalDate.parse (date);
                 newDate = dates.plusMonths (12);
            }else {
                DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");
                date = df.format(Calendar.getInstance().getTime());

                LocalDate dates = LocalDate.parse (date);
                 newDate = dates.plusMonths (24);
            }



            payment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Checkout.preload(getApplicationContext());
                    if (message!=null){
                        startPayment (total);
                    }else if (message1!=null){
                        startPayment (total);
                    }else {
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Confirm.this)
                                .setMessage("Would you like to pay")
                                .setPositiveButton("Now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        checkEmail (Email);
                                    }
                                })
                                .setNegativeButton("Later", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        checkEmailWithoutPay(Email);
                                    }
                                });
                        AlertDialog alert11 = alertDialog.create();
                        alert11.show();
                    }

                }
            });



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startPayment(String Price) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        int IntValue = (int) Double.parseDouble (Price);
        amount = IntValue*100;
        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Tech Eye");
            //options.put("description", "The Battle of the Bests");
            //You can omit the image option to fetch the image from dashboard
            // options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", String.valueOf (amount));

            JSONObject preFill = new JSONObject();
            preFill.put("email", Email);
            preFill.put("contact", Mobileno);

            options.put("prefill", preFill);

            co.open(Confirm.this, options);
        } catch (Exception e) {
            Toast.makeText(Confirm.this, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    public void Add_RegistrationProperty(String name,String reason_for_third_eye, String country,
                             String state, String city, String pincode, String plot_size,
                             String mobile_number,String service_type, String subscription_for,
                             String aadhar_number, String passport_number, String paymentId,
                             String paymentStatus, String email,String carpet,String support){


        ProgressDialog progressDialog = new ProgressDialog(Confirm.this);
        progressDialog.setContentView (R.layout.progress_dialog);
        progressDialog.getWindow ().setBackgroundDrawableResource (android.R.color.transparent);
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest (Request.Method.POST,
                URLS.Register, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                Toast.makeText (Confirm.this, "Add Property SuccessFully", Toast.LENGTH_SHORT).show ( );

                Intent intent = new Intent ( Confirm.this,MainActivity.class );
                startActivity (intent);

                progressDialog.dismiss ();

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();
                error.printStackTrace ();
                Toast.makeText (Confirm.this, "Register not Successfully", Toast.LENGTH_SHORT).show ( );
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String> ( );
                params.put("name", name);
                params.put("reason_for_third_eye", reason_for_third_eye);
                params.put("country", country);
                params.put("state", state);
                params.put("city", city);
                params.put("pincode", pincode);
                params.put("plot_size", plot_size);
                params.put("mobile_number", mobile_number);
                params.put("mobile1", "null");
                params.put("declaration", "null");
                params.put("address1", "null");
                params.put("service_type", service_type);
                params.put("subscription_for", subscription_for);
                params.put("aadhar_number", aadhar_number);
                params.put("passport_number",passport_number );
                params.put("paymentId", paymentId);
                params.put("paymentStatus", paymentStatus);
                params.put("email", email);
                params.put("billing_address1", "null");
                params.put("billing_address2", "null");
                params.put("billing_country", "null");
                params.put("billing_state", "null");
                params.put("billing_city", "null");
                params.put("billing_pincode", "null");
                params.put("role", "Subscriber");
                params.put("password", Password);
                params.put("carpet_area", carpet);
                params.put("aadhar_image", "null");
                params.put("passport_image", "null");
                params.put("password_confirmation", Password);
                params.put("amount", total);
                params.put("ref_no", paymentId);
                params.put("support", support);
                params.put ("start_date",date);
                params.put ("expiry_date", String.valueOf (newDate));

                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (Confirm.this);
        requestQueue.add (stringRequest);
    }

    public void Registration(String name,String reason_for_third_eye, String country,
                             String state, String city, String pincode, String plot_size,
                             String mobile_number, String mobile1, String declaration,
                             String address1,String service_type, String subscription_for,
                             String aadhar_number, String passport_number, String paymentId,
                             String paymentStatus, String email, String billing_Address1,
                             String billing_Address2,String billing_country, String billing_State,
                             String billing_City,String billing_Pincode,String carpet,String support,
                             String startDate,String endDate){

        ProgressDialog progressDialog = new ProgressDialog(Confirm.this);
        progressDialog.setMessage("Registering Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest (Request.Method.POST,
                URLS.Register, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                Toast.makeText (Confirm.this, "Register Successfully", Toast.LENGTH_SHORT).show ( );

                Intent intent = new Intent ( Confirm.this,MainActivity.class );
                startActivity (intent);

                progressDialog.dismiss ();

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();
                error.printStackTrace ();
                Toast.makeText (Confirm.this, "Register not Successfully", Toast.LENGTH_SHORT).show ( );
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String> ( );
                params.put("name", name);
                params.put("reason_for_third_eye", reason_for_third_eye);
                params.put("country", country);
                params.put("state", state);
                params.put("city", city);
                params.put("pincode", pincode);
                params.put("plot_size", plot_size);
                params.put("mobile_number", mobile_number);
                params.put("mobile1", mobile1);
                params.put("declaration", declaration);
                params.put("address1", address1);
                params.put("service_type", service_type);
                params.put("subscription_for", subscription_for);
                params.put("aadhar_number", aadhar_number);
                params.put("passport_number",passport_number );
                params.put("paymentId", paymentId);
                params.put("paymentStatus", paymentStatus);
                params.put("email", email);
                params.put("billing_address1", billing_Address1);
                params.put("billing_address2", billing_Address2);
                params.put("billing_country", billing_country);
                params.put("billing_state", billing_State);
                params.put("billing_city", billing_City);
                params.put("billing_pincode", billing_Pincode);
                params.put("role", "Subscriber");
                params.put("password", Password);
                params.put("carpet_area", carpet);
                params.put("aadhar_image", "data:image/png;base64," + AadharPhoto);
                params.put("passport_image","data:image/png;base64," + PassportPhoto);
                params.put("password_confirmation", Password);
                params.put("amount", total);
                params.put("ref_no", paymentId);
                params.put("support", support);
                params.put ("start_date",startDate);
                params.put ("expiry_date", endDate);

                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (Confirm.this);
        requestQueue.add (stringRequest);
    }

    public void checkEmail(String email){

        ProgressDialog progressDialog = new ProgressDialog(Confirm.this);
        progressDialog.setMessage("Email is Avilable or Not...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.checkMail, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );
                    String ststues = jsonObject.getString ("email");

                    if(ststues.equals ("2")){

                        Toast.makeText (Confirm.this, "Email Id is Avilable", Toast.LENGTH_SHORT).show ( );

                        startPayment (total);

                    }else if(ststues.equals ("1")){

                        Toast.makeText (Confirm.this, "Email Id is Avilable", Toast.LENGTH_SHORT).show ( );

                        startPayment (total);


                    }
                    else {
                        Toast.makeText (Confirm.this, "Email Id is Not Avilable", Toast.LENGTH_SHORT).show ( );
                        startPayment (total);

                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace ();
                progressDialog.dismiss ();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put ("email",email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue (Confirm.this);
        requestQueue.add (stringRequest);
    }

    public void checkEmailWithoutPay(String email){

        ProgressDialog progressDialog = new ProgressDialog(Confirm.this);
        progressDialog.setMessage("Email is Avilable or Not...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.checkMail, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );
                    String ststues = jsonObject.getString ("email");

                    if(ststues.equals ("2")){

                        Toast.makeText (Confirm.this, "Email Id is Avilable", Toast.LENGTH_SHORT).show ( );

                        if (property_type.equals ("PlotSize")){
                            Registration (Name,reasonoftecheye,Country,State,City,Pincode,property_size,Mobileno,Whatsappno,Declaration,
                                    address1,Servicetype,ServiceDuration,AadharNo,PassportNo,"Not Paid","Pending",Email,billing_address1,
                                    billing_address2,billing_country, billing_state,billing_city,billing_pincode,"null","false","null","null");
                        }else {
                            Registration (Name,reasonoftecheye,Country,State,City,Pincode,"null",Mobileno,Whatsappno,Declaration,
                                    address1,Servicetype,ServiceDuration,AadharNo,PassportNo,"Not Paid","Pending",Email,billing_address1,
                                    billing_address2,billing_country, billing_state,billing_city,billing_pincode,property_size,"false","null","null");
                        }



                    }else if(ststues.equals ("1")){


                        Toast.makeText (Confirm.this, "Email Id is Avilable", Toast.LENGTH_SHORT).show ( );
                        if (property_type.equals ("PlotSize")){
                            Registration (Name,reasonoftecheye,Country,State,City,Pincode,property_size,Mobileno,Whatsappno,Declaration,
                                    address1,Servicetype,ServiceDuration,AadharNo,PassportNo,"Not Paid","Pending",Email,billing_address1,
                                    billing_address2,billing_country, billing_state,billing_city,billing_pincode,"null","false","null","null");
                        }else {
                            Registration (Name,reasonoftecheye,Country,State,City,Pincode,"null",Mobileno,Whatsappno,Declaration,
                                    address1,Servicetype,ServiceDuration,AadharNo,PassportNo,"Not Paid","Pending",Email,billing_address1,
                                    billing_address2,billing_country, billing_state,billing_city,billing_pincode,property_size,"false","null","null");
                        }


                    }
                    else {
                        Toast.makeText (Confirm.this, "Email Id is Not Avilable", Toast.LENGTH_SHORT).show ( );

                        if (property_type.equals ("PlotSize")){
                            Registration (Name,reasonoftecheye,Country,State,City,Pincode,property_size,Mobileno,Whatsappno,Declaration,
                                    address1,Servicetype,ServiceDuration,AadharNo,PassportNo,"Not Paid","Pending",Email,billing_address1,
                                    billing_address2,billing_country, billing_state,billing_city,billing_pincode,"null","false","null","null");
                        }else {
                            Registration (Name,reasonoftecheye,Country,State,City,Pincode,"null",Mobileno,Whatsappno,Declaration,
                                    address1,Servicetype,ServiceDuration,AadharNo,PassportNo,"Not Paid","Pending",Email,billing_address1,
                                    billing_address2,billing_country, billing_state,billing_city,billing_pincode,property_size,"false","null","null");
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace ();
                progressDialog.dismiss ();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put ("email",email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue (Confirm.this);
        requestQueue.add (stringRequest);
    }


    @Override
    public void onPaymentSuccess(String s) {

        if(message !=null){

            if (property_type.equals ("PlotSize")){
                Add_RegistrationProperty (Name,reasonoftecheye,Country,State,City,Pincode,property_size,Mobileno,
                        Servicetype,ServiceDuration,AadharNo,PassportNo,s,"success",Email,"null","true");
            }else {
                Add_RegistrationProperty (Name,reasonoftecheye,Country,State,City,Pincode,"null",Mobileno,
                        Servicetype,ServiceDuration,AadharNo,PassportNo,s,"success",Email,property_size,"true");
            }

        }else if(message1 != null){

            UpdateDetails (s,"success");

        }else{

            if (property_type.equals ("PlotSize")){
                Registration (Name,reasonoftecheye,Country,State,City,Pincode,property_size,Mobileno,Whatsappno,Declaration,
                        address1,Servicetype,ServiceDuration,AadharNo,PassportNo,s,"success",Email,billing_address1,
                        billing_address2,billing_country, billing_state,billing_city,billing_pincode,"null","true",date,String.valueOf (newDate));
            }else {
                Registration (Name,reasonoftecheye,Country,State,City,Pincode,"null",Mobileno,Whatsappno,Declaration,
                        address1,Servicetype,ServiceDuration,AadharNo,PassportNo,s,"success",Email,billing_address1,
                        billing_address2,billing_country, billing_state,billing_city,billing_pincode,property_size,"true",date,String.valueOf (newDate));
            }

        }


    }

    @Override
    public void onPaymentError(int i, String s) {


        Toast.makeText (this, s, Toast.LENGTH_SHORT).show ( );
    }

    public void UpdateDetails(String paymentId,String paymentStatus){

        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.updateUserDetails, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject ( response );
                    String success = object.getString ("1");

                    Toast.makeText (Confirm.this, success, Toast.LENGTH_SHORT).show ( );
                    startActivity (new Intent ( Confirm.this, ViewPropertyDetails.class));
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }



            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText (Confirm.this, "Update error", Toast.LENGTH_SHORT).show ( );
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );

                params.put ("id",id);
                params.put ("name",Name);
                params.put ("reason_for_third_eye",reasonoftecheye);
                params.put ("country",Country);
                params.put ("state",State);
                params.put ("city",City);
                params.put ("pincode",Pincode);
                params.put ("plot_size",property_size);
                params.put ("carpet_area","carpet_area");
                params.put ("mobile_number",Mobileno);
                params.put ("mobile1",Whatsappno);
                params.put ("declaration",Declaration);
                params.put ("address1",billing_address1);
                params.put ("billing_address1",billing_address1);
                params.put ("billing_address2",billing_address2);
                params.put ("billing_country",billing_country);
                params.put ("billing_state",billing_state);
                params.put ("billing_city",billing_city);
                params.put ("billing_pincode",billing_pincode);
                params.put ("aadhar_verified","yes");
                params.put ("service_type",Servicetype);
                params.put ("subscription_for",ServiceDuration);
                params.put ("aadhar_number",AadharNo);
                params.put ("passport_number",PassportNo);
                params.put ("paymentId",paymentId);
                params.put ("paymentStatus",paymentStatus);
                params.put ("frequency",Servicetype);
                params.put ("duration",ServiceDuration);
                params.put ("start_date",date);
                params.put ("expiry_date", String.valueOf (newDate));
                params.put ("entry_flag","false");
                params.put ("function","null");
                params.put ("support","null");
                params.put ("login_status","active");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue (Confirm.this);
        requestQueue.add (stringRequest);
    }
}