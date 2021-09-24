package com.example.techeye.subscriber;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.techeye.MainActivity;
import com.example.techeye.subscriber.personal.Login_ModelClass;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_id = "keyid";
    private static final String KEY_password = "keypassword";
    private static final String KEY_name = "keyfirst_name";
    private static final String KEY_email = "keylast_name";
    private static final String KEY_mobile_number = "keymobile_number";
    private static final String KEY_aadhar_number = "keyaadhar_number";
    private static final String KEY_passport_number = "keypassport_number";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user register
    //this method will store the user data in shared preferences
    public void userLogin(Login_ModelClass login_modelClass) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(KEY_id,                login_modelClass.getId ());
        editor.putString(KEY_password,                login_modelClass.getPassword ());
        editor.putString(KEY_name,        login_modelClass.getName ());
        editor.putString(KEY_email,         login_modelClass.getEmail ());
        editor.putString(KEY_mobile_number,     login_modelClass.getMobileno ());
        editor.putString(KEY_aadhar_number,     login_modelClass.getAadhar ());
        editor.putString(KEY_passport_number,     login_modelClass.getPassport ());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_id, null) != null;
    }

    //this method will give the logged in user
    public Login_ModelClass getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Login_ModelClass(


                sharedPreferences.getString(KEY_id, null),
                sharedPreferences.getString(KEY_password, null),
                sharedPreferences.getString(KEY_name, null),
                sharedPreferences.getString(KEY_email, null),
                sharedPreferences.getString(KEY_mobile_number, null),
                sharedPreferences.getString(KEY_aadhar_number, null),
                sharedPreferences.getString(KEY_passport_number, null)


        );

    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent (mCtx, MainActivity.class));
    }



}
