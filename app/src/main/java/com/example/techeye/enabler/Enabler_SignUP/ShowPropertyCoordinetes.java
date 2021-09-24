package com.example.techeye.enabler.Enabler_SignUP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techeye.R;
import com.example.techeye.enabler.Enabler_SignUP.Geofence_Enabler.ShowPropertyinMap;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowPropertyCoordinetes extends AppCompatActivity {

    ListView list;
    TextView textView;
    Button btn_navigation;
    String listdata1;
    ArrayList<String> array_list1;
    String str_Coordinete;

    int PERMISSION_ID = 1;
    FusedLocationProviderClient mFusedLocationClient;
    protected LocationManager locationManager;
    String str_EnablerJobId,str_UserId;

    double latitude;
    double longitude;
    List<LatLng> point;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_show_property_coordinetes);

        textView = findViewById (R.id.textView);
        list = findViewById (R.id.list);
        btn_navigation = findViewById (R.id.navigation);

        str_Coordinete = getIntent ().getStringExtra ("coordinete");
        str_EnablerJobId = getIntent ().getStringExtra ("id");
        str_UserId = getIntent ().getStringExtra ("str_UserId");



        str_Coordinete = str_Coordinete.replace ("[", "");
        str_Coordinete = str_Coordinete.replace ("]", "");
        str_Coordinete = str_Coordinete.replace ("{", "");
        str_Coordinete = str_Coordinete.replace ("}", "");
        str_Coordinete = str_Coordinete.replace ("latitude", "");
        str_Coordinete = str_Coordinete.replace ("longitude", "");
        str_Coordinete = str_Coordinete.replaceAll ("\\\\","");
        str_Coordinete = str_Coordinete.replaceAll ("\"","");

        str_Coordinete = str_Coordinete.trim ();

        String str[] = str_Coordinete.split (",");

        array_list1 = new ArrayList<String> (Arrays.asList (str));

        ArrayAdapter arrayAdapter = new ArrayAdapter (getApplicationContext ( ),
                R.layout.list_text, array_list1);
        list.setAdapter (arrayAdapter);

        listdata1 = array_list1.get (0);
        listdata1 = listdata1.trim ( );
       // getLastLocation ();

        str_Coordinete = str_Coordinete.replaceAll (" ",",");

        String str1[] = str_Coordinete.split (",");

        array_list1 = new ArrayList<> (Arrays.asList (str1));

        List<Double> pointY = new ArrayList<> ( );
        List<Double> pointX = new ArrayList<> ( );
        int i;
        for (i = 0; i < array_list1.size ( ); i = i + 2) {
            String dd = array_list1.get (i);
            pointX.add (Double.valueOf (dd));
            // Toast.makeText (ShowValueInList.this, "" + pointX, Toast.LENGTH_SHORT).show ( );
        }

        int j;
        for (j = 1; j < array_list1.size ( ); j = j + 2) {
            String nd = array_list1.get (j);
            pointY.add (Double.valueOf (nd));
            //Toast.makeText (ShowValueInList.this, "" + pointY, Toast.LENGTH_SHORT).show ( );

        }

        point = new ArrayList<LatLng>();
        for (int k = 0 ; k < pointX.size(); k++){

            point.add(new LatLng(pointX.get(k),pointY.get(k)));
        }

         //Toast.makeText (ShowPropertyCoordinetes.this, "" + point, Toast.LENGTH_SHORT).show ( );

        String[] latlong = listdata1.split (" ");

        latitude = Double.parseDouble (latlong[0]);
        longitude = Double.parseDouble (latlong[1]);

        textView.setText (latitude+","+longitude);

        Toast.makeText (ShowPropertyCoordinetes.this, "" +latitude , Toast.LENGTH_SHORT).show ( );

        btn_navigation.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent ( ShowPropertyCoordinetes.this, ShowPropertyinMap.class );
                intent.putExtra ("latitude",String.valueOf (latitude));
                intent.putExtra ("longitude",String.valueOf (latitude));
                intent.putExtra ("id",str_EnablerJobId);
                intent.putExtra ("str_UserId",str_UserId);
                Bundle bundle = new Bundle();
                bundle.putSerializable("latlonglist", (Serializable) point);
                startActivity (intent);
            }
        });

    }

    private static String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder ( );
        for (String str : strArr)
            sb.append (str).append (delimiter);
        return sb.substring (0, sb.length ( ) - 1);
    }

}