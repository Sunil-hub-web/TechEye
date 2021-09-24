package com.example.techeye.enabler.Enabler_SignUP;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PersonalDetails_Enabler extends AppCompatActivity {

    Button btn_next,btn_Profile,btn_DL;
    EditText edit_Name,edit_EmailAddress,edit_MobileNo,edit_EmgContactNo,edit_BloodGroup,edit_DLNumber;
    TextView text_Code1,text_Code2,text_ChooserProfile,text_ChooserDL;

    AwesomeValidation awesomeValidation;
    public static final int REQUEST_GET_SINGLE_IMAGE = 1;
    public static final int REQUEST_GET_SINGLE_IMAGE1 = 2;
    Bitmap bitmap, bitmap1;
    Uri imageUri1, imageUri2;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_personal_details_enabler);

        awesomeValidation = new AwesomeValidation (ValidationStyle.BASIC);

        btn_next = findViewById (R.id.pdnext);
        btn_Profile = findViewById (R.id.btnaadhar);
        btn_DL = findViewById (R.id.btndlnumber);

        edit_Name = findViewById (R.id.name);
        edit_EmailAddress = findViewById (R.id.emailaddress);
        edit_MobileNo = findViewById (R.id.pemobileno);
        edit_EmgContactNo = findViewById (R.id.contactno);
        edit_BloodGroup = findViewById (R.id.bloodGroup);
        edit_DLNumber = findViewById (R.id.dlnumber);
        text_ChooserDL = findViewById (R.id.choosedl);
        text_ChooserProfile = findViewById (R.id.choose);

        awesomeValidation.addValidation (PersonalDetails_Enabler.this,R.id.name,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.name);
        awesomeValidation.addValidation (PersonalDetails_Enabler.this, R.id.emailaddress, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation (PersonalDetails_Enabler.this,R.id.pemobileno,"^[0-9]{1,10}$",R.string.mobilenoerror);
        awesomeValidation.addValidation (PersonalDetails_Enabler.this,R.id.contactno,"^[0-9]{1,10}$",R.string.mobilenoerror);
        awesomeValidation.addValidation (PersonalDetails_Enabler.this,R.id.bloodGroup,"^[A-Za-z]{0,2}[+]{1}$",R.string.bloodgroup);
        awesomeValidation.addValidation (PersonalDetails_Enabler.this,R.id.dlnumber,"^(([A-Z]{2}[0-9]{2})( )|([A-Z]{2}-[0-9]{2}))((19|20)[0-9][0-9])[0-9]{7}$",R.string.dlnumber);


        btn_next.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if(awesomeValidation.validate ()){
                    if(imageUri1 != null && imageUri2 !=null){

                      //emailVerification (edit_EmailAddress.getText ().toString ().trim ());

                        Intent intent = new Intent ( PersonalDetails_Enabler.this,EmailOTPConfirmation_enabler.class );
                        intent.putExtra ("name",edit_Name.getText ().toString ().trim ());
                        intent.putExtra ("email",edit_EmailAddress.getText ().toString ().trim ());
                        intent.putExtra ("mobile",edit_MobileNo.getText ().toString ().trim ());
                        intent.putExtra ("contact",edit_EmgContactNo.getText ().toString ().trim ());
                        intent.putExtra ("bloodgroup",edit_BloodGroup.getText ().toString ().trim ());
                        intent.putExtra ("dlnumber",edit_DLNumber.getText ().toString ().trim ());
                        intent.putExtra("imageuri", imageUri1.toString());
                        intent.putExtra("imageuri1", imageUri2.toString());
                        intent.putExtra("token", "225566");
                        startActivity (intent);
                    }else{
                        Toast.makeText (PersonalDetails_Enabler.this, "select Your image", Toast.LENGTH_SHORT).show ( );
                    }
                }else{
                    Toast.makeText (PersonalDetails_Enabler.this, "validation not success fully", Toast.LENGTH_SHORT).show ( );
                }

            }
        });

        btn_Profile.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_IMAGE);

            }
        });

        btn_DL.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_IMAGE1);

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
                text_ChooserProfile.setText (bitmap.toString ());
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
            text_ChooserDL.setText (bitmap1.toString ( ));

        } else {
            Toast.makeText (this, "Select Image", Toast.LENGTH_SHORT).show ( );
        }
    }

    public void emailVerification(String email){

        ProgressDialog dialog = new ProgressDialog (PersonalDetails_Enabler.this);
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

                        Toast.makeText (PersonalDetails_Enabler.this, "OTP has been sent to your email ID", Toast.LENGTH_SHORT).show ( );

                            Intent intent = new Intent ( PersonalDetails_Enabler.this,EmailOTPConfirmation_enabler.class );
                            intent.putExtra ("name",edit_Name.getText ().toString ().trim ());
                            intent.putExtra ("email",edit_EmailAddress.getText ().toString ().trim ());
                            intent.putExtra ("mobile",edit_MobileNo.getText ().toString ().trim ());
                            intent.putExtra ("contact",edit_EmgContactNo.getText ().toString ().trim ());
                            intent.putExtra ("bloodgroup",edit_BloodGroup.getText ().toString ().trim ());
                            intent.putExtra ("dlnumber",edit_DLNumber.getText ().toString ().trim ());
                            intent.putExtra("imageuri", imageUri1.toString());
                            intent.putExtra("imageuri1", imageUri2.toString());
                            intent.putExtra("token", token);
                            startActivity (intent);

                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ();

                Toast.makeText (PersonalDetails_Enabler.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put("email",email);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (PersonalDetails_Enabler.this);
        requestQueue.add (request);


    }

}