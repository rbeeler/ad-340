package com.ruthbeeler.possibleapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity {

    ListView cameraList;
    CameraListAdapter listAdapter;

    String dataurl = "http://brisksoft.us/ad340/traffic_cameras_merged.json";
    ArrayList<Traffic> cameraData = new ArrayList<>;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cameraList = findViewById(R.id.cameraList);
        /*listAdapter = new CameraListAdapter(context:this cameraData);*/

        /*loadCameraData(dataUrl);*/
    }

    public class CameraListAdapter extends ArrayAdapter<Traffic> {
        private Context context;
        private ArrayList<Traffic> values;

        public CameraListAdapter(Context context, ArrayList<Traffic> values) {

            super(context, 0, values);
            this.context = context;
            this.values = values;
        }

    }
}