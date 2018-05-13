package com.example.asus.parkalot.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.asus.parkalot.R;
import com.example.asus.parkalot.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)

    byte[] imgStream;
    DatabaseHelper helper;
    SharedPreferences sp;
    int id;



    public void getPhoto()
    {
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public  void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int [] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(requestCode ==1){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                getPhoto();

            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        helper = new DatabaseHelper(this);
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else {
            getPhoto();
        }

        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        id = sp.getInt("id",100);



    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null){
            Uri selectedImage = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
                imgStream = stream.toByteArray();
                ImageView insertImage = (ImageView)findViewById(R.id.insertImage);
                insertImage.setImageBitmap(bitmap);
                boolean isInserted = helper.insertImage(id,imgStream);
                if(isInserted==true)
                {
                    Toast.makeText(this,"Image and Parking Lot ADD",Toast.LENGTH_LONG);
                    Intent intent;
                    intent = new Intent(ImageActivity.this, IndexActivity.class);
                    startActivity(intent);
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
