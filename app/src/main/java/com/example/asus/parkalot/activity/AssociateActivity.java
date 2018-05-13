package com.example.asus.parkalot.activity;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import android.text.format.DateFormat;

import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssociateActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    private static final String TAG = "SignupActivity";
    //int hour,minute;
    //int hourfinal,minutefinal;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_address) EditText _addressText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_mobile) EditText _mobileText;
    //@BindView(R.id.valet) CheckBox checkBox;
    @BindView(R.id.nos)EditText _slots;
    //int valet;
    DatabaseHelper helper;
    @BindView(R.id.catSpinner) Spinner spinner;
    String category;

    @BindView(R.id.btn_signup) Button _signupButton;
    SharedPreferences sp;
int id;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_associate);
        ButterKnife.bind(this);
        helper = new DatabaseHelper(this);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.category_array,
                        android.R.layout.simple_spinner_item);
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                category= (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });



        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = _nameText.getText().toString();
                String latitude = _addressText.getText().toString();
                String longitude= _emailText.getText().toString();
                String chargeStr = _mobileText.getText().toString();
                int charges = Integer.parseInt(chargeStr);
                String slots = _slots.getText().toString();
                int nos = Integer.parseInt(slots);
                if(validate()==true)
                {
                    boolean isInserted=helper.insertParkingLot(name,nos,latitude,longitude,charges,category);


                    if (isInserted == true) {

                        Toast.makeText(AssociateActivity.this, "Thanks for Joining Us", Toast.LENGTH_LONG).show();
                        boolean isUpdated=helper.updateUser(id);
                        if (isUpdated == true) {

                            Toast.makeText(AssociateActivity.this, "User Updated", Toast.LENGTH_LONG).show();

                        }

                        Intent intent;
                        intent = new Intent(AssociateActivity.this, ImageActivity.class);
                        startActivity(intent);

                    }
                    else
                        Toast.makeText(AssociateActivity.this, "Ooops, Error", Toast.LENGTH_SHORT).show();


                }
            }
        });
/*
        _stime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                hour=c.get(Calendar.HOUR_OF_DAY);
                minute=c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(AssociateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutee) {
                        _stime.setText(hourOfDay+":"+minutee);
                    }
                },hour,minute,true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        _ftime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                hourfinal=c.get(Calendar.HOUR_OF_DAY);
                minutefinal=c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(AssociateActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutee) {
                        _ftime.setText(hourOfDay+":"+minutee);
                    }
                },hourfinal,minutefinal,true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });
        */

    }



       /* final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
*/



        // TODO: Implement your own signup logic here.




    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        //String stimee=_stime.getText().toString();
        //String etimee=_ftime.getText().toString();
        String nslots=_slots.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty()) {
            _mobileText.setError("Enter Valid Charges");
            valid = false;
        } else {
            _mobileText.setError(null);
        }
        /*
        if(stimee.isEmpty())
        {
            _stime.setError("Enter valid Time");
            valid=false;
        }
        else {
            _stime.setError(null);
        }

        if(etimee.isEmpty())
        {
            _ftime.setError("Enter valid Time");
            valid=false;
        }
        else {
            _ftime.setError(null);
        }
        */

        if(nslots.isEmpty())
        {
            _slots.setError("Enter valid slots");
            valid=false;
        }
        else {
            _slots.setError(null);
        }


        return valid;
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }


}

