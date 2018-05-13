package com.example.asus.parkalot.activity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.parkalot.R;
import com.example.asus.parkalot.adapter.RvAdapter;
import com.example.asus.parkalot.model.ParkingPlaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String URL_JSON = "https://raw.githubusercontent.com/HarshitSingh96/Parkalot/master/LICENSE.md/.gitignore/data.json";
    private JsonArrayRequest ArrayRequest;
    private RequestQueue requestQueue;
    private List<ParkingPlaces> parkings = new ArrayList<>();
    private RecyclerView myrv;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myrv = findViewById(R.id.recycler_view);
        jsoncall();
        dialog = new Dialog(this);
        showPopup();


    }

    public void jsoncall() {


        ArrayRequest = new JsonArrayRequest(URL_JSON, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;


                for (int i = 0; i < response.length(); i++) {

                    //Toast.makeText(getApplicationContext(),String.valueOf(i),Toast.LENGTH_SHORT).show();

                    try {

                        jsonObject = response.getJSONObject(i);
                        ParkingPlaces places = new ParkingPlaces();

                        places.setName(jsonObject.getString("name"));
                        places.setRating(jsonObject.getString("Rating"));
                        places.setImage_url(jsonObject.getString("img"));
                        places.setCategorie(jsonObject.getString("categorie"));
                        //Toast.makeText(MainActivity.this,anime.toString(),Toast.LENGTH_SHORT).show();
                        parkings.add(places);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                //Toast.makeText(MainActivity.this, "Size of Liste " + String.valueOf(parkings.size()), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, parkings.get(1).toString(), Toast.LENGTH_SHORT).show();

                setRvadapter(parkings);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(ArrayRequest);
    }

    //method to show popup
    public void showPopup() {

        dialog.setContentView(R.layout.layout_associate_popup);
        TextView associate, close;

        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);

        associate = (TextView) dialog.findViewById(R.id.associate);
        close = (TextView)dialog.findViewById(R.id.close);

        associate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent associateActivity = new Intent(MainActivity.this, AssociateActivity.class);
                startActivity(associateActivity);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void setRvadapter(List<ParkingPlaces> lst) {

        RvAdapter myAdapter = new RvAdapter(this, parkings);
        myrv.setLayoutManager(new LinearLayoutManager(this));
        myrv.setAdapter(myAdapter);

    }

    public void openAssociate(View view) {
    }

    public void dismiss(View view) {
    }
}
