package com.example.techeye.subscriber.geofence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.techeye.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DirectionMapForGeofence extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    Bitmap smallMarker;
    ImageView imageButton;
    Button btn_save;
    TextView text_CurrentLocation;
    TextView latitude,longitude,country_name,locality,postalcode,address,text_MapView,City,State;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;

    String str_country_name,str_postalcode,str_statename,str_cityname,userid,userid1;
    String str_latitude1,str_longitude1,str_country_name1,str_locality1,str_postalcode1,str_Address1,str_stae,str_city;

    double Latitude = 20.3490;
    double Longitude = 85.8077;

    double lat,longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_map_for_geofence);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.geofence_map);
        mapFragment.getMapAsync(this);


        latitude = findViewById (R.id.text_view1);
        longitude = findViewById (R.id.text_view2);
        country_name = findViewById (R.id.text_view3);
        locality = findViewById (R.id.text_view6);
        postalcode = findViewById (R.id.text_view7);
        address = findViewById (R.id.text_view8);
        text_MapView = findViewById(R.id.mapView);
        text_CurrentLocation = findViewById(R.id.currentLocation);
        imageButton = findViewById(R.id.imageButton);
        btn_save = findViewById (R.id.verifay);
        City = findViewById (R.id.text_view5);
        State = findViewById (R.id.text_view4);

        Intent intent = getIntent ();

        userid = intent.getStringExtra ("userid");
        str_country_name = intent.getStringExtra ("country_name");
        str_statename = intent.getStringExtra ("statename");
        str_cityname = intent.getStringExtra ("cityname");
        str_postalcode = intent.getStringExtra ("postalcode");

        country_name.setText (str_country_name);
        postalcode.setText (str_postalcode);
        State.setText(str_statename);
        City.setText(str_cityname);

        //str_latitude = str_latitude.replace ("Latitude :\n","");
        //str_longitude = str_longitude.replace ("Longitude :\n","");

        /*Latitude = Double.parseDouble (str_latitude);
        Longitude = Double.parseDouble (str_longitude);*/

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        text_MapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = text_MapView.getText().toString().trim();

                if (value.equals("TERRAIN")) {

                    text_MapView.setText("HYBRID");
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                } else {

                    text_MapView.setText("TERRAIN");
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }

            }
        });

        text_CurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check gps is enable or not
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps
                    locationPermission();
                } else {
                    //GPS is already On then
                    getLocation();

                    LatLng sydney = new LatLng(Latitude,Longitude );
                    mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in here").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    //mMap.moveCamera (CameraUpdateFactory.newLatLngZoom (sydney, 16));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f));


                }
            }
        });

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.pin);
        Bitmap b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, 50, 50, false);

        btn_save.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {

                str_latitude1 = latitude.getText ().toString ().trim ();
                str_longitude1 = longitude.getText ().toString ().trim ();
                str_country_name1 = country_name.getText ().toString ().trim ();
                str_locality1 = locality.getText ().toString ().trim ();
                str_postalcode1 = postalcode.getText ().toString ().trim ();
                str_Address1 = address.getText ().toString ().trim ();
                str_city = City.getText ().toString ().trim ();
                str_stae = State.getText ().toString ().trim ();

                Intent intent1 = new Intent ( DirectionMapForGeofence.this,Geofence.class );

                intent1.putExtra ("latitude",str_latitude1);
                intent1.putExtra ("longitude",str_longitude1);
                intent1.putExtra ("country",str_country_name1);
                intent1.putExtra ("locality",str_locality1);
                intent1.putExtra ("postalcode",str_postalcode1);
                intent1.putExtra ("address",str_Address1);
                intent1.putExtra ("city",str_city);
                intent1.putExtra ("state",str_stae);
                intent1.putExtra ("userid",userid);


                startActivity (intent1);

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DirectionMapForGeofence.this,VideoCalling.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        enableUserLocation();
        if (ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled (true);

        getTopLocation (googleMap);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Latitude,Longitude );
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in here").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.moveCamera (CameraUpdateFactory.newLatLngZoom (sydney, 16));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f));

        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //Showing Current Location
        googleMap.setMyLocationEnabled(true); // false to disable

    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                //initialize location
                Location location = task.getResult();

                if (location != null) {

                    try {
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(DirectionMapForGeofence.this, Locale.getDefault());

                        //initialize AddressList
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        //set Latitude On Text View
                        latitude.setText(Html.fromHtml("<font color='#606060'><b>Latitude :</b><br></font>" + addresses.get(0).getLatitude()));
                        Latitude = addresses.get (0).getLatitude ();
                        //set Longitude On Text View
                        longitude.setText(Html.fromHtml("<font color='#606060'><b>Longitude :</b><br></font>" + addresses.get(0).getLongitude()));
                        Longitude= addresses.get (0).getLongitude ();
                        //set countary name On Text View
                        country_name.setText(Html.fromHtml("<font color='#606060'><b>CountryName :</b><br></font>" + addresses.get(0).getCountryName()));

                        //set state name On Text View
                        State.setText(Html.fromHtml("<font color='#606060'><b>State :</b><br></font>" + addresses.get(0).getAdminArea ()));

                        //set city name On Text View
                        City.setText(Html.fromHtml("<font color='#606060'><b>City :</b><br></font>" + addresses.get(0).getSubLocality ()+"/"+addresses.get(0).getSubAdminArea ()));

                        //set Locality On Text View
                        locality.setText(Html.fromHtml("<font color='#606060'><b>Locality :</b><br></font>" + addresses.get(0).getLocality()));

                        //set Locality On Text View
                        postalcode.setText(Html.fromHtml("<font color='#606060'><b>PostalCode :</b><br></font>" + addresses.get(0).getPostalCode()));

                        //set address On Text View
                        address.setText(Html.fromHtml("<font color='#606060'><b>Address :</b><br></font>" + addresses.get(0).getAddressLine(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    private void getTopLocation(GoogleMap googleMap) {

        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                //Check gps is enable or not
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps
                    locationPermission();
                } else {
                    //GPS is already On then
                    LatLng topLocation = googleMap.getCameraPosition().target;

                    Geocoder geocoder = new Geocoder(DirectionMapForGeofence.this, Locale.getDefault());

                    try {
                        List<Address> addresses = geocoder.getFromLocation(topLocation.latitude, topLocation.longitude, 1);
                        if (addresses.size() > 0) {

                            Address address1 = addresses.get(0);
                            String streetAddress = address1.getCountryName();
                            String streetAddress1 = address1.getLocality();
                            String streetAddress2 = address1.getPostalCode();
                            String streetAddress3 = address1.getAddressLine(0);

                            country_name.setText(streetAddress);
                            locality.setText(streetAddress1);
                            postalcode.setText(streetAddress2);
                            address.setText(streetAddress3);

                            //set Latitude On Text View
                            latitude.setText(Html.fromHtml("<font color='#606060'><b>Latitude :</b><br></font>" + addresses.get(0).getLatitude()));

                            //set Longitude On Text View
                            longitude.setText(Html.fromHtml("<font color='#606060'><b>Longitude :</b><br></font>" + addresses.get(0).getLongitude()));

                            //set countary name On Text View
                            country_name.setText(Html.fromHtml("<font color='#606060'><b>CountryName :</b><br></font>" + streetAddress));

                            //set Locality On Text View
                            locality.setText(Html.fromHtml("<font color='#606060'><b>Locality :</b><br></font>" + streetAddress1));

                            //set Locality On Text View
                            postalcode.setText(Html.fromHtml("<font color='#606060'><b>PostalCode :</b><br></font>" + streetAddress2));

                            //set address On Text View
                            address.setText(Html.fromHtml("<font color='#606060'><b>Address :</b><br></font>" + streetAddress3));

                        /*mMap.addMarker(new MarkerOptions()
                                .position(topLocation)
                                .title(streetAddress)
                                .draggable(true)
                        );*/

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                //We do not have the permission..
            }
        }
    }

    public void locationPermission() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable Your GPS Location").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}