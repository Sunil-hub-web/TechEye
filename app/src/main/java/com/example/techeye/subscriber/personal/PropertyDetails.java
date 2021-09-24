package com.example.techeye.subscriber.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.techeye.R;
import com.example.techeye.subscriber.PersonalDetails;
import com.example.techeye.subscriber.personal.Modelclass.City_ModelClass;
import com.example.techeye.subscriber.personal.Modelclass.Country_ModelClass;
import com.example.techeye.subscriber.personal.Modelclass.State_ModelClass;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PropertyDetails extends AppCompatActivity {

    Button btn_next;

    Spinner spinner_reason_for,spinner_country,
            spinner_state,spinner_city,spinner_plotsize;
    ArrayList<Country_ModelClass> list_country;
    ArrayList<State_ModelClass>list_state;
    ArrayList<City_ModelClass>list_city;
    String[] reason = { "Choose your property type", "Residential plot", "Commercial Plot", "Agaricultural Land", "Individual House","Commercial Building","Under Constrution House/Apartment"};
    String[] sqft = { "<= 2000 sqft", "2000 - 10000 sqft", "Above 10000 sqft"};
    String Country,State,City,reasonoftecheye,property_size,Pincode,message,password,property_type;
    RelativeLayout rl_layout;
    EditText et_pincode;
    TextView txt_plotsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_property_details);

        Intent intent = getIntent ();
        message = intent.getStringExtra ("message");
        password = intent.getStringExtra ("password");

        Initialize();

        GetCountry();

        btn_next.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                if (!reasonoftecheye.equals("Choose your property type") &&
                    property_size!=null &&
                    Country!=null &&
                    State!=null &&
                    City!=null){

                    if(et_pincode.getText ().toString ().trim ().equals ("")){



                        Intent intent = new Intent(PropertyDetails.this, PersonalDetails.class);
                        intent.putExtra("reasonoftecheye",reasonoftecheye);
                        intent.putExtra("property_size",property_size);
                        intent.putExtra("Country",Country);
                        intent.putExtra("State",State);
                        intent.putExtra("City",City);
                        intent.putExtra("Pincode","null");
                        intent.putExtra("message",message);
                        intent.putExtra("password",password);
                        intent.putExtra("property_type",property_type);
                        startActivity(intent);

                    }else{

                        Intent intent = new Intent(PropertyDetails.this, PersonalDetails.class);
                        intent.putExtra("reasonoftecheye",reasonoftecheye);
                        intent.putExtra("property_size",property_size);
                        intent.putExtra("Country",Country);
                        intent.putExtra("State",State);
                        intent.putExtra("City",City);
                        intent.putExtra("Pincode",et_pincode.getText().toString().trim());
                        intent.putExtra("message",message);
                        intent.putExtra("password",password);
                        intent.putExtra("property_type",property_type);
                        startActivity(intent);

                    }

                }else {

                    Snackbar.make(rl_layout, "Please fill all the fields", Snackbar.LENGTH_LONG)
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }


            }
        });


    }

    private void Initialize() {
        spinner_reason_for = findViewById(R.id.spinner_reason_for);
        spinner_country = findViewById(R.id.spinner_country);
        spinner_state = findViewById(R.id.spinner_state);
        spinner_city = findViewById(R.id.spinner_city);
        spinner_plotsize = findViewById(R.id.spinner_plotsize);
        list_country = new ArrayList<>();
        list_state = new ArrayList<>();
        list_city = new ArrayList<>();
        btn_next = findViewById (R.id.next);
        rl_layout = findViewById (R.id.rl_layout);
        et_pincode = findViewById (R.id.et_pincode);
        txt_plotsize = findViewById (R.id.txt_plotsize);


        ArrayAdapter reson_for_techeye_adapter = new ArrayAdapter(this,R.layout.spiner_text,reason);
        reson_for_techeye_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_reason_for.setAdapter(reson_for_techeye_adapter);

        spinner_reason_for.setSelection(-1,true);

        ArrayAdapter sqft_adapter = new ArrayAdapter(this,R.layout.spiner_text,sqft);
        sqft_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_plotsize.setAdapter(sqft_adapter);


        spinner_reason_for.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


                        Object item = parent.getItemAtPosition(pos);
                        reasonoftecheye = item.toString();

                        if (reasonoftecheye.equals("Residential plot") ||
                                reasonoftecheye.equals("Commercial Plot") ||
                                reasonoftecheye.equals("Agaricultural Land")){
                            property_type = "PlotSize";
                            txt_plotsize.setText("Size of your property(Plot size)");
                        }else if (reasonoftecheye.equals("Individual House") ||
                                reasonoftecheye.equals("Commercial Building") ||
                                reasonoftecheye.equals("Under Constrution House/Apartment")){
                            txt_plotsize.setText("Size of your property(Carpet area)");
                            property_type = "CarpetArea";
                        }else {
                            txt_plotsize.setText("Size of your property");
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        spinner_plotsize.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


                        Object item = parent.getItemAtPosition(pos);
                        property_size = item.toString();

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    public void GetCountry(){
        list_country.clear();
        ProgressDialog dialog = new ProgressDialog(PropertyDetails.this);
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://rentopool.com/Thirdeye/api/auth/getcountry", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("success")){

                        String country = object.getString("country");

                        JSONArray array = new JSONArray(country);

                        for (int i = 0 ; i < array.length() ; i++){

                            JSONObject jsonObject = array.getJSONObject(i);

                            Country_ModelClass country_modelClass = new Country_ModelClass(
                                    jsonObject.getString("country")
                            );
                            list_country.add(country_modelClass);
                        }

                        CountrySpinnerAdapter adapter = new CountrySpinnerAdapter(PropertyDetails.this,
                                R.layout.spinner_textview,list_country);
                        spinner_country.setAdapter(adapter);

                    }
                }    catch (JSONException e) {
                    e.printStackTrace();
                }

                spinner_country.setSelection(-1,true);

                spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {

                        try {

                            Country_ModelClass mystate=(Country_ModelClass) parent.getSelectedItem();

                             Country = mystate.getCountry();

                            GetState(Country);


                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    } // to close the onItemSelected
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(PropertyDetails.this);
        requestQueue.add(stringRequest);

    }

    public void GetState(String CountryName){
        list_state.clear();
        ProgressDialog dialog = new ProgressDialog(PropertyDetails.this);
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://rentopool.com/Thirdeye/api/auth/getstate?country="+CountryName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("success")){

                        String country = object.getString("state");

                        JSONArray array = new JSONArray(country);

                        for (int i = 0 ; i < array.length() ; i++){

                            JSONObject jsonObject = array.getJSONObject(i);

                            State_ModelClass state_modelClass = new State_ModelClass(
                                    jsonObject.getString("state")
                            );
                            list_state.add(state_modelClass);
                        }

                        StateSpinnerAdapter adapter = new StateSpinnerAdapter(PropertyDetails.this,
                                R.layout.spinner_textview,list_state);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_state.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                spinner_state.setSelection(-1,true);

                spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {

                        try {

                            State_ModelClass mystate=(State_ModelClass) parent.getSelectedItem();

                             State = mystate.getState();

                            GetCity(State);


                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    } // to close the onItemSelected
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(PropertyDetails.this);
        requestQueue.add(stringRequest);

    }

    public void GetCity(String StateName){
        list_city.clear();
        ProgressDialog dialog = new ProgressDialog(PropertyDetails.this);
        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://rentopool.com/Thirdeye/api/auth/getcity?state="+StateName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String status = object.getString("status");
                    if (status.equals("success")){

                        String country = object.getString("city");

                        JSONArray array = new JSONArray(country);

                        for (int i = 0 ; i < array.length() ; i++){

                            JSONObject jsonObject = array.getJSONObject(i);

                            City_ModelClass city_modelClass = new City_ModelClass(
                                    jsonObject.getString("city")
                            );
                            list_city.add(city_modelClass);
                        }

                        CitySpinnerAdapter adapter = new CitySpinnerAdapter(PropertyDetails.this,
                                R.layout.spinner_textview,list_city);
                        spinner_city.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                spinner_city.setSelection(-1,true);

                spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                    {

                        try {

                            City_ModelClass mystate=(City_ModelClass) parent.getSelectedItem();

                             City = mystate.getCity();

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    } // to close the onItemSelected
                    public void onNothingSelected(AdapterView<?> parent)
                    {

                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(PropertyDetails.this);
        requestQueue.add(stringRequest);

    }

}