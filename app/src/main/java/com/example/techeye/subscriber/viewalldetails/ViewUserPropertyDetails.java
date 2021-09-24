package com.example.techeye.subscriber.viewalldetails;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.techeye.subscriber.Kyc;
import com.example.techeye.subscriber.Url.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewUserPropertyDetails extends AppCompatActivity implements UserPropertyAdapter.ContactsAdapterListener {

    RecyclerView recyclerView;
    UserPropertyAdapter userPropertyAdapter;
    ArrayList<UserProperty_ModelClass> userProperties;
    ArrayList<UserId_ModelClass> userIdDetails;
    ArrayAdapter<String> adapter;
    ListView listView;

    String url = "https://www.rentopool.com/Thirdeye/api/auth/getpropertybyuser";
    String user_id;
    String email ;
    Button btn_Delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_view_user_property_details);

        recyclerView = findViewById (R.id.recycler);

        email = SharedPrefManager.getInstance (ViewUserPropertyDetails.this).getUser ().getEmail ();

        userProperties = new ArrayList<> (  );
        userIdDetails = new ArrayList<> (  );

        getUserIdDetails(email);
    }

    public void getUserIdDetails(String email){
        userIdDetails.clear ();
        ProgressDialog dialog = new ProgressDialog (ViewUserPropertyDetails.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();

        StringRequest request = new StringRequest(Request.Method.POST, URLS.getuserdetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(AccountDetails.this, ""+response, Toast.LENGTH_SHORT).show();
                dialog.dismiss ();
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String statues = jsonObject.getString ("Information");

                    JSONArray array = new JSONArray ( statues );

                    for(int i = 0;i<array.length ();i++){

                        JSONObject jsonObject1 = array.getJSONObject (i);

                        UserId_ModelClass userId_modelClass = new UserId_ModelClass (
                                jsonObject1.getString ("id"));

                        userIdDetails.add (userId_modelClass);

                    }
                    Log.d ("userdetails",userIdDetails.toString () );


                    //Toast.makeText (ViewUserPropertyDetails.this, ""+ , Toast.LENGTH_SHORT).show ( );

                    AlertDialog.Builder alertDialog = new
                            AlertDialog.Builder(ViewUserPropertyDetails.this);
                    View rowList = getLayoutInflater().inflate(R.layout.get_user_id, null);
                    listView = rowList.findViewById(R.id.list);
                    btn_Delete = rowList.findViewById (R.id.delete);
                    adapter = new ArrayAdapter(ViewUserPropertyDetails.this, R.layout.list_text, userIdDetails);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    alertDialog.setView(rowList);
                    AlertDialog altdialog = alertDialog.create();
                    altdialog.show();

                    listView.setOnItemClickListener (new AdapterView.OnItemClickListener ( ) {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            UserId_ModelClass userIdModelClass = userIdDetails.get (position);

                            user_id = userIdModelClass.getId ();
                            getUserPropertyDetails(user_id);
                            Toast.makeText (ViewUserPropertyDetails.this, ""+userIdModelClass.getId (), Toast.LENGTH_SHORT).show ( );

                            altdialog.dismiss ();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss ();
                Toast.makeText(ViewUserPropertyDetails.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("email",email);

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ViewUserPropertyDetails.this);
        requestQueue.add(request);



    }

    public void getUserPropertyDetails(String userid){

        //userProperties.clear ();
        ProgressDialog dialog = new ProgressDialog (ViewUserPropertyDetails.this);
        dialog.setMessage ("Retriving...");
        dialog.show ();

        StringRequest request = new StringRequest (Request.Method.POST, url, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                dialog.dismiss ();

                try {
                    JSONObject object = new JSONObject ( response );

                    String information = object.getString ("Information");
                    if (information.equals ("[]")){
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewUserPropertyDetails.this)
                                .setMessage("Geofence is not done yet. Please do geofence of this property.")
                                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        startActivity(new Intent(ViewUserPropertyDetails.this, ProfilePage_Subscriber.class));
                                    }
                                });
                        AlertDialog alert11 = alertDialog.create();
                        alert11.show();
                    }else {
                        JSONArray array = new JSONArray (information);

                        for (int i = 0; i < array.length ( ); i++) {

                            JSONObject object1 = array.getJSONObject (i);

                            UserProperty_ModelClass userProperty = new UserProperty_ModelClass (
                                    object1.getString ("user_id"),
                                    object1.getString ("id"),
                                    object1.getString ("address"),
                                    object1.getString ("co_ordinates"));

                            userProperties.add (userProperty);

                        }
                        Log.d ("property", userProperties.toString ( ));

                        userPropertyAdapter = new UserPropertyAdapter (ViewUserPropertyDetails.this, userProperties, ViewUserPropertyDetails.this);
                        recyclerView.setLayoutManager (new LinearLayoutManager (ViewUserPropertyDetails.this));
                        recyclerView.setHasFixedSize (true);
                        recyclerView.setAdapter (userPropertyAdapter);

                    }

                } catch (JSONException e) {
                    e.printStackTrace ( );
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewUserPropertyDetails.this)
                            .setMessage("Geofence is not done yet. Please do geofence of this property.")
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent (ViewUserPropertyDetails.this, ProfilePage_Subscriber.class));
                                }
                            });
                    AlertDialog alert11 = alertDialog.create();
                    alert11.show();
                }


            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss ();

                Toast.makeText (ViewUserPropertyDetails.this, "Error"+error, Toast.LENGTH_SHORT).show ( );

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
        RequestQueue requestQueue = Volley.newRequestQueue (ViewUserPropertyDetails.this);
        requestQueue.add (request);
    }

    @Override
    public void onContactSelected(UserProperty_ModelClass userProperty_modelClass) {



        StringRequest stringRequest = new StringRequest (Request.Method.POST, URLS.deleteProperty, new Response.Listener<String> ( ) {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject ( response );

                    String statues = jsonObject.getString ("message");
                    if(statues.equals ("Property Deleted.")){
                        Toast.makeText (ViewUserPropertyDetails.this, "delete success", Toast.LENGTH_SHORT).show ( );
                        getUserIdDetails (email);


                    }else{
                        Toast.makeText (ViewUserPropertyDetails.this, "delete not success", Toast.LENGTH_SHORT).show ( );
                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }




            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText (ViewUserPropertyDetails.this, ""+error, Toast.LENGTH_SHORT).show ( );

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<> (  );
                params.put ("user_id",userProperty_modelClass.getUserId ());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy ( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue (ViewUserPropertyDetails.this);
        requestQueue.add (stringRequest);
    }
}