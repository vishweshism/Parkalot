package com.example.asus.parkalot.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.parkalot.R;
import com.example.asus.parkalot.adapter.RvAdapter;
import com.example.asus.parkalot.database.DatabaseHelper;
import com.example.asus.parkalot.model.ParkingPlaces;

import java.util.List;

public class IndexActivity extends AppCompatActivity {
    Button newBtn, viewBtn, myBtn, cancelBtn, logoutBtn;
    DatabaseHelper helper = new DatabaseHelper(this);
    int id;
    TextView TV1;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //String email = getIntent().getSerializableExtra("Email Id").toString();
        newBtn = (Button)findViewById(R.id.newBtn);
        viewBtn = (Button)findViewById(R.id.viewBtn);
        myBtn = (Button)findViewById(R.id.myBtn);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        logoutBtn = (Button)findViewById(R.id.logoutBtn);
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String uname = sp.getString("name","");
        id = sp.getInt("id",100);

        TV1 = (TextView) findViewById(R.id.TVUname);
        TV1.setText("Welcome, "+ uname);
        dialog = new Dialog(this);
        showPopup();

    }



    public void newBooking(View v)
    {

        Intent intent;
        intent = new Intent(IndexActivity.this, SelectPlot.class);
        //intent.putExtra("userem",id);
        startActivity(intent);
    }

    public void viewSpaces(View v)
    {
        Intent intent;
        intent = new Intent(IndexActivity.this, MainActivity.class);
        //intent.putExtra("userem",id);
        startActivity(intent);
    }

    public void logOut(View v)
    {
        Intent intent;
        intent = new Intent(IndexActivity.this, RatingActivity.class);
        startActivity(intent);
    }

    public void viewProfile(View v)
    {
        Intent intent;
        intent = new Intent(IndexActivity.this, Profile.class);
        startActivity(intent);
    }
    public void viewBooking(View v)
    {
        Intent intent;
        intent = new Intent(IndexActivity.this, ManageBooking.class);
        startActivity(intent);

    }
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

                Intent associateActivity = new Intent(IndexActivity.this, AssociateActivity.class);
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



}
