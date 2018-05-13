package com.example.asus.parkalot.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;
import com.example.asus.parkalot.model.Registration;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper helper;
    EditText name, email, contact, drivingLicenseNo, password, cpassword;
    //CheckBox cbox;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        helper= new DatabaseHelper(this);

        name = (EditText) findViewById(R.id.name_text);
        email = (EditText) findViewById(R.id.email_text);
        contact = (EditText) findViewById(R.id.contact_text);
        drivingLicenseNo = (EditText) findViewById(R.id.license_text);
        password = (EditText) findViewById(R.id.pass_text);
        cpassword = (EditText) findViewById(R.id.conpass_text);
        //cbox = (CheckBox) findViewById(R.id.checkBox);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        AddData();


    }

    public void AddData()
    {

        submitBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent;
                        boolean valid = true;

                        String nameStr = name.getText().toString();
                        String emailStr = email.getText().toString().trim();
                        String contactStr = contact.getText().toString().trim();
                        String drivingLicenseNoStr = drivingLicenseNo.getText().toString().trim();
                        String passwordStr =(String) password.getText().toString();
                        String cpasswordStr = cpassword.getText().toString();
                        if (!isValidUserName(nameStr)) {
                            name.setError("User Name is empty");
                            valid=false;
                        }
                        if (!isValidEmail(emailStr)) {
                            email.setError("invalid Email");
                            valid=false;
                        }
                        if (!isValidPassword(password.getText().toString())) {
                            password.setError("Password must be at least 6 characters");
                            valid=false;
                        }
                        if (!isContact(contactStr)) {
                            contact.setError("Invalid Contact");
                            valid=false;
                        }
                        if (!isValidUserName(drivingLicenseNoStr)) {
                            name.setError("Driving License Name is empty");
                            valid=false;
                        }

                        if(nameStr.isEmpty()||passwordStr.isEmpty()||emailStr.isEmpty()||contactStr.isEmpty()||drivingLicenseNoStr.isEmpty()||!password.getText().toString().equals(cpasswordStr)||valid==false)
                        {
                            if (!password.getText().toString().equals(cpasswordStr)) {
                                Toast pass = Toast.makeText(RegisterActivity.this, "Passwords Dont Match!", Toast.LENGTH_LONG);
                                pass.show();
                            }
                            valid=false;

                        }


                        if(valid==true){
                            boolean isInserted=helper.insertData(nameStr,emailStr,contactStr,passwordStr,drivingLicenseNoStr);


                                if (isInserted == true) {


                                    Toast.makeText(RegisterActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                    intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_LONG).show();

                            intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }
                    }
                }
        );
    }

    private boolean isValidUserName(String username) {
        if (username != null) {
            return true;
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 6) {
            return true;
        }
        return false;
    }

    private boolean isContact(String contact){
        String contact_pattern = "^[2-9]{2}[0-9]{8}$";
        Pattern pattern = Pattern.compile(contact_pattern);
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();
    }
}