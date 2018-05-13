package com.example.asus.parkalot.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleBook extends AppCompatActivity {

    Spinner spinner;
    EditText slot_text;
    Button payBtn;
    EditText start_time;
    CheckBox valet,carWash,mechAst;

    String time;
    int chargesHr;
    int charges;
    int servicesTaken;
    int duration;
    int slotNo;
    Button button;

    SharedPreferences sp,ps,ticket;
    int uid,pid,slotNos,rating;
    String category;
    String lotName,latitude,longitude;
    DatabaseHelper helper;
    Boolean valid;
    String slotNoStr="1";




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_book);
        helper = new DatabaseHelper(this);
        button = (Button)findViewById(R.id.button);
        ps = getSharedPreferences("Lot Details",MODE_PRIVATE);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        ticket = getSharedPreferences("ticket",MODE_PRIVATE);
        uid = sp.getInt("id",0);
        pid = ps.getInt("pid",0);
        lotName = ps.getString("lotName","");
        latitude = ps.getString("latitude","");
        longitude = ps.getString("longitude","");
        slotNos = ps.getInt("slotNos",0);
        category = ps.getString("category","");
        chargesHr = ps.getInt("charges",0);
        rating = ps.getInt("rating",0);
        valid=true;
        charges = 0;


        spinner = (Spinner)findViewById(R.id.spinner);
        slot_text = (EditText)findViewById(R.id.slot_text);
        //payBtn = (Button)findViewById(R.id.payBtn);
        start_time = (EditText)findViewById(R.id.start_time);

        valet = (CheckBox)findViewById(R.id.checkBox1);
        carWash = (CheckBox)findViewById(R.id.checkBox2);
        mechAst = (CheckBox)findViewById(R.id.checkBox3);


        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.duration_array,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch(position){
                    case 0:
                        duration = 1;
                        charges = duration*chargesHr;
                        break;
                    case 1:
                        duration = 1;
                        charges = duration*chargesHr;
                        break;
                    case 2:
                        duration = 2;
                        charges = duration*chargesHr;
                        break;
                    case 3:
                        duration=3;
                        charges = duration*chargesHr;
                        break;
                        default:
                            Toast.makeText(SimpleBook.this,"Error in selection",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





       button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidTime(time)) {
                    start_time.setError("Invalid Time");
                    valid=false;
                }
                if (!isValidSlot(slotNo)) {
                    slot_text.setError("Invalid Slot");
                    valid=false;
                }
                if(valet.isChecked()){
                    servicesTaken++;
                }
                if(carWash.isChecked()){
                    servicesTaken++;
                }
                if(carWash.isChecked()){
                    servicesTaken++;
                }
                charges = charges + servicesTaken*100;
                time = start_time.getText().toString().trim();
                slotNoStr = slot_text.getText().toString().trim();
                slotNo = Integer.parseInt(slotNoStr);

                if(valid==true)
                {
                    boolean isInserted=helper.insertBooking(lotName, pid, uid, time, duration , slotNo, servicesTaken, charges,0);



                    if (isInserted == true) {

                        Toast.makeText(SimpleBook.this, "Booking Successful", Toast.LENGTH_LONG).show();
                        ticket.edit().putString("Name",lotName).apply();
                        ticket.edit().putString("latitude",latitude).apply();
                        ticket.edit().putString("longitude",longitude).apply();
                        ticket.edit().putInt("pid",pid).apply();
                        ticket.edit().putString("time",time).apply();
                        ticket.edit().putInt("slotNo",slotNo).apply();
                        ticket.edit().putInt("charges",charges).apply();

                        Intent intent;
                        intent = new Intent(SimpleBook.this, ManageBooking.class);
                        startActivity(intent);

                    }
                    else
                        Toast.makeText(SimpleBook.this, "Ooops, Error", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public boolean isValidTime(String time)
    {
        /*String time_pattern = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern pattern = Pattern.compile(time_pattern);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
        */
        return  true;
    }

    public boolean isValidSlot(int slotNo)
    {
        /*
        if(slotNo>0&&slotNo< 12){
            return true;
        }
        else
        {
            return false;
        }
        */
        return  true;
    }
}
