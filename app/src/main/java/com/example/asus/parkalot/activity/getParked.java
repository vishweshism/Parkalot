package com.example.asus.parkalot.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.DynamicLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CheckBox;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.parkalot.R;

import java.util.Calendar;
import java.util.List;


import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;

import butterknife.BindView;

public class getParked extends AppCompatActivity  {

    DatabaseHelper helper;

    EditText start_time,slotNo;
    CheckBox valet;
    CheckBox carWash;
    CheckBox mechAst;


    Button confirmButton;
    Spinner durationSpinner;
    int uid;
    int slotNos,pid;
    String latitude,longitude;
    int charges,rating;
    String lotName, category;

    SharedPreferences sp;
    SharedPreferences ps;

    private String image_url = "https://images.pexels.com/photos/2996/parking-parking-lot-underground-garage.jpg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
       helper = new DatabaseHelper(this);
       ps = getSharedPreferences("Lot Details",MODE_PRIVATE);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        uid = sp.getInt("id",0);
        pid = ps.getInt("pid",0);
       lotName = ps.getString("lotName","");
        latitude = ps.getString("latitude","");
        longitude = ps.getString("longitude","");
       slotNos = ps.getInt("slotNos",0);
       category = ps.getString("category","");
       charges = ps.getInt("charges",0);
       rating = ps.getInt("rating",0);

        start_time = (EditText) findViewById(R.id.start_time);
        durationSpinner = (Spinner)findViewById(R.id.spinDur);
        slotNo = (EditText)findViewById(R.id.slotNo);
        valet = (CheckBox) findViewById(R.id.checkBox1);
        carWash = (CheckBox) findViewById(R.id.checkBox2);
        mechAst = (CheckBox) findViewById(R.id.checkBox3);

        confirmButton = findViewById(R.id.confirm_button);


        //initialize views
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

        collapsingToolbarLayout.setTitle(lotName);

        TextView rowName = findViewById(R.id.p_rowname);
        TextView rowcategorie = findViewById(R.id.p_categorie);
        TextView rowRating = findViewById(R.id.p_rating);
        ImageView rowImage = findViewById(R.id.p_thumbnail);

        rowName.setText(lotName);
        rowcategorie.setText(category);
        rowRating.setText(rating);

        //RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getParked.this, PaymentActivity.class);
                startActivity(i);
            }
        });




        //set Image using Glide
        Glide.with(this).load(image_url).into(rowImage);

        //create Dynamic Buttons
        //createLayoutDynamically();


    }

}

