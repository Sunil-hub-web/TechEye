package com.example.techeye.enabler.Enabler_SignUP;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.techeye.R;
import com.example.techeye.subscriber.SharedPrefManager;
import com.example.techeye.subscriber.Url.URLS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class EnablerJobRequest extends AppCompatActivity {

    RecyclerView recyclerView;
    JobRequestAdapter jobRequestAdapter;
    String id;
    ArrayList<JobRequest_ModelClass> jobrequest = new ArrayList<> (  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_enabler_job_request);

        recyclerView = findViewById (R.id.jobRequest);
        recyclerView.setHasFixedSize (true);
        recyclerView.setLayoutManager (new LinearLayoutManager (EnablerJobRequest.this));

        id = SharedPrefManager.getInstance (EnablerJobRequest.this).getUser ().getId ();
        getJobRequest(id);
    }

    public void getJobRequest(String enablerid){

        ProgressDialog dialog = new ProgressDialog (EnablerJobRequest.this);
        dialog.setMessage ("Retriving...");
        dialog.show ( );


        Map<String,String> params = new HashMap<> (  );

        params.put ("enabler_id",enablerid);

        JSONObject jsonObject = new JSONObject ( params );

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.POST, URLS.getEnablerJobs, jsonObject, new Response.Listener<JSONObject> ( ) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {

                dialog.dismiss ();

                try {
                    String msg = response.getString ("Information");
                    JSONArray array = new JSONArray ( msg );

                    for(int i=0;i<array.length ();i++) {

                        JSONObject jsonObject1 = array.getJSONObject (i);

                        Calendar calendar = Calendar.getInstance();
                        Date today = calendar.getTime();

                        calendar.add(Calendar.DAY_OF_YEAR, 1);
                        Date tomorrow = calendar.getTime();

                        DateFormat dateFormat = new SimpleDateFormat("yy/dd/MM");

                        String todayAsString = dateFormat.format(today);
                        String tomorrowAsString = dateFormat.format(tomorrow);

                        if (todayAsString.equals (jsonObject1.getString ("assign_date"))) {


                            JobRequest_ModelClass jobRequest_modelClass = new JobRequest_ModelClass (
                                    jsonObject1.getString ("property_id"),
                                    jsonObject1.getString ("enabler_id"),
                                    jsonObject1.getString ("assign_date"),
                                    jsonObject1.getString ("start_date"),
                                    jsonObject1.getString ("end_date"),
                                    jsonObject1.getString ("status"),
                                    jsonObject1.getString ("id")
                            );

                            jobrequest.add (jobRequest_modelClass);
                            jobRequestAdapter = new JobRequestAdapter (EnablerJobRequest.this, jobrequest);
                            recyclerView.setAdapter (jobRequestAdapter);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace ( );
                }
            }
        }, new Response.ErrorListener ( ) {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss ();
                Toast.makeText (EnablerJobRequest.this, ""+error, Toast.LENGTH_SHORT).show ( );


            }
        });

         RequestQueue requestQueue = Volley.newRequestQueue (EnablerJobRequest.this);
         requestQueue.add (jsonObjectRequest);

    }
}