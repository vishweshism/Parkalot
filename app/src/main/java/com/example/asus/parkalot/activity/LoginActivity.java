package com.example.asus.parkalot.activity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ASUS on 16/03/2018.
 */

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    private TextView registerText;
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Cursor cursor;
    Button login;
    EditText emaile,passe;
    SharedPreferences sp;
    String name,emailid,contact,password,licenseno;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        if(sp.getBoolean("logged",false)){
            goToMainActivity();
        }
        registerText = (TextView)findViewById(R.id.registerText);
        emaile=(EditText)findViewById(R.id.user_text);
        passe=(EditText)findViewById(R.id.pass_text);
        login=(Button)findViewById(R.id.button2);
        openHelper=new DatabaseHelper(this);
        db=openHelper.getReadableDatabase();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emaile.getText().toString();
                String pass = passe.getText().toString();
                cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COLUMN_EMAIL + "=? AND "
                        + DatabaseHelper.COLUMN_PASSWORD + "=?", new String[]{email, pass});

                if (cursor != null) {
                    if (cursor.getCount() > 0)
                    {
                        cursor.moveToFirst();
                        id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                        name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
                        emailid = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL));
                        contact = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTACT));
                        password = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD));
                        licenseno = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LICENSENO));
                        sp.edit().putInt("id",id).apply();
                        sp.edit().putString("name",name).apply();
                        sp.edit().putString("email",emailid).apply();
                        sp.edit().putString("contact",contact).apply();
                        sp.edit().putString("password",password).apply();
                        sp.edit().putString("licenseno",licenseno).apply();

                        Toast.makeText(getApplicationContext(), "Login Successful " , Toast.LENGTH_SHORT).show();
                        goToMainActivity();
                        sp.edit().putBoolean("logged",true).apply();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Login Error!", Toast.LENGTH_SHORT).show();
                        Intent intent;
                        intent = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    /*private void signUp() {
        Intent loginIntent = new Intent(LoginActivity.this, SignUP.class);
        startActivity(loginIntent);

    }*/




    public void onClick1(View view) {

        Intent intent;
        intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToMainActivity(){
        Intent i = new Intent(this,IndexActivity.class);
        startActivity(i);
    }

    public void social(View v){
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse("http://www.watumull.edu/profmore/"));
        startActivity(viewIntent);

    }
}
