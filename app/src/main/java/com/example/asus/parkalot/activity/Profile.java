package com.example.asus.parkalot.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;

public class Profile extends AppCompatActivity {
    DatabaseHelper helper;

    EditText name,email,mobile,license,password,cpassword;
    int id;
    String nameStr,emailStr, mobileStr, passwordStr,cpasswordStr, licenseStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        helper = new DatabaseHelper(this);

        name = (EditText)findViewById(R.id.nametext);
        email = (EditText)findViewById(R.id.emailtext);
        mobile = (EditText)findViewById(R.id.mobtext);
        license = (EditText)findViewById(R.id.licensetext);
        password = (EditText)findViewById(R.id.passtext);
        cpassword = (EditText)findViewById(R.id.cpasstext);
        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
        nameStr = sp.getString("name", "");
        emailStr = sp.getString("email", "");
        mobileStr = sp.getString("contact", "");
        passwordStr = sp.getString("password","");
        licenseStr = sp.getString("licenseno", "");
        cpasswordStr = sp.getString("licenseno","");
        id = sp.getInt("id",0);
        name.setText(nameStr);
        email.setText(emailStr);
        mobile.setText(mobileStr);
        license.setText(licenseStr);
        password.setText(passwordStr);
        cpassword.setText(passwordStr);


    }

    public void update(View v){
        nameStr = name.getText().toString();
        emailStr = email.getText().toString().trim();
        mobileStr = mobile.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
        cpasswordStr = cpassword.getText().toString().trim();
        licenseStr = license.getText().toString().trim();
        if(passwordStr.equals(cpasswordStr))
        {
            boolean isUpdated=helper.updateData(id,nameStr,emailStr,mobileStr,passwordStr,licenseStr);
            if (isUpdated == true) {

                Toast.makeText(Profile.this, "User Updated", Toast.LENGTH_LONG).show();

            }
            else

                Toast.makeText(Profile.this, "Data not Inserted", Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(Profile.this,"Password Mismatch",Toast.LENGTH_LONG).show();

        }

    }

    public void cancel(View v){
        Intent intent;
        intent = new Intent(Profile.this, IndexActivity.class);
        startActivity(intent);
    }

    public void delete(View v){
        boolean isDeleted=helper.deleteData(id);
        if (isDeleted == true) {

            Toast.makeText(Profile.this, "User Deleted", Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(Profile.this,"Lol, Error",Toast.LENGTH_LONG).show();
        }

    }


}
