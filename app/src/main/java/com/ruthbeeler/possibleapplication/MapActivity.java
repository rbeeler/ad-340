package com.ruthbeeler.possibleapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_REQUEST_CODE = 1234;
    private static final String[] permission = {ACCESS_COARSE_LOCATION};
    private boolean locationPermissionGranted;
    private CameraActivity camerasActivity;
    private static boolean WIFIconnected = false;
    private static boolean mobileConnected = false;

    //list of cameras
    List<Traffic> cameraData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getDeviceLocation();
        loadCamInfo();
        checkNetworkConnections();
        showCameraMarkers();
        getLocationPermission();
    }

    public void getDeviceLocation() {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (locationPermissionGranted) {
                Task<Location> location = fusedLocationClient.getLastLocation();

                location.addOnSuccessListener(
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    mMap.setMyLocationEnabled(true);

                                    LatLng myloc = new LatLng(location.getLatitude(), location.getLongitude());
                                    //LatLng seattle = new LatLng(47.6062, -122.3321);

                                    mMap.addMarker(new MarkerOptions().position(myloc)
                                            .title("Here"));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLng(myloc));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 5));

                                    //showCameraMarkers();
                                }
                            }
                        }
                );
            }
        } catch (SecurityException e) {

            Log.e("Security Exception ", "Get device location");
        }
    }

    public void loadCamInfo() {

        String dataUrl = "http://brisksoft.us/ad340/traffic_cameras_merged.json";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET, dataUrl, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d("Cameras 1", response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject camera = response.getJSONObject(i);

                        double[] coords = {camera.getDouble("ypos"), camera.getDouble("xpos")};

                        Traffic c = new Traffic(
                                camera.getString("cameralabel"),
                                camera.getJSONObject("imageurl").getString("url"),
                                camera.getString("ownershipcd"),
                                coords
                        );
                        cameraData.add(c);
                        Log.i("Camera data", c.toString());

                    }
                    showCameraMarkers();

                } catch (JSONException e) {
                    Log.d("CAMERAS error", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error

            }
        });
        queue.add(jsonReq);

    }


    public boolean checkNetworkConnections() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            //different types of connection

            WIFIconnected = networkInfo.getType() == connectivityManager.TYPE_WIFI;
            mobileConnected = networkInfo.getType() == connectivityManager.TYPE_MOBILE;
            if (WIFIconnected) {
                Log.i("WIFI connected", "successfully");
                return true;
            } else if (mobileConnected) {
                Log.i("Mobile connected ", "successfuly");
                return true;
            }
        } else {
            Log.i("Connection status ", "No connection");
            return false;
        }
        return false;
    }

    public void showCameraMarkers() {

        Log.i("CAMERA", cameraData.toString());


        for(Traffic camera: cameraData){
            Log.i("CAMERA DATA", camera.toString());
            LatLng position = new LatLng(camera.myCoordinates()[0], camera.myCoordinates()[1]);
            Marker m = mMap.addMarker(new MarkerOptions().position(position).title(camera.getLabel()).snippet(camera.imageUrl()));
            m.setTag(camera);
        }
    }

    private void getLocationPermission() {

        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_REQUEST_CODE);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Add a marker in Seattle and move the camera
        //LatLng seattle = new LatLng(47.6062, -122.3321);
        //mMap.addMarker(new MarkerOptions().position(seattle).title("Marker in Seattle"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation, 5f));
    }
}
