package com.example.asus.parkalot.model;

/**
 * Created by ASUS on 16/03/2018.
 */

import android.os.Build;

/**
 * Created by vishwesh on 05/03/18.
 */

public class Registration {
    int id;
    int isAssociate = 0;
    String contact;
    String name, email, password, license_no;

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getContact(){
        return this.contact;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail()
    {
        return this.email;
    }
    /*
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return this.userName;
   }
   */
    public void setPassword(String Password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setLicense_no(String license_no)
    {
        this.license_no = license_no;
    }
    public String getLicense_no(){
        return this.license_no;
    }

    public void setAssociate(int isAssociate)
    {
        this.isAssociate = isAssociate;
    }
    public int getAssociate()
    {
        return isAssociate;
    }


}
