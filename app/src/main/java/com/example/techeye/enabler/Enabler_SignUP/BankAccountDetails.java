package com.example.techeye.enabler.Enabler_SignUP;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.techeye.MainActivity;
import com.example.techeye.R;
import com.example.techeye.subscriber.Confirm;
import com.example.techeye.subscriber.Url.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BankAccountDetails extends AppCompatActivity {

    EditText edit_BankName, edit_IFCCode, edit_AccountHolderName, edit_AcountNumber, edit_ConformAcountNumber, edit_MobileNo;
    Button btn_Save;
    boolean isEmailValid, isPasswordValid, isPasswordVisible;

    private AwesomeValidation awesomeValidation;

    String name,mobileNo,emailId,dlno,bloodGroup,contactNo,aadharNo,passportNo,password,image_Aadhar,image_Passport,
            image_Profile,image_dl,bankName,ifcsCode,accountHolderName,accountNo;
    Uri imageProfile,imageDl,imageAadhar,imagePassport;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_bank_account_details);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        edit_BankName = findViewById(R.id.bankname);
        edit_AccountHolderName = findViewById(R.id.accountholdername);
        edit_AcountNumber = findViewById(R.id.accountno);
        edit_ConformAcountNumber = findViewById(R.id.conformaccountno);
        edit_IFCCode = findViewById(R.id.ifccode);
        edit_MobileNo = findViewById(R.id.mobileno);

        btn_Save = findViewById(R.id.save);

        awesomeValidation.addValidation(BankAccountDetails.this, R.id.bankname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.bankname);
        awesomeValidation.addValidation(BankAccountDetails.this, R.id.accountholdername, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.holdername);
        awesomeValidation.addValidation(BankAccountDetails.this, R.id.accountno, "^[0-9]{1,20}$", R.string.accountno);
        awesomeValidation.addValidation(BankAccountDetails.this, R.id.conformaccountno, "^[0-9]{0,20}$", R.string.confaccNumber);
        awesomeValidation.addValidation(BankAccountDetails.this, R.id.ifccode, "^[A-za-z]{4}[A-za-z0-9]{1,10}$", R.string.ifccode);
        awesomeValidation.addValidation(BankAccountDetails.this, R.id.mobileno, "^[0-9]{1,15}$", R.string.Mobile);

        try{

            name = getIntent ().getStringExtra ("name");
            mobileNo = getIntent ().getStringExtra ("mobile");
            emailId = getIntent ().getStringExtra ("email");
            dlno = getIntent ().getStringExtra ("dlnumber");
            bloodGroup = getIntent ().getStringExtra ("bloodgroup");
            contactNo = getIntent ().getStringExtra ("contact");
            aadharNo = getIntent ().getStringExtra ("aadharno");
            passportNo = getIntent ().getStringExtra ("passportno");
            password = getIntent ().getStringExtra ("password");
            imageAadhar = Uri.parse (getIntent ( ).getExtras ( ).getString ("imageuri1"));
            imagePassport = Uri.parse (getIntent ( ).getExtras ( ).getString ("imageuri2"));
            imageProfile = Uri.parse (getIntent ( ).getExtras ( ).getString ("imageuri3"));
            imageDl = Uri.parse (getIntent ( ).getExtras ( ).getString ("imageuri4"));

        }catch (Exception e){
            e.printStackTrace ();
        }

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (awesomeValidation.validate()) {

                    if (!edit_AcountNumber.getText().toString().equals(edit_ConformAcountNumber.getText().toString())) {
                        edit_ConformAcountNumber.setError("AccountNo Not matched");
                        edit_ConformAcountNumber.requestFocus();
                    } else {
                        Toast.makeText(BankAccountDetails.this, "Account Number Is Correct", Toast.LENGTH_SHORT).show();

                        bankName = edit_BankName.getText ().toString ().trim ();
                        ifcsCode = edit_IFCCode.getText ().toString ().trim ();
                        accountHolderName = edit_AccountHolderName.getText ().toString ().trim ();
                        accountNo = edit_AcountNumber.getText ().toString ().trim ();

                        UserRegister();

                    }

                    Toast.makeText(BankAccountDetails.this, "Your Validation Complite", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(BankAccountDetails.this, "Please fill details properly", Toast.LENGTH_SHORT).show();
                }
            }
        });


        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap (this.getContentResolver ( ), imageAadhar);
            ByteArrayOutputStream baos = new ByteArrayOutputStream ( );
            bitmap = Bitmap.createScaledBitmap (bitmap, 500, 750, true);
            bitmap.compress (Bitmap.CompressFormat.PNG, 80, baos); //bm is the bitmap object
            byte[] img = baos.toByteArray ( );
            image_Aadhar = Base64.encodeToString (img, Base64.DEFAULT);

            Bitmap bitmap1 = MediaStore.Images.Media.getBitmap (this.getContentResolver ( ), imagePassport);
            ByteArrayOutputStream baos1 = new ByteArrayOutputStream ( );
            bitmap1 = Bitmap.createScaledBitmap (bitmap1, 500, 750, true);
            bitmap1.compress (Bitmap.CompressFormat.PNG, 80, baos1); //bm is the bitmap object
            byte[] img1 = baos1.toByteArray ( );
            image_Passport = Base64.encodeToString (img1, Base64.DEFAULT);

            Bitmap bitmap2 = MediaStore.Images.Media.getBitmap (this.getContentResolver ( ), imageProfile);
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream ( );
            bitmap2 = Bitmap.createScaledBitmap (bitmap2, 500, 750, true);
            bitmap2.compress (Bitmap.CompressFormat.PNG, 80, baos2); //bm is the bitmap object
            byte[] img2 = baos2.toByteArray ( );
            image_Profile = Base64.encodeToString (img2, Base64.DEFAULT);

            Bitmap bitmap3 = MediaStore.Images.Media.getBitmap (this.getContentResolver ( ), imageDl);
            ByteArrayOutputStream baos3 = new ByteArrayOutputStream ( );
            bitmap3 = Bitmap.createScaledBitmap (bitmap3, 500, 750, true);
            bitmap3.compress (Bitmap.CompressFormat.PNG, 80, baos3); //bm is the bitmap object
            byte[] img3 = baos3.toByteArray ( );
            image_dl = Base64.encodeToString (img3, Base64.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace ( );
        }


    }

    public void UserRegister(){

        ProgressDialog progressDialog = new ProgressDialog(BankAccountDetails.this);
        progressDialog.setMessage("Registering Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest (Request.Method.POST, URLS.Register_enabler, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );
                    String status = jsonObject.getString ("message");
                    if(status.equals ("Enabler successfully registered")){

                        Toast.makeText (BankAccountDetails.this, status, Toast.LENGTH_SHORT).show ( );

                        Intent intent = new Intent(BankAccountDetails.this, MainActivity.class);
                        startActivity (intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();

                Toast.makeText (BankAccountDetails.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );

                params.put ("enabler_name",name);
                params.put ("enabler_email",emailId);
                params.put ("phone","+91 "+mobileNo);
                params.put ("emergency_contact_number","+91 "+contactNo);
                params.put ("blood_group",bloodGroup);
                params.put ("dl_number",dlno);
                params.put ("enabler_aadhar_number",aadharNo);
                params.put ("enabler_passport_number",passportNo);
                params.put ("password",password);
                params.put ("password confirmation",password);
                params.put ("bank_name",bankName);
                params.put ("ifsc_code",ifcsCode);
                params.put ("account_holder_name",accountHolderName);
                params.put ("account_number",accountNo);
                params.put ("enabler_image",image_Profile);
                params.put ("enabler_aadhar_image",image_Aadhar);
                params.put ("enabler_passport_image",image_Passport);
                params.put ("dl_image",image_dl);

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (BankAccountDetails.this);
        requestQueue.add (request);
    }
}
