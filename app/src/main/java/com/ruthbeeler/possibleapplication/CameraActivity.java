package com.ruthbeeler.possibleapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity {

    ListView cameraList;
    CameraListAdapter listAdapter;

    String dataUrl = "http://brisksoft.us/ad340/traffic_cameras_merged.json";
    ArrayList<Traffic> cameraData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraList = (ListView) findViewById(R.id.cameraList);
        listAdapter = new CameraListAdapter(this,  cameraData);
        cameraList.setAdapter(listAdapter);

        loadCameraData(dataUrl);
    }

    public class CameraListAdapter extends ArrayAdapter<Traffic> {
        private final Context context;
        private  ArrayList<Traffic> values;

        public CameraListAdapter(Context context, ArrayList<Traffic> values){
            super(context, 0, values);
            this.context = context;
            this.values = values;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View customView = inflater.inflate(R.layout.camera_list_items, parent, false);

            TextView label = (TextView) customView.findViewById(R.id.label);
            label.setText(values.get(position).label);

            ImageView image = (ImageView) customView.findViewById(R.id.image);
            String imageUrl = values.get(position).image;

            if(!imageUrl.isEmpty()){
                Picasso.get().load(imageUrl).into(image);
            }

            return customView;
        }
    }


    public void loadCameraData(String dataUrl) {

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

                    }
                    listAdapter.notifyDataSetChanged();

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
}