package com.example.techeye.enabler.Enabler_SignUP;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techeye.R;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Details extends AppCompatActivity {

    Button btn_Next,btn_AadharImage,btn_PassportImage;
    EditText edit_AadharNo,edit_PassPortNo,edit_Password,edit_Confpassword;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    TextView text_ChooseAadhar,text_Passport;
    public static final int REQUEST_GET_SINGLE_IMAGE = 1;
    public static final int REQUEST_GET_SINGLE_IMAGE1 = 2;
    Bitmap bitmap, bitmap1;
    Uri imageUri1, imageUri2;
    String message = "Password Do not Match!";
    String message1 = "Password Match";
    TextView text_check;

    String name,mbileNo,emailId,dlno,bloodGroup,contactNo;
    Uri imageProfile,imageDl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_details);

        btn_Next = findViewById (R.id.dnext);
        btn_AadharImage = findViewById (R.id.btnaadhar);
        btn_PassportImage = findViewById (R.id.btnpassport);
        edit_Password = findViewById (R.id.password);
        edit_Confpassword = findViewById (R.id.confpassword);
        edit_AadharNo = findViewById (R.id.aadharno);
        edit_PassPortNo = findViewById (R.id.passportno);
        text_ChooseAadhar = findViewById (R.id.chooseaadhar);
        text_Passport = findViewById (R.id.choosepassport);
        checkBox1 = findViewById (R.id.checkBox1);
        checkBox2 = findViewById (R.id.checkBox2);
        checkBox3 = findViewById (R.id.checkBox3);
        checkBox4 = findViewById (R.id.checkBox4);
        text_check = findViewById (R.id.check);


        try{

            name = getIntent ().getStringExtra ("name");
            mbileNo = getIntent ().getStringExtra ("mobile");
            emailId = getIntent ().getStringExtra ("email");
            dlno = getIntent ().getStringExtra ("dlnumber");
            bloodGroup = getIntent ().getStringExtra ("bloodgroup");
            contactNo = getIntent ().getStringExtra ("contact");
            imageProfile = Uri.parse (getIntent ( ).getExtras ( ).getString ("imageuri"));
            imageDl = Uri.parse (getIntent ( ).getExtras ( ).getString ("imageuri1"));

        }catch (Exception e){
            e.printStackTrace ();
        }
        btn_Next.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (edit_AadharNo.getText ().toString ().trim ().length ()==12){

                    if(imageUri1 !=null && imageUri2 !=null){

                        Intent intent = new Intent ( Details.this,BankAccountDetails.class );
                        intent.putExtra ("name",name);
                        intent.putExtra ("email",emailId);
                        intent.putExtra ("mobile",mbileNo);
                        intent.putExtra ("contact",contactNo);
                        intent.putExtra ("bloodgroup",bloodGroup);
                        intent.putExtra ("dlnumber",dlno);
                        intent.putExtra("aadharno", edit_AadharNo.getText ().toString ().trim ());
                        intent.putExtra("passportno", edit_PassPortNo.getText ().toString ().trim ());
                        intent.putExtra("password", edit_Password.getText ().toString ().trim ());
                        intent.putExtra("imageuri1", imageUri1.toString());
                        intent.putExtra("imageuri2", imageUri2.toString());
                        intent.putExtra("imageuri3", imageProfile.toString ());
                        intent.putExtra("imageuri4", imageDl.toString ());

                        startActivity (intent);
                    }
                }else {

                    edit_AadharNo.setError ("enter valide Aadhar No");
                }


            }
        });

        btn_AadharImage.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_IMAGE);
            }
        });

        btn_PassportImage.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( );
                intent.setType ("image/*");
                intent.setAction (Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_IMAGE1);

            }
        });

        edit_Password.addTextChangedListener (new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (edit_Password.getText ( ).toString ( ).trim ( ).length ( ) >= 8 ) {


                    checkBox3.setChecked (true);
                    text_check.setText ("");

                } else {

                    checkBox3.setChecked (false);
                    text_check.setText (message);
                    text_check.setTextColor (Color.RED);

                }

                if (isValidNumericPassword (edit_Password.getText ( ).toString ( ))) {

                    checkBox1.setChecked (true);
                    text_check.setText ("");

                } else {

                    checkBox1.setChecked (false);
                    text_check.setText (message);
                    text_check.setTextColor (Color.RED);
                }

                if (isValidUppercasePassword (edit_Password.getText ( ).toString ( )) &&
                        isValidLowercasePassword (edit_Password.getText ( ).toString ( ))) {

                    checkBox2.setChecked (true);
                    text_check.setText ("");

                } else {

                    checkBox2.setChecked (false);
                    text_check.setText (message);
                    text_check.setTextColor (Color.RED);
                }

                if (isValidSpeialePassword (edit_Password.getText ( ).toString ( ))) {

                    checkBox4.setChecked (true);
                    text_check.setText ("");

                } else {
                    checkBox4.setChecked (false);
                    text_check.setText (message);
                    text_check.setTextColor (Color.RED);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_Confpassword.addTextChangedListener (new TextWatcher ( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (edit_Confpassword.getText ( ).toString ( ).trim().length ( ) >= 8 &&
                        edit_Confpassword.getText ( ).toString ( ).trim ( ).equals (edit_Password.getText ( ).toString ( ).trim ())) {

                    text_check.setText (message1);
                    text_check.setTextColor (Color.GREEN);

                } else {
                    if (edit_Confpassword.getText().toString().length()<=8){
                        text_check.setText ("Password must contain 8 charaters");
                        text_check.setTextColor (Color.RED);
                    }else {
                        text_check.setText (message);
                        text_check.setTextColor (Color.RED);
                    }

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
                text_ChooseAadhar.setText (bitmap.toString ());
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
            text_Passport.setText (bitmap1.toString ( ));

        } else {
            Toast.makeText (this, "Select Image", Toast.LENGTH_SHORT).show ( );
        }
    }

    public boolean isValidSpeialePassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }
    public boolean isValidUppercasePassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[A-Z]).{1,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }

    public boolean isValidLowercasePassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[a-z]).{1,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }
    public boolean isValidNumericPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9]).{4,}$";

        pattern = Pattern.compile (PASSWORD_PATTERN);
        matcher = pattern.matcher (password);

        return matcher.matches ( );

    }
}