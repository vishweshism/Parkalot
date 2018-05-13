package com.example.asus.parkalot.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;

public class ManageBooking extends AppCompatActivity {
String lotName,latitude,longitude;
int pid;
String time;
int slotNo;
int charge;
TextView lotname;
TextView datetime;
TextView slotno;
TextView charges;
Button deleteBtn;
Button getNav;
SharedPreferences ticket;
DatabaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_booking);
        helper = new DatabaseHelper(this);

        ticket = getSharedPreferences("ticket",MODE_PRIVATE);
        lotname = (TextView)findViewById(R.id.lotname);
        datetime = (TextView)findViewById(R.id.datetime);
        slotno = (TextView)findViewById(R.id.slotno);
        charges=(TextView)findViewById(R.id.charges);
        deleteBtn=(Button)findViewById(R.id.deleteBtn);
        getNav = (Button)findViewById(R.id.getNav);
        pid = ticket.getInt("pid",0);
        lotName = ticket.getString("Name","");
        time = ticket.getString("time","");
        slotNo = ticket.getInt("slotNo",0);
        String slotNoStr = Integer.toString(slotNo);

        charge = ticket.getInt("charges",0);
        String chargeStr = Integer.toString(charge);
        latitude=ticket.getString("latitude","");
        longitude=ticket.getString("longitude","");

        lotname.setText(lotName);
        datetime.setText(time);
        slotno.setText(slotNoStr);
        charges.setText(chargeStr);



    }

    public void getNav(View v)
    {
        latitude=ticket.getString("latitude","");
        longitude=ticket.getString("longitude","");

        Uri mapUri = Uri.parse("geo:0,0?q="+latitude+","+longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    public void delete(View v){
        boolean isDeleted=helper.deleteData(pid);
        if (isDeleted == true) {

            Toast.makeText(ManageBooking.this, "Booking Deleted", Toast.LENGTH_LONG).show();
            Intent intent;
            intent = new Intent(ManageBooking.this, IndexActivity.class);
            startActivity(intent);

        }
        else
        {
            Toast.makeText(ManageBooking.this,"Lol, Error",Toast.LENGTH_LONG).show();
        }

    }
}
