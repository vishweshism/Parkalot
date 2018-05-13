package com.example.asus.parkalot.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.media.MediaPlayer;

import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;

public class splashActivity extends AppCompatActivity {
    private ProgressBar mProgressbar;
    private TextView mLoadingText;
    DatabaseHelper myDb;

    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myDb = new DatabaseHelper(this);

        mProgressbar = (ProgressBar)findViewById(R.id.progressbar);
        mLoadingText = (TextView)findViewById(R.id.LoadingCompleteTextView);
        final MediaPlayer CarSoundMediaPlayer = MediaPlayer.create(this, R.raw.carstartgarage);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus++;
                    android.os.SystemClock.sleep(50);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressbar.setProgress(mProgressStatus);
                        }
                    });
                }

                CarSoundMediaPlayer.start();




                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mLoadingText.setVisibility(View.VISIBLE);
                        Intent loginIntent = new Intent(splashActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();

                    }
                }) ;
            }

        }).start();



    }

}
