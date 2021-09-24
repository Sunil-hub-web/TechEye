package com.example.techeye.enabler.Enabler_SignUP;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.techeye.R;
import com.example.techeye.subscriber.ProfilePage_Subscriber;
import com.example.techeye.subscriber.SharedPrefManager;
import com.example.techeye.subscriber.Url.URLS;
import com.example.techeye.subscriber.viewalldetails.UserPropertyAdapter;
import com.example.techeye.subscriber.viewalldetails.UserProperty_ModelClass;
import com.example.techeye.subscriber.viewalldetails.ViewUserPropertyDetails;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CapturePropertyPhoto extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;
    private static final int PERMISSION_CODE = 1000;
    private static final int PICK_IMAGE_REQUEST = 1;
    public int PIC_CODE = 0;
    private static final int CAMERA_REQUEST = 10001;

    String url = "https://www.rentopool.com/Thirdeye/api/auth/getpropertybyuser";

    TextView txtDateTime, txtLatLngs, txtAddress, txtPostal, radioText, radioText1, radioText2, radioText3, radioText4, radioText5, radioText6, radioText7, radioText8, radioText9, radioText10, radioText11,
            condition, condition1, condition2, condition3, condition4, condition5, condition6, condition7, condition8, condition9, condition10, condition11;
    ImageView imgPreview, imgPreview1, imgPreview2, imgPreview3, imgPreview4, imgPreview5, imgPreview6, imgPreview7, imgPreview8, imgPreview9, imgPreview10, imgPreview11;

    Uri image_uri, image_uri1, image_uri2, image_uri3, image_uri4, image_uri5, image_uri6, image_uri7, image_uri8, image_uri9, image_uri10, image_uri11;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    TextView textView, textView1;
    RadioButton radioButton;
    RadioGroup radioGroup;

    RadioButton radioGate, radioApproachroad, radioFrontage, radioFrontlhs, radioFrontrhs,
            radioLeftBoundary, radioRightBoundary, radioPictureFromBackSide, radioLocality1, radioLocality2,
            radioAnyOther1, radioAnyOther2;

    String selectedPlace, selectedPlace1, selectedPlace2, selectedPlace3, selectedPlace4, selectedPlace5,
            selectedPlace6, selectedPlace7, selectedPlace8, selectedPlace9, selectedPlace10, selectedPlace11,
            checkCondition, checkCondition1, checkCondition2, checkCondition3, checkCondition4, checkCondition5,
            checkCondition6, checkCondition7, checkCondition8, checkCondition9, checkCondition10, checkCondition11;

    String txt_DataTime, txt_LatLngs, txt_Postal, txt_Address, txt_Radio, txt_Condition;

    Bitmap bitmap, bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, bitmap7, bitmap8, bitmap9, bitmap10,
            bitmap11, scaledbMap, scaledbMap1, scaledbMap2, scaledbMap3, scaledbMap4, scaledbMap5, scaledbMap6,
            scaledbMap7, scaledbMap8, scaledbMap9, scaledbMap10, scaledbMap11;

    Button btn_printPdf, button2, btn_saveImage;

    String str_Gate, str_Approachroad, str_Frontage, str_Frontlhs, str_Frontrhs,str_LeftBoundary, str_RightBoundary,
            str_PictureFromBackSide, str_Locality1, str_Locality2, str_AnyOther1, str_AnyOther2;

    ProgressDialog pd;
    File file;
    View dialogLayout;
    LayoutInflater inflater;

    String textView_Data, textView_Data1, textView_Data2, textView_Data3, textView_Data4, textView_Data5, textView_Data6, textView_Data7, textView_Data8, textView_Data9, textView_Data10, textView_Data11;
    String completed = "COMPLETED";
    TextView text_Comp, text_Comp1, text_Comp2, text_Comp3, text_Comp4, text_Comp5, text_Comp6, text_Comp7, text_Comp8, text_Comp9, text_Comp10, text_Comp11;
    String str_UserId,str_EnablerJobId,str_LATITUDE,str_LONGITUDE;

    ArrayList<String> propertyImage = new ArrayList<> (  );
    ArrayList<String> propertyImageName = new ArrayList<> (  );
    ArrayList<String> propertyImageDescription = new ArrayList<> (  );
    ArrayList<PropertyPhoto_ModelClass> list_property = new ArrayList<> (  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_capture_property_photo);

        imgPreview = findViewById(R.id.imgPreview);
        imgPreview1 = findViewById(R.id.imgPreview1);
        imgPreview2 = findViewById(R.id.imgPreview2);
        imgPreview3 = findViewById(R.id.imgPreview3);
        imgPreview4 = findViewById(R.id.imgPreview4);
        imgPreview5 = findViewById(R.id.imgPreview5);
        imgPreview6 = findViewById(R.id.imgPreview6);
        imgPreview7 = findViewById(R.id.imgPreview7);
        imgPreview8 = findViewById(R.id.imgPreview8);
        imgPreview9 = findViewById(R.id.imgPreview9);
        imgPreview10 = findViewById(R.id.imgPreview10);
        imgPreview11 = findViewById(R.id.imgPreview11);

        radioText = findViewById(R.id.radiotext);
        radioText1 = findViewById(R.id.radiotext1);
        radioText2 = findViewById(R.id.radiotext2);
        radioText3 = findViewById(R.id.radiotext3);
        radioText4 = findViewById(R.id.radiotext4);
        radioText5 = findViewById(R.id.radiotext5);
        radioText6 = findViewById(R.id.radiotext6);
        radioText7 = findViewById(R.id.radiotext7);
        radioText8 = findViewById(R.id.radiotext8);
        radioText9 = findViewById(R.id.radiotext9);
        radioText10 = findViewById(R.id.radiotext10);
        radioText11 = findViewById(R.id.radiotext11);

        condition = findViewById(R.id.condition);
        condition1 = findViewById(R.id.condition1);
        condition2 = findViewById(R.id.condition2);
        condition3 = findViewById(R.id.condition3);
        condition4 = findViewById(R.id.condition4);
        condition5 = findViewById(R.id.condition5);
        condition6 = findViewById(R.id.condition6);
        condition7 = findViewById(R.id.condition7);
        condition8 = findViewById(R.id.condition8);
        condition9 = findViewById(R.id.condition9);
        condition10 = findViewById(R.id.condition10);
        condition11 = findViewById(R.id.condition11);

        btn_printPdf = findViewById(R.id.printPdf);
        btn_saveImage = findViewById(R.id.saveImage);

        txtDateTime = findViewById(R.id.txt_desc_datatime);
        txtLatLngs = findViewById(R.id.txt_desc_latlngs);
        txtAddress = findViewById(R.id.txt_desc_address);
        txtPostal = findViewById(R.id.txt_desc_postal);

        str_EnablerJobId = getIntent ().getStringExtra ("id");
        str_UserId = getIntent ().getStringExtra ("str_UserId");
        str_LATITUDE = getIntent ().getStringExtra ("str_LATITUDE");
        str_LONGITUDE = getIntent ().getStringExtra ("str_LONGITUDE");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(CapturePropertyPhoto.this);

        inflater = getLayoutInflater();
        dialogLayout = inflater.inflate(R.layout.propertyphoto, null);

        getUserPropertyDetails (str_UserId);
        showAlert();

        btn_saveImage.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Log.d ("imageproperty",propertyImage.toString () );
                Log.d ("imagename",propertyImageName.toString () );
                Log.d ("imagedesc",propertyImageDescription.toString () );



                String EnablerId = SharedPrefManager.getInstance (CapturePropertyPhoto.this).getUser ().getId ();

                for (int i = 0 ; i < propertyImage.size () ; i++){

                    PropertyPhoto_ModelClass modelClass = new PropertyPhoto_ModelClass (
                            EnablerId,
                            propertyImage.get (i).toString (),
                            propertyImageDescription.get (i).toString (),
                            propertyImageName.get (i).toString ()
                    );

                    list_property.add (modelClass);

                }

                Gson gson = new Gson();
                String json = gson.toJson(list_property);
                Log.d("extra_js",json);

                storeData (json);


            }
        });

    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void cameraOpertions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED) {
                //Permission not enabled request it
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                // show popup to request permission

                requestPermissions(permission, PERMISSION_CODE);

            } else {

                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_LONG).show();
        }

    }

    private void openCamera() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);
    }
    private void openCamera1() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 8);
    }
    private void openCamera2() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 9);
    }
    private void openCamera3() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 10);
    }
    private void openCamera4() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 11);
    }
    private void openCamera5() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 12);
    }
    private void openCamera6() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 13);
    }
    private void openCamera7() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 14);
    }
    private void openCamera8() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 15);
    }
    private void openCamera9() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 16);
    }
    private void openCamera10() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 17);
    }
    private void openCamera11() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 18);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        if (requestCode == 7 && resultCode == RESULT_OK) {

            bitmap = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview.setImageBitmap (bitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream ( );
            bitmap = Bitmap.createScaledBitmap (bitmap, 500, 750, true);
            bitmap.compress (Bitmap.CompressFormat.PNG, 40, baos); //bm is the bitmap object
            byte[] img = baos.toByteArray ( );
            str_Gate = Base64.encodeToString (img, Base64.DEFAULT);

            radioText.setText(selectedPlace);

            propertyImage.add (str_Gate);
            propertyImageName.add (selectedPlace);

            alertCondition1();

        }else if((requestCode == 8 && resultCode == RESULT_OK)){

            bitmap1 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview1.setImageBitmap (bitmap1);

            ByteArrayOutputStream baos1 = new ByteArrayOutputStream ( );
            bitmap1 = Bitmap.createScaledBitmap (bitmap1, 500, 750, true);
            bitmap1.compress (Bitmap.CompressFormat.PNG, 40, baos1); //bm is the bitmap object
            byte[] img1 = baos1.toByteArray ( );
            str_Approachroad = Base64.encodeToString (img1, Base64.DEFAULT);

            propertyImage.add (str_Approachroad);
            propertyImageName.add (selectedPlace1);

            radioText1.setText(selectedPlace1);

            alertCondition1();

        }else if((requestCode == 9 && resultCode == RESULT_OK)){

            bitmap2 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview2.setImageBitmap (bitmap2);

            ByteArrayOutputStream baos2 = new ByteArrayOutputStream ( );
            bitmap2 = Bitmap.createScaledBitmap (bitmap2, 500, 750, true);
            bitmap2.compress (Bitmap.CompressFormat.PNG, 40, baos2); //bm is the bitmap object
            byte[] img2 = baos2.toByteArray ( );
            str_Frontage = Base64.encodeToString (img2, Base64.DEFAULT);

            propertyImage.add (str_Frontage);
            propertyImageName.add (selectedPlace2);

            radioText2.setText(selectedPlace2);

            alertCondition1();

        }else if((requestCode == 10 && resultCode == RESULT_OK)){

            bitmap3 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview3.setImageBitmap (bitmap3);

            ByteArrayOutputStream baos3 = new ByteArrayOutputStream ( );
            bitmap3 = Bitmap.createScaledBitmap (bitmap3, 500, 750, true);
            bitmap3.compress (Bitmap.CompressFormat.PNG, 40, baos3); //bm is the bitmap object
            byte[] img3 = baos3.toByteArray ( );
            str_Frontlhs = Base64.encodeToString (img3, Base64.DEFAULT);

            radioText3.setText(selectedPlace3);

            propertyImage.add (str_Frontlhs);
            propertyImageName.add (selectedPlace3);

            alertCondition1();

        }else if((requestCode == 11 && resultCode == RESULT_OK)){

            bitmap4 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview4.setImageBitmap (bitmap4);

            ByteArrayOutputStream baos4 = new ByteArrayOutputStream ( );
            bitmap4 = Bitmap.createScaledBitmap (bitmap4, 500, 750, true);
            bitmap4.compress (Bitmap.CompressFormat.PNG, 40, baos4); //bm is the bitmap object
            byte[] img4 = baos4.toByteArray ( );
            str_Frontrhs = Base64.encodeToString (img4, Base64.DEFAULT);

            radioText4.setText(selectedPlace4);

            propertyImage.add (str_Frontrhs);
            propertyImageName.add (selectedPlace4);

            alertCondition1();

        }else if((requestCode == 12 && resultCode == RESULT_OK)){

            bitmap5 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview5.setImageBitmap (bitmap5);

            ByteArrayOutputStream baos5 = new ByteArrayOutputStream ( );
            bitmap5 = Bitmap.createScaledBitmap (bitmap5, 500, 750, true);
            bitmap5.compress (Bitmap.CompressFormat.PNG, 40, baos5); //bm is the bitmap object
            byte[] img5 = baos5.toByteArray ( );
            str_LeftBoundary = Base64.encodeToString (img5, Base64.DEFAULT);

            propertyImage.add (str_LeftBoundary);
            propertyImageName.add (selectedPlace5);

            radioText5.setText(selectedPlace5);

            alertCondition1();

        }else if((requestCode == 13 && resultCode == RESULT_OK)){

            bitmap6 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview6.setImageBitmap (bitmap6);

            ByteArrayOutputStream baos6 = new ByteArrayOutputStream ( );
            bitmap6 = Bitmap.createScaledBitmap (bitmap6, 500, 750, true);
            bitmap6.compress (Bitmap.CompressFormat.PNG, 40, baos6); //bm is the bitmap object
            byte[] img6 = baos6.toByteArray ( );
            str_RightBoundary = Base64.encodeToString (img6, Base64.DEFAULT);

            radioText6.setText(selectedPlace6);

            propertyImage.add (str_RightBoundary);
            propertyImageName.add (selectedPlace6);

            alertCondition1();

        }else if((requestCode == 14 && resultCode == RESULT_OK)){

            bitmap7 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview7.setImageBitmap (bitmap7);

            ByteArrayOutputStream baos7 = new ByteArrayOutputStream ( );
            bitmap7 = Bitmap.createScaledBitmap (bitmap7, 500, 750, true);
            bitmap7.compress (Bitmap.CompressFormat.PNG, 40, baos7); //bm is the bitmap object
            byte[] img7 = baos7.toByteArray ( );
            str_PictureFromBackSide = Base64.encodeToString (img7, Base64.DEFAULT);


            radioText7.setText(selectedPlace7);

            propertyImage.add (str_PictureFromBackSide);
            propertyImageName.add (selectedPlace7);

            alertCondition1();

        }else if((requestCode == 15 && resultCode == RESULT_OK)){

            bitmap8 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview8.setImageBitmap (bitmap8);

            ByteArrayOutputStream baos8 = new ByteArrayOutputStream ( );
            bitmap8 = Bitmap.createScaledBitmap (bitmap8, 500, 750, true);
            bitmap8.compress (Bitmap.CompressFormat.PNG, 40, baos8); //bm is the bitmap object
            byte[] img8 = baos8.toByteArray ( );
            str_Locality1 = Base64.encodeToString (img8, Base64.DEFAULT);

            radioText8.setText(selectedPlace8);

            propertyImage.add (str_Locality1);
            propertyImageName.add (selectedPlace8);

            alertCondition2();

        }else if((requestCode == 16 && resultCode == RESULT_OK)){

            bitmap9 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview9.setImageBitmap (bitmap9);

            ByteArrayOutputStream baos9 = new ByteArrayOutputStream ( );
            bitmap9 = Bitmap.createScaledBitmap (bitmap9, 500, 750, true);
            bitmap9.compress (Bitmap.CompressFormat.PNG, 40, baos9); //bm is the bitmap object
            byte[] img9 = baos9.toByteArray ( );
            str_Locality2 = Base64.encodeToString (img9, Base64.DEFAULT);

            radioText9.setText(selectedPlace9);

            propertyImage.add (str_Locality2);
            propertyImageName.add (selectedPlace9);

            alertCondition2();

        }else if((requestCode == 17 && resultCode == RESULT_OK)){

            bitmap10 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview10.setImageBitmap (bitmap10);

            ByteArrayOutputStream baos10 = new ByteArrayOutputStream ( );
            bitmap10 = Bitmap.createScaledBitmap (bitmap10, 500, 750, true);
            bitmap10.compress (Bitmap.CompressFormat.PNG, 40, baos10); //bm is the bitmap object
            byte[] img10 = baos10.toByteArray ( );
            str_AnyOther1 = Base64.encodeToString (img10, Base64.DEFAULT);

            radioText10.setText(selectedPlace10);

            propertyImage.add (str_AnyOther1);
            propertyImageName.add (selectedPlace10);

            alertCondition2();

        }else if((requestCode == 18 && resultCode == RESULT_OK)){

            bitmap11 = (Bitmap) data.getExtras ( ).get ("data");
            Toast.makeText (this, "Image : "+bitmap, Toast.LENGTH_SHORT).show ( );
            imgPreview11.setImageBitmap (bitmap11);

            ByteArrayOutputStream baos11 = new ByteArrayOutputStream ( );
            bitmap11 = Bitmap.createScaledBitmap (bitmap11, 500, 750, true);
            bitmap11.compress (Bitmap.CompressFormat.PNG, 40, baos11); //bm is the bitmap object
            byte[] img11 = baos11.toByteArray ( );
            str_AnyOther2 = Base64.encodeToString (img11, Base64.DEFAULT);

            radioText11.setText(selectedPlace11);

            propertyImage.add (str_AnyOther2);
            propertyImageName.add (selectedPlace11);

            alertCondition2();
        }

        textView_Data11 = radioText11.getText().toString().trim();
        textView_Data10 = radioText10.getText().toString().trim();
        textView_Data9 = radioText9.getText().toString().trim();
        textView_Data8 = radioText8.getText().toString().trim();
        textView_Data7 = radioText7.getText().toString().trim();
        textView_Data6 = radioText6.getText().toString().trim();
        textView_Data5 = radioText5.getText().toString().trim();
        textView_Data4 = radioText4.getText().toString().trim();
        textView_Data3 = radioText3.getText().toString().trim();
        textView_Data2 = radioText2.getText().toString().trim();
        textView_Data1 = radioText1.getText().toString().trim();
        textView_Data = radioText.getText().toString().trim();

    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CapturePropertyPhoto.this);
        builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>Open Camera</font>"));
        builder.setMessage(Html.fromHtml("<font color='#FFFFFF'>What Do You Want Capture Photo Or Record Video</font>"));
        builder.setPositiveButton(Html.fromHtml("<font color='#FFFFFF'>Capture Photo</font>"),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        cameraOpertions();

                        if (textView_Data == null) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(CapturePropertyPhoto.this);
                            builder.setTitle(Html.fromHtml("<font color='#FFFFFF' >Open Camera</font>"));

                            //radioGroup = dialogLayout.findViewById(R.id.radioGroup);
                            radioGate = dialogLayout.findViewById(R.id.gate);
                            radioApproachroad = dialogLayout.findViewById(R.id.approachroad);
                            radioFrontage = dialogLayout.findViewById(R.id.frontage);
                            radioFrontlhs = dialogLayout.findViewById(R.id.frontlhs);
                            radioFrontrhs = dialogLayout.findViewById(R.id.frontrhs);
                            radioLeftBoundary = dialogLayout.findViewById(R.id.leftboundary);
                            radioRightBoundary = dialogLayout.findViewById(R.id.rightboundary);
                            radioPictureFromBackSide = dialogLayout.findViewById(R.id.picturefrombackside);
                            radioLocality1 = dialogLayout.findViewById(R.id.locality1);
                            radioLocality2 = dialogLayout.findViewById(R.id.locality2);
                            radioAnyOther1 = dialogLayout.findViewById(R.id.anyother1);
                            radioAnyOther2 = dialogLayout.findViewById(R.id.anyother2);

                            text_Comp = dialogLayout.findViewById(R.id.comp);
                            text_Comp1 = dialogLayout.findViewById(R.id.comp1);
                            text_Comp2 = dialogLayout.findViewById(R.id.comp2);
                            text_Comp3 = dialogLayout.findViewById(R.id.comp3);
                            text_Comp4 = dialogLayout.findViewById(R.id.comp4);
                            text_Comp5 = dialogLayout.findViewById(R.id.comp5);
                            text_Comp6 = dialogLayout.findViewById(R.id.comp6);
                            text_Comp7 = dialogLayout.findViewById(R.id.comp7);
                            text_Comp8 = dialogLayout.findViewById(R.id.comp8);
                            text_Comp9 = dialogLayout.findViewById(R.id.comp9);
                            text_Comp10 = dialogLayout.findViewById(R.id.comp10);
                            text_Comp11 = dialogLayout.findViewById(R.id.comp11);

                            radioApproachroad.setEnabled(false);
                            radioFrontage.setEnabled(false);
                            radioFrontlhs.setEnabled(false);
                            radioFrontrhs.setEnabled(false);
                            radioLeftBoundary.setEnabled(false);
                            radioRightBoundary.setEnabled(false);
                            radioPictureFromBackSide.setEnabled(false);
                            radioLocality1.setEnabled(false);
                            radioLocality2.setEnabled(false);
                            radioAnyOther1.setEnabled(false);
                            radioAnyOther2.setEnabled(false);

                            builder.setPositiveButton(Html.fromHtml("<font color='#000000'>Ok</font>"),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            radioList();
                                        }
                                    });
                            builder.setView(dialogLayout);
                            builder.setCancelable(false);
                            AlertDialog dialog1 = builder.create();
                            dialog1.show();
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable (Color.BLACK));
                            Button nbutton = dialog1.getButton(DialogInterface.BUTTON_POSITIVE);
                            //Set negative button background
                            nbutton.setBackgroundResource(R.drawable.button_shap);
                            //Set negative button text color
                            nbutton.setTextColor(Color.BLACK);
                            radioComplitedOption();

                        } else {

                            //Show Your Another AlertDialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(CapturePropertyPhoto.this);
                            builder.setTitle(Html.fromHtml("<font color='#FFFFFF'>Open Camera</font>"));

                            inflater = getLayoutInflater();
                            dialogLayout = inflater.inflate(R.layout.propertyphoto, null);

                            //radioGroup = dialogLayout.findViewById(R.id.radioGroup);
                            radioGate = dialogLayout.findViewById(R.id.gate);
                            radioApproachroad = dialogLayout.findViewById(R.id.approachroad);
                            radioFrontage = dialogLayout.findViewById(R.id.frontage);
                            radioFrontlhs = dialogLayout.findViewById(R.id.frontlhs);
                            radioFrontrhs = dialogLayout.findViewById(R.id.frontrhs);
                            radioLeftBoundary = dialogLayout.findViewById(R.id.leftboundary);
                            radioRightBoundary = dialogLayout.findViewById(R.id.rightboundary);
                            radioPictureFromBackSide = dialogLayout.findViewById(R.id.picturefrombackside);
                            radioLocality1 = dialogLayout.findViewById(R.id.locality1);
                            radioLocality2 = dialogLayout.findViewById(R.id.locality2);
                            radioAnyOther1 = dialogLayout.findViewById(R.id.anyother1);
                            radioAnyOther2 = dialogLayout.findViewById(R.id.anyother2);

                            text_Comp = dialogLayout.findViewById(R.id.comp);
                            text_Comp1 = dialogLayout.findViewById(R.id.comp1);
                            text_Comp2 = dialogLayout.findViewById(R.id.comp2);
                            text_Comp3 = dialogLayout.findViewById(R.id.comp3);
                            text_Comp4 = dialogLayout.findViewById(R.id.comp4);
                            text_Comp5 = dialogLayout.findViewById(R.id.comp5);
                            text_Comp6 = dialogLayout.findViewById(R.id.comp6);
                            text_Comp7 = dialogLayout.findViewById(R.id.comp7);
                            text_Comp8 = dialogLayout.findViewById(R.id.comp8);
                            text_Comp9 = dialogLayout.findViewById(R.id.comp9);
                            text_Comp10 = dialogLayout.findViewById(R.id.comp10);
                            text_Comp11 = dialogLayout.findViewById(R.id.comp11);

                            builder.setPositiveButton(Html.fromHtml("<font color='#000000'>Ok</font>"),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            radioList();
                                        }
                                    });
                            builder.setView(dialogLayout);
                            builder.setCancelable(false);
                            AlertDialog dialog1 = builder.create();
                            dialog1.show();
                            dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                            Button nbutton = dialog1.getButton(DialogInterface.BUTTON_POSITIVE);
                            //Set negative button background
                            nbutton.setBackgroundResource(R.drawable.button_shap);
                            //Set negative button text color
                            nbutton.setTextColor(Color.BLACK);
                            radioComplitedOption();


                        }
                    }
                });
        builder.setNeutralButton(Html.fromHtml("<font color='#FFFFFF'>Record Video</font>"),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        cameraOpertions();

                        Intent intent = new Intent(CapturePropertyPhoto.this, RecordingPropertyVideo.class);
                        startActivity(intent);

                    }
                });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
        // Change the alert dialog background color
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

       /* Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        //Set negative button background
        nbutton.setBackgroundResource(R.drawable.button_shap);
        //Set negative button text color
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        //Set positive button background
        pbutton.setBackgroundResource(R.drawable.button_shap);
        //Set positive button text color
        pbutton.setTextColor(Color.BLACK);*/
    }

    public void radioList() {
        if (radioGate.isChecked()) {
            selectedPlace = radioGate.getText().toString().trim();
            openCamera();
        } else if (radioApproachroad.isChecked()) {
            selectedPlace1 = radioApproachroad.getText().toString().trim();
            openCamera1();
        } else if (radioFrontage.isChecked()) {
            selectedPlace2 = radioFrontage.getText().toString().trim();
            openCamera2();
        } else if (radioFrontlhs.isChecked()) {
            selectedPlace3 = radioFrontlhs.getText().toString().trim();
            openCamera3();
        } else if (radioFrontrhs.isChecked()) {
            selectedPlace4 = radioFrontrhs.getText().toString().trim();
            openCamera4();
        } else if (radioLeftBoundary.isChecked()) {
            selectedPlace5 = radioLeftBoundary.getText().toString().trim();
            openCamera5();
        } else if (radioRightBoundary.isChecked()) {
            selectedPlace6 = radioRightBoundary.getText().toString().trim();
            openCamera6();
        } else if (radioPictureFromBackSide.isChecked()) {
            selectedPlace7 = radioPictureFromBackSide.getText().toString().trim();
            openCamera7();
        } else if (radioLocality1.isChecked()) {
            selectedPlace8 = radioLocality1.getText().toString().trim();
            openCamera8();
        } else if (radioLocality2.isChecked()) {
            selectedPlace9 = radioLocality2.getText().toString().trim();
            openCamera9();
        } else if (radioAnyOther1.isChecked()) {
            selectedPlace10 = radioAnyOther1.getText().toString().trim();
            openCamera10();
        } else if (radioAnyOther2.isChecked()) {
            selectedPlace11 = radioAnyOther2.getText().toString().trim();
            openCamera11();
        } else {
            Toast.makeText(getApplicationContext(), selectedPlace, Toast.LENGTH_LONG).show(); // print the value of selected super star
        }
    }

    public void alertCondition1() {
        //Show Your Another AlertDialog
        final Dialog dialog = new Dialog(CapturePropertyPhoto.this);
        dialog.setContentView(R.layout.alertcondition1);
        dialog.setCancelable(false);
        Button button = dialog.findViewById(R.id.button);
        textView = dialog.findViewById(R.id.editText);
        radioButton = dialog.findViewById(R.id.ok);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButton.setChecked(false);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!radioButton.isChecked()) {

                    if (TextUtils.isEmpty(textView.getText())) {
                        textView.setError("Name is not empty");
                        Toast.makeText(CapturePropertyPhoto.this, "Please Select Radio Button", Toast.LENGTH_SHORT).show();
                    } else {
                        conditionCheck1();
                        dialog.dismiss();
                        showAlert();
                    }
                } else {

                    conditionCheck();
                    dialog.dismiss(); //finish();
                    showAlert();
                }
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void alertCondition2() {
        //Show Your Another AlertDialog
        final Dialog dialog1 = new Dialog(CapturePropertyPhoto.this);
        dialog1.setContentView(R.layout.alertcondition2);
        dialog1.setCancelable(false);
        Button button1 = dialog1.findViewById(R.id.button);
        button2 = dialog1.findViewById(R.id.button1);
        textView1 = dialog1.findViewById(R.id.editText);
        button2.setVisibility(View.INVISIBLE);

        if (radioAnyOther2.isChecked()) {
            button2.setVisibility(View.VISIBLE);
            button1.setVisibility(View.INVISIBLE);
        }

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(textView1.getText())) {
                    textView1.setError("Fill the TextField");
                    Toast.makeText(CapturePropertyPhoto.this, "please fill text view", Toast.LENGTH_SHORT).show();
                } else {
                    conditionCheck2();
                    dialog1.dismiss();
                    showAlert();

                }
                //finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conditionCheck2();
                dialog1.dismiss();
            }
        });

        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void conditionCheck() {
        if (radioButton.isChecked()) {
            if (radioGate.isChecked ()) {
                checkCondition = radioButton.getText().toString();
                condition.setText(checkCondition);
                propertyImageDescription.add (checkCondition);
            } else if (radioApproachroad.isChecked()) {
                checkCondition1 = radioButton.getText().toString();
                condition1.setText(checkCondition1);
                propertyImageDescription.add (checkCondition1);
            } else if (radioFrontage.isChecked()) {
                checkCondition2 = radioButton.getText().toString();
                condition2.setText(checkCondition2);
                propertyImageDescription.add (checkCondition2);
            } else if (radioFrontlhs.isChecked()) {
                checkCondition3 = radioButton.getText().toString();
                condition3.setText(checkCondition3);
                propertyImageDescription.add (checkCondition3);
            } else if (radioFrontrhs.isChecked()) {
                checkCondition4 = radioButton.getText().toString();
                condition4.setText(checkCondition4);
                propertyImageDescription.add (checkCondition4);
            } else if (radioLeftBoundary.isChecked()) {
                checkCondition5 = radioButton.getText().toString();
                condition5.setText(checkCondition5);
                propertyImageDescription.add (checkCondition5);
            } else if (radioRightBoundary.isChecked()) {
                checkCondition6 = radioButton.getText().toString();
                condition6.setText(checkCondition6);
                propertyImageDescription.add (checkCondition6);
            } else if (radioPictureFromBackSide.isChecked()) {
                checkCondition7 = radioButton.getText().toString();
                condition7.setText(checkCondition7);
                propertyImageDescription.add (checkCondition7);
            } else if (radioLocality1.isChecked()) {
                checkCondition8 = radioButton.getText().toString();
                condition8.setText(checkCondition8);
                propertyImageDescription.add (checkCondition8);
            } else if (radioLocality2.isChecked()) {
                checkCondition9 = radioButton.getText().toString();
                condition9.setText(checkCondition9);
                propertyImageDescription.add (checkCondition9);
            } else if (radioAnyOther1.isChecked()) {
                checkCondition10 = radioButton.getText().toString();
                condition10.setText(checkCondition10);
                propertyImageDescription.add (checkCondition10);
            } else if (radioAnyOther2.isChecked()) {
                checkCondition11 = radioButton.getText().toString();
                condition11.setText(checkCondition11);
                propertyImageDescription.add (checkCondition11);
            }

        } else {
            Toast.makeText(this, "Please select radiobutton", Toast.LENGTH_SHORT).show();
        }
    }

    public void conditionCheck1() {
        if (radioGate.isChecked()) {
            checkCondition = textView.getText().toString();
            condition.setText(checkCondition);
            txt_Condition = condition.getText().toString().trim();
            propertyImageDescription.add (checkCondition);
        } else if (radioApproachroad.isChecked()) {
            checkCondition1 = textView.getText().toString();
            condition1.setText(checkCondition1);
            txt_Condition = condition1.getText().toString().trim();
            propertyImageDescription.add (checkCondition1);
        } else if (radioFrontage.isChecked()) {
            checkCondition2 = textView.getText().toString();
            condition2.setText(checkCondition2);
            txt_Condition = condition2.getText().toString().trim();
            propertyImageDescription.add (checkCondition2);
        } else if (radioFrontlhs.isChecked()) {
            checkCondition3 = textView.getText().toString();
            condition3.setText(checkCondition3);
            txt_Condition = condition3.getText().toString().trim();
            propertyImageDescription.add (checkCondition3);
        } else if (radioFrontrhs.isChecked()) {
            checkCondition4 = textView.getText().toString();
            condition4.setText(checkCondition4);
            txt_Condition = condition4.getText().toString().trim();
            propertyImageDescription.add (checkCondition4);
        } else if (radioLeftBoundary.isChecked()) {
            checkCondition5 = textView.getText().toString();
            condition5.setText(checkCondition5);
            txt_Condition = condition5.getText().toString().trim();
            propertyImageDescription.add (checkCondition5);
        } else if (radioRightBoundary.isChecked()) {
            checkCondition6 = textView.getText().toString();
            condition6.setText(checkCondition6);
            txt_Condition = condition6.getText().toString().trim();
            propertyImageDescription.add (checkCondition6);
        } else if (radioPictureFromBackSide.isChecked()) {
            checkCondition7 = textView.getText().toString();
            condition7.setText(checkCondition7);
            txt_Condition = condition7.getText().toString().trim();
            propertyImageDescription.add (checkCondition7);
        } else if (radioLocality1.isChecked()) {
            checkCondition8 = textView.getText().toString();
            condition8.setText(checkCondition8);
            txt_Condition = condition8.getText().toString().trim();
            propertyImageDescription.add (checkCondition8);
        } else if (radioLocality2.isChecked()) {
            checkCondition9 = textView.getText().toString();
            condition9.setText(checkCondition9);
            txt_Condition = condition9.getText().toString().trim();
            propertyImageDescription.add (checkCondition9);
        } else if (radioAnyOther1.isChecked()) {
            checkCondition10 = textView.getText().toString();
            condition10.setText(checkCondition10);
            txt_Condition = condition10.getText().toString().trim();
            propertyImageDescription.add (checkCondition10);
        } else if (radioAnyOther2.isChecked()) {
            checkCondition11 = textView.getText().toString();
            condition11.setText(checkCondition11);
            txt_Condition = condition11.getText().toString().trim();
            propertyImageDescription.add (checkCondition11);
        }
    }

    public void conditionCheck2() {
        if (radioLocality1.isChecked()) {
            checkCondition8 = textView1.getText().toString();
            condition8.setText(checkCondition8);
            txt_Condition = condition8.getText().toString().trim();
            propertyImageDescription.add (checkCondition8);
        } else if (radioLocality2.isChecked()) {
            checkCondition9 = textView1.getText().toString();
            condition9.setText(checkCondition9);
            txt_Condition = condition9.getText().toString().trim();
            propertyImageDescription.add (checkCondition9);
        } else if (radioAnyOther1.isChecked()) {
            checkCondition10 = textView1.getText().toString();
            condition10.setText(checkCondition10);
            txt_Condition = condition10.getText().toString().trim();
            propertyImageDescription.add (checkCondition10);
        } else if (radioAnyOther2.isChecked()) {
            checkCondition11 = textView1.getText().toString();
            condition11.setText(checkCondition11);
            txt_Condition = condition11.getText().toString().trim();
            propertyImageDescription.add (checkCondition11);
        } else {
            Toast.makeText(getApplicationContext(), selectedPlace, Toast.LENGTH_LONG).show(); // print the value of selected super star
        }

    }

    public void radioComplitedOption() {

        if (textView_Data != null) {
            if (textView_Data.equals("Gate")) {
                text_Comp.setText(completed);
                radioGate.setEnabled(false);

                if (textView_Data1.equals("Approach Road")) {

                    text_Comp.setText(completed);
                    text_Comp1.setText(completed);

                    radioGate.setEnabled(false);
                    radioApproachroad.setEnabled(false);

               /*     radioRightBoundary.setEnabled(false);
                    radioPictureFromBackSide.setEnabled(false);
                    radioLocality1.setEnabled(false);
                    radioLocality2.setEnabled(false);
                    radioAnyOther1.setEnabled(false);
                    radioAnyOther2.setEnabled(false);
*/
                    if (textView_Data2.equals("Frontage")) {

                        text_Comp.setText(completed);
                        text_Comp1.setText(completed);
                        text_Comp2.setText(completed);

                        radioGate.setEnabled(false);
                        radioApproachroad.setEnabled(false);
                        radioFrontage.setEnabled(false);

                        if (textView_Data3.equals("Front LHS")) {

                            text_Comp.setText(completed);
                            text_Comp1.setText(completed);
                            text_Comp2.setText(completed);
                            text_Comp3.setText(completed);

                            radioGate.setEnabled(false);
                            radioApproachroad.setEnabled(false);
                            radioFrontage.setEnabled(false);
                            radioFrontlhs.setEnabled(false);

                            if (textView_Data4.equals("Front RHS")) {

                                text_Comp.setText(completed);
                                text_Comp1.setText(completed);
                                text_Comp2.setText(completed);
                                text_Comp3.setText(completed);
                                text_Comp4.setText(completed);

                                radioGate.setEnabled(false);
                                radioApproachroad.setEnabled(false);
                                radioFrontage.setEnabled(false);
                                radioFrontlhs.setEnabled(false);
                                radioFrontrhs.setEnabled(false);

                                if (textView_Data5.equals("Left Boundary")) {

                                    text_Comp.setText(completed);
                                    text_Comp1.setText(completed);
                                    text_Comp2.setText(completed);
                                    text_Comp3.setText(completed);
                                    text_Comp4.setText(completed);
                                    text_Comp5.setText(completed);

                                    radioGate.setEnabled(false);
                                    radioApproachroad.setEnabled(false);
                                    radioFrontage.setEnabled(false);
                                    radioFrontlhs.setEnabled(false);
                                    radioFrontrhs.setEnabled(false);
                                    radioLeftBoundary.setEnabled(false);

                                    if (textView_Data6.equals("Right Boundary")) {

                                        text_Comp.setText(completed);
                                        text_Comp1.setText(completed);
                                        text_Comp2.setText(completed);
                                        text_Comp3.setText(completed);
                                        text_Comp4.setText(completed);
                                        text_Comp5.setText(completed);
                                        text_Comp6.setText(completed);

                                        radioGate.setEnabled(false);
                                        radioApproachroad.setEnabled(false);
                                        radioFrontage.setEnabled(false);
                                        radioFrontlhs.setEnabled(false);
                                        radioFrontrhs.setEnabled(false);
                                        radioLeftBoundary.setEnabled(false);
                                        radioRightBoundary.setEnabled(false);

                                        if (textView_Data7.equals("Picture From Back Side")) {

                                            text_Comp.setText(completed);
                                            text_Comp1.setText(completed);
                                            text_Comp2.setText(completed);
                                            text_Comp3.setText(completed);
                                            text_Comp4.setText(completed);
                                            text_Comp5.setText(completed);
                                            text_Comp6.setText(completed);
                                            text_Comp7.setText(completed);

                                            radioGate.setEnabled(false);
                                            radioApproachroad.setEnabled(false);
                                            radioFrontage.setEnabled(false);
                                            radioFrontlhs.setEnabled(false);
                                            radioFrontrhs.setEnabled(false);
                                            radioLeftBoundary.setEnabled(false);
                                            radioRightBoundary.setEnabled(false);
                                            radioPictureFromBackSide.setEnabled(false);

                                            if (textView_Data8.equals("Locality1")) {

                                                text_Comp.setText(completed);
                                                text_Comp1.setText(completed);
                                                text_Comp2.setText(completed);
                                                text_Comp3.setText(completed);
                                                text_Comp4.setText(completed);
                                                text_Comp5.setText(completed);
                                                text_Comp6.setText(completed);
                                                text_Comp7.setText(completed);
                                                text_Comp8.setText(completed);

                                                radioGate.setEnabled(false);
                                                radioApproachroad.setEnabled(false);
                                                radioFrontage.setEnabled(false);
                                                radioFrontlhs.setEnabled(false);
                                                radioFrontrhs.setEnabled(false);
                                                radioLeftBoundary.setEnabled(false);
                                                radioRightBoundary.setEnabled(false);
                                                radioPictureFromBackSide.setEnabled(false);
                                                radioLocality1.setEnabled(false);

                                                if (textView_Data9.equals("Locality2")) {

                                                    text_Comp.setText(completed);
                                                    text_Comp1.setText(completed);
                                                    text_Comp2.setText(completed);
                                                    text_Comp3.setText(completed);
                                                    text_Comp4.setText(completed);
                                                    text_Comp5.setText(completed);
                                                    text_Comp6.setText(completed);
                                                    text_Comp7.setText(completed);
                                                    text_Comp8.setText(completed);
                                                    text_Comp9.setText(completed);

                                                    radioGate.setEnabled(false);
                                                    radioApproachroad.setEnabled(false);
                                                    radioFrontage.setEnabled(false);
                                                    radioFrontlhs.setEnabled(false);
                                                    radioFrontrhs.setEnabled(false);
                                                    radioLeftBoundary.setEnabled(false);
                                                    radioRightBoundary.setEnabled(false);
                                                    radioPictureFromBackSide.setEnabled(false);
                                                    radioLocality1.setEnabled(false);
                                                    radioLocality2.setEnabled(false);

                                                    if (textView_Data10.equals("Any Other1")) {

                                                        text_Comp.setText(completed);
                                                        text_Comp1.setText(completed);
                                                        text_Comp2.setText(completed);
                                                        text_Comp3.setText(completed);
                                                        text_Comp4.setText(completed);
                                                        text_Comp5.setText(completed);
                                                        text_Comp6.setText(completed);
                                                        text_Comp7.setText(completed);
                                                        text_Comp8.setText(completed);
                                                        text_Comp9.setText(completed);
                                                        text_Comp10.setText(completed);

                                                        radioGate.setEnabled(false);
                                                        radioApproachroad.setEnabled(false);
                                                        radioFrontage.setEnabled(false);
                                                        radioFrontlhs.setEnabled(false);
                                                        radioFrontrhs.setEnabled(false);
                                                        radioLeftBoundary.setEnabled(false);
                                                        radioRightBoundary.setEnabled(false);
                                                        radioPictureFromBackSide.setEnabled(false);
                                                        radioLocality1.setEnabled(false);
                                                        radioLocality2.setEnabled(false);
                                                        radioAnyOther1.setEnabled(false);

                                                        if (textView_Data11.equals("Any Other2")) {

                                                            text_Comp.setText(completed);
                                                            text_Comp1.setText(completed);
                                                            text_Comp2.setText(completed);
                                                            text_Comp3.setText(completed);
                                                            text_Comp4.setText(completed);
                                                            text_Comp5.setText(completed);
                                                            text_Comp6.setText(completed);
                                                            text_Comp7.setText(completed);
                                                            text_Comp8.setText(completed);
                                                            text_Comp9.setText(completed);
                                                            text_Comp10.setText(completed);
                                                            text_Comp11.setText(completed);

                                                            radioGate.setEnabled(false);
                                                            radioApproachroad.setEnabled(false);
                                                            radioFrontage.setEnabled(false);
                                                            radioFrontlhs.setEnabled(false);
                                                            radioFrontrhs.setEnabled(false);
                                                            radioLeftBoundary.setEnabled(false);
                                                            radioRightBoundary.setEnabled(false);
                                                            radioPictureFromBackSide.setEnabled(false);
                                                            radioLocality1.setEnabled(false);
                                                            radioLocality2.setEnabled(false);
                                                            radioAnyOther1.setEnabled(false);
                                                            radioAnyOther2.setEnabled(false);

                                                        }

                                                    }

                                                } else {
                                                    Toast.makeText(this, "selected Plase 9 is null", Toast.LENGTH_LONG).show();
                                                }

                                            } else {
                                                Toast.makeText(this, "selected Plase 8 is null", Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Toast.makeText(this, "selected Plase 7 is null", Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        Toast.makeText(this, "selected Plase 6 is null", Toast.LENGTH_LONG).show();
                                    }

                                } else {
                                    Toast.makeText(this, "selected Plase 5 is null", Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Toast.makeText(this, "selected Plase 4 is null", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(this, "selected Plase 3 is null", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "selected Plase 2 is null", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this, "selected Plase 1 is null", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "selected Plase is null", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "selected Plase is null", Toast.LENGTH_LONG).show();
        }
    }


    public void storeData(String propertyImage){

        ProgressDialog progressDialog = new ProgressDialog(CapturePropertyPhoto.this);
        progressDialog.setMessage("Storing Image Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest (Request.Method.POST, URLS.storePropertyImage, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss ();

                try {
                    JSONObject jsonObject = new JSONObject ( response );

                    String status = jsonObject.getString ("message");

                    if(status.equals ("Picture stored")){
                        Toast.makeText (CapturePropertyPhoto.this, status, Toast.LENGTH_SHORT).show ( );
                    }

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }

            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss ();

                Toast.makeText (CapturePropertyPhoto.this,error.getMessage (), Toast.LENGTH_SHORT).show ( );

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put ("property_images",propertyImage);
                params.put ("id",str_EnablerJobId);

                return params;
            }
        };

        request.setRetryPolicy (new DefaultRetryPolicy ( 50000,100,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ));

        RequestQueue requestQueue = Volley.newRequestQueue (CapturePropertyPhoto.this);
        requestQueue.add (request);

    }

    public void getUserPropertyDetails(String userid){

        //userProperties.clear ();
        ProgressDialog dialog = new ProgressDialog (CapturePropertyPhoto.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();

        StringRequest request = new StringRequest (Request.Method.POST, url, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                dialog.dismiss ();

                try {
                    JSONObject object = new JSONObject ( response );

                    String information = object.getString ("Information");

                        JSONArray array = new JSONArray (information);

                        for (int i = 0; i < array.length ( ); i++) {

                            JSONObject object1 = array.getJSONObject (i);

                            String address = object1.getString ("address");

                            DateFormat df = new SimpleDateFormat ("EEE, d MMM yyyy, HH:mm");
                            String date = df.format (Calendar.getInstance ( ).getTime ( ));

                            txtDateTime.setText ("Date&Time :" + date);
                            txtLatLngs.setText ("la :" + str_LATITUDE + " ,lo :" + str_LONGITUDE);
                            //txtPostal.setText ("Pin_Code :" + postal1);
                            address = address.replace ("Address:","");
                            txtAddress.setText (address);

                        }

                } catch (JSONException e) {
                    e.printStackTrace ( );
                }


            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss ();

                Toast.makeText (CapturePropertyPhoto.this, "Error"+error, Toast.LENGTH_SHORT).show ( );

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );

                params.put ("user_id",userid);
                return params                                                                           ;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (CapturePropertyPhoto.this);
        requestQueue.add (request);
    }

}