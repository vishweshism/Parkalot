package com.example.asus.parkalot.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;

public class RatingActivity extends AppCompatActivity {

    DatabaseHelper helper;

    RatingBar mRatingBar ;
    TextView mRatingScale;
    EditText mFeedback ;
    Button mSendFeedback ;
    int rating;
    String feedback;
    int uid;
    Boolean isInserted;
    int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        helper = new DatabaseHelper(this);
        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        uid = sp.getInt("id",100);
        pid=1;


        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mRatingScale= (TextView) findViewById(R.id.tvRatingScale);
        mFeedback= (EditText) findViewById(R.id.etFeedback);
        mSendFeedback = (Button) findViewById(R.id.btnSubmit);
        feedback = mFeedback.getText().toString();

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        rating = 1;
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        rating =2;
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        rating=3;
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        rating =4;
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        rating = 5;
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });
        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFeedback.getText().toString().isEmpty()) {
                    Toast.makeText(RatingActivity.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                } else {
                    //mFeedback.setText("");
                    //mRatingBar.setRating(0);
                    isInserted= helper.insertFeedback(feedback,uid,pid,rating);

                    if (isInserted == true) {

                        Toast.makeText(RatingActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_LONG).show();

                    }
                    else
                        Toast.makeText(RatingActivity.this, "Feedback Error", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    intent = new Intent(RatingActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
