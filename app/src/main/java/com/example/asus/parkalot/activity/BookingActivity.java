package com.example.asus.parkalot.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.DynamicLayout;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.parkalot.R;

import java.util.Calendar;

import butterknife.BindView;



public class BookingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.start_time)
    EditText _stime;
    @BindView(R.id.duration) EditText _duration;
    int hour, minute;
    Button confirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        //hide the default action bar
        //getSupportActionBar().hide();



        //receive data
        String name = getIntent().getExtras().getString("name");
        String categorie = getIntent().getExtras().getString("categorie");
        String ratings = getIntent().getExtras().getString("rating");
        String image_url = getIntent().getExtras().getString("image");


        //initialize views
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

        collapsingToolbarLayout.setTitle(name);

        TextView rowName = (TextView) findViewById(R.id.p_rowname);
        TextView rowcategorie = (TextView) findViewById(R.id.p_categorie);
        TextView rowRating = (TextView) findViewById(R.id.p_rating);
        ImageView rowImage = (ImageView) findViewById(R.id.p_thumbnail);
        confirmButton = (Button) findViewById(R.id.confirm_button);
        _stime = findViewById(R.id.start_time);
        _duration = findViewById(R.id.duration);

        _stime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                hour=c.get(Calendar.HOUR_OF_DAY);
                minute=c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog=new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minutee) {
                        _stime.setText(hourOfDay+":"+minutee);
                    }
                },hour,minute,true);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookingActivity.this, PaymentActivity.class);
                startActivity(i);
            }
        });

        rowName.setText(name);
        rowcategorie.setText(categorie);
        rowRating.setText(ratings);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);

        //set Image using Glide
        Glide.with(this).load(image_url).into(rowImage);

        //create Dynamic Buttons



    }//end of onCreate

    //create layout buttons dynamically


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}

