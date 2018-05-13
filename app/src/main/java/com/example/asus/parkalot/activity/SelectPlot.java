package com.example.asus.parkalot.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;

import java.util.List;

public class SelectPlot extends AppCompatActivity {
    Spinner spinner;
    SharedPreferences ps;
    Button next;
    int pid;
    //DatabaseHelper helper;
    Cursor cursor;
    SQLiteOpenHelper openHelper;
    DatabaseHelper helper;
    SQLiteDatabase db;
    int slotNos;
    String latitude, longitude, category;
    int charges, rating;
    byte[] image;
    Button nextBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_plot);
        openHelper = new DatabaseHelper(this);
        db=openHelper.getReadableDatabase();
        helper = new DatabaseHelper(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        nextBtn = (Button)findViewById(R.id.nextBtn);


        ps = getSharedPreferences("Lot Details", MODE_PRIVATE);
        next = (Button) findViewById(R.id.nextBtn);
        db = openHelper.getReadableDatabase();
        //helper = new DatabaseHelper(this);
        List<String> lables = helper.getAllLabels();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectPlot.this,
                android.R.layout.simple_spinner_item, lables);


        // Drop down layout style - list view with radio button
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                ps.edit().putString("lotName", (String) parent.getItemAtPosition(position));
                Log.v("pid", "Pid " + position);
                pid = position + 1;
                ps.edit().putInt("pid", pid).apply();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }


        });


        nextBtn.setOnClickListener(new View.OnClickListener()
        {
           public void onClick(View v)
           {
               String ppid = String.valueOf(pid);
               cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_PLOTS + " WHERE pid =?", new String[]{ppid});

               if (cursor != null) {
                   if (cursor.getCount() > 0) {
                       cursor.moveToFirst();
                       slotNos = cursor.getInt(cursor.getColumnIndex("slotNos"));
                       latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                       longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                       charges = cursor.getInt(cursor.getColumnIndex("charges"));
                       category = cursor.getString(cursor.getColumnIndex("category"));
                       rating = cursor.getInt(cursor.getColumnIndex("rating"));

                       ps.edit().putInt("slotNos", slotNos).apply();
                       ps.edit().putString("latitude", latitude).apply();
                       ps.edit().putString("longitude", longitude).apply();
                       ps.edit().putInt("charges", charges).apply();
                       ps.edit().putString("category", category).apply();
                       ps.edit().putInt("rating", rating);
                       Toast.makeText(getApplicationContext(), "Parking Info Fetched! ", Toast.LENGTH_SHORT).show();

                       Intent intent;
                       intent = new Intent(SelectPlot.this,SimpleBook.class);
                       startActivity(intent);

                   } else {
                       Toast.makeText(getApplicationContext(), "Error while fetching!", Toast.LENGTH_SHORT).show();
                   }

               }
           }
        });



    }


    }


