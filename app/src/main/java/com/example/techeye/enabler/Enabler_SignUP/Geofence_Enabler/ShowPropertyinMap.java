package com.example.techeye.enabler.Enabler_SignUP.Geofence_Enabler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.techeye.R;
import com.example.techeye.enabler.Enabler_SignUP.CapturePropertyPhoto;
import com.example.techeye.enabler.Enabler_SignUP.RecordingPropertyVideo;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;


public class ShowPropertyinMap extends AppCompatActivity implements OnMapReadyCallback {

     double LATITUDE;
     double LONGITUDE;

     String str_LATITUDE;
     String str_LONGITUDE,str_EnablerJobId,str_UserId;

    private GoogleMap mMap;

    private GeofencingClient geofencingClient;
    private GeofenceHelper geofenceHelper;

    private float GEOFENCE_RADIUS = 50;
    private String GEOFENCE_ID = "SOME_GEOFENCE_ID";

    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;
    private static final String TAG = "MapsActivity";

    ArrayList<LatLng> latLngs;
    Button normal, satellite, terrain, hybrid;

    Polyline polyline;
    int red = 0, green = 0, blue = 0;
    ImageView imageButton, imageButton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_show_propertyin_map);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ( )
                .findFragmentById (R.id.map);
        mapFragment.getMapAsync (this);

        normal = findViewById (R.id.btn_normal);
        satellite = findViewById (R.id.btn_satellite);
        terrain = findViewById (R.id.btn_terrain);
        hybrid = findViewById (R.id.btn_hybrid);
        imageButton = findViewById (R.id.imageButton);
        imageButton1 = findViewById (R.id.imageButton1);

        str_LATITUDE = getIntent ().getStringExtra ("latitude");
        str_LONGITUDE = getIntent ().getStringExtra ("longitude");
        str_EnablerJobId = getIntent ().getStringExtra ("id");
        str_UserId = getIntent ().getStringExtra ("str_UserId");
        Bundle bundleObject = getIntent ( ).getExtras ( );
        latLngs = (ArrayList<LatLng>) bundleObject.getSerializable ("latlonglist");

        LATITUDE = Double.valueOf (str_LATITUDE);
        LONGITUDE = Double.valueOf (str_LONGITUDE);

        normal.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                mMap.setMapType (GoogleMap.MAP_TYPE_NORMAL);
            }
        });
        satellite.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                mMap.setMapType (GoogleMap.MAP_TYPE_SATELLITE);
            }
        });
        terrain.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                mMap.setMapType (GoogleMap.MAP_TYPE_TERRAIN);
            }
        });
        hybrid.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                mMap.setMapType (GoogleMap.MAP_TYPE_HYBRID);
            }
        });

        imageButton.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent (ShowPropertyinMap.this, CapturePropertyPhoto.class);
                intent1.putExtra ("id",str_EnablerJobId);
                intent1.putExtra ("str_UserId",str_UserId);
                intent1.putExtra ("str_LONGITUDE",str_LONGITUDE);
                intent1.putExtra ("str_LATITUDE",str_LATITUDE);
                startActivity (intent1);
            }
        });

        imageButton1.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent (ShowPropertyinMap.this, RecordingPropertyVideo.class);
                startActivity (intent1);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled (true);

        enableUserLocation();

        LatLng YourLocation = new LatLng (LATITUDE, LONGITUDE);
        mMap.addMarker (new MarkerOptions ( ).position (YourLocation).title ("Destination Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(YourLocation));
        mMap.animateCamera (CameraUpdateFactory.newLatLngZoom (YourLocation, 18f));
        mMap.setBuildingsEnabled (true);
        mMap.getUiSettings ( ).setCompassEnabled (true);
        mMap.getUiSettings ( ).setZoomControlsEnabled (true);
        mMap.getUiSettings ( ).setRotateGesturesEnabled (true);

        for (int i = 0; i < latLngs.size ( ); i++) {
            mMap.addMarker(new MarkerOptions().position(latLngs.get(i)).title("markers"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(YourLocation));
            mMap.animateCamera (CameraUpdateFactory.newLatLngZoom (YourLocation, 18f));
            if (polyline != null) polyline.remove ( );
            PolylineOptions polylineOptions = new PolylineOptions ( ).addAll (latLngs).clickable (true);
            polyline = mMap.addPolyline (polylineOptions);
            polyline.setColor (Color.rgb (red, green, blue));

        }

        handleMapLongClick(YourLocation);


    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled (true);
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale (this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions (this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult (requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                if (ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mMap.setMyLocationEnabled (true);
            } else {
                //We do not have the permission..

            }
        }

        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Toast.makeText (this, "You can add geofences...", Toast.LENGTH_SHORT).show ( );
            } else {
                //We do not have the permission..
                Toast.makeText (this, "Background location access is neccessary for geofences to trigger...", Toast.LENGTH_SHORT).show ( );
            }
        }
    }

    private void handleMapLongClick(LatLng latLng) {
        mMap.clear ( );
        addMarker (latLng);
        addCircle (latLng, GEOFENCE_RADIUS);
        addGeofence (latLng, GEOFENCE_RADIUS);
    }

    private void addGeofence(LatLng latLng, float radius) {

        Geofence geofence = geofenceHelper.getGeofence (GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest (geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent (imageButton,imageButton1 );

        if (ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        geofencingClient.addGeofences (geofencingRequest, pendingIntent)
                .addOnSuccessListener (new OnSuccessListener<Void> ( ) {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d (TAG, "onSuccess: Geofence Added...");

                        //imageButton.setVisibility (View.VISIBLE);
                    }
                })
                .addOnFailureListener (new OnFailureListener ( ) {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geofenceHelper.getErrorString (e);
                        Log.d (TAG, "onFailure: " + errorMessage);


                    }
                });
    }

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);
    }

    private void addCircle(LatLng latLng, float radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255, 255, 0,0));
        circleOptions.fillColor(Color.argb(64, 255, 0,0));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }
}