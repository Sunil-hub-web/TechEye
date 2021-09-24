package com.example.techeye.enabler.Enabler_SignUP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.techeye.R;
import com.example.techeye.subscriber.SharedPrefManager;
import com.example.techeye.subscriber.Url.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileDetails extends AppCompatActivity {

    TextView text_Name;
    TextView text_Email;
    TextView text_MobileNo;
    TextView text_BloodGroup;
    TextView text_Bankname;
    TextView text_AccountHolderName;
    TextView text_IFSCCode;
    TextView text_AccountNo;
    TextView text_AadharNo;
    TextView text_PassportNo;
    TextView text_DLNo;
    TextView text_Contactno;

    String str_Name,str_Email,str_MobileNo,str_BloodGroup,str_Bankname,str_AccountHolderName,str_IFSCCode,
            str_AccountNo,tstr_AadharNo,str_PassportNo,str_DLNo,str_Contactno,str_AadharImage,str_ProfileImage,
            str_DLImage,str_PassportImage,userid;

    ImageView aadharImag,passportImage,dlImage,imageView_Back;
    CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user_profile_details);

        text_Name = findViewById (R.id.viewname);
        text_Email = findViewById (R.id.viewemailid);
        text_MobileNo = findViewById (R.id.viewmobileno);
        text_BloodGroup = findViewById (R.id.viewbloodgroup);
        text_Bankname = findViewById (R.id.viewbankname);
        text_AccountHolderName = findViewById (R.id.viewaccountname);
        text_IFSCCode = findViewById (R.id.viewifsccode);
        text_AccountNo = findViewById (R.id.viewaccountno);
        text_AadharNo = findViewById (R.id.viewaadharno);
        text_PassportNo = findViewById (R.id.viewpassportnumber);
        text_DLNo = findViewById (R.id.viewdlnumber);
        text_Contactno = findViewById (R.id.contactno);

        aadharImag = findViewById (R.id.imgPreview);
        passportImage = findViewById (R.id.imgPreview2);
        dlImage = findViewById (R.id.imgPreview1);
        profileImage = findViewById (R.id.profile_image);
        imageView_Back = findViewById (R.id.back);

        userid = SharedPrefManager.getInstance (UserProfileDetails.this).getUser ().getId ();

        getUserData (userid);

        imageView_Back.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent (UserProfileDetails.this,Navigation_Activity.class);
                startActivity (intent);
            }
        });


    }

    public void getUserData(String userid){

        StringRequest request = new StringRequest (Request.Method.POST, URLS.getUserDetails_enabler, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject ( response );

                    String status = jsonObject.getString ("Information");

                    JSONArray array = new JSONArray ( status );

                    for(int i=0;i<array.length ();i++){

                        JSONObject object = array.getJSONObject (0);

                        str_Name = object.getString ("enabler_name");
                        str_Email = object.getString ("enabler_email");
                        str_MobileNo = object.getString ("phone");
                        str_Contactno = object.getString ("emergency_contact_number");
                        str_BloodGroup = object.getString ("blood_group");
                        str_DLNo = object.getString ("dl_number");
                        tstr_AadharNo = object.getString ("enabler_aadhar_number");
                        str_PassportNo = object.getString ("enabler_passport_number");
                        str_Bankname = object.getString ("bank_name");
                        str_IFSCCode = object.getString ("ifsc_code");
                        str_AccountHolderName = object.getString ("account_holder_name");
                        str_AccountNo = object.getString ("account_number");
                        str_AadharImage = object.getString ("enabler_aadhar_image");
                        str_PassportImage = object.getString ("enabler_passport_image");
                        str_DLImage = object.getString ("dl_image");
                        str_ProfileImage = object.getString ("enabler_image");

                        text_Name.setText (str_Name);
                        text_Email.setText (str_Email);
                        text_MobileNo.setText (str_MobileNo);
                        text_Contactno.setText (str_Contactno);
                        text_BloodGroup.setText (str_BloodGroup);
                        text_DLNo.setText (str_DLNo);
                        text_AadharNo.setText (tstr_AadharNo);
                        text_PassportNo.setText (str_PassportNo);
                        text_Bankname.setText (str_Bankname);
                        text_IFSCCode.setText (str_IFSCCode);
                        text_AccountHolderName.setText (str_AccountHolderName);
                        text_AccountNo.setText (str_AccountNo);

                        byte[] decodedString = Base64.decode(str_ProfileImage, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        profileImage.setImageBitmap (decodedByte);

                        byte[] decodedString1 = Base64.decode(str_AadharImage, Base64.DEFAULT);
                        Bitmap decodedByte1 = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
                        aadharImag.setImageBitmap (decodedByte1);

                        byte[] decodedString2 = Base64.decode(str_PassportImage, Base64.DEFAULT);
                        Bitmap decodedByte2 = BitmapFactory.decodeByteArray(decodedString2, 0, decodedString2.length);
                        passportImage.setImageBitmap (decodedByte2);

                        byte[] decodedString3 = Base64.decode(str_DLImage, Base64.DEFAULT);
                        Bitmap decodedByte3 = BitmapFactory.decodeByteArray(decodedString3, 0, decodedString3.length);
                        dlImage.setImageBitmap (decodedByte3);


                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText (UserProfileDetails.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );

                params.put ("id",userid);

                return params;
            }
        };

        RequestQueue requestQueue  = Volley.newRequestQueue (UserProfileDetails.this);
        requestQueue.add (request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed ( );
    }
}