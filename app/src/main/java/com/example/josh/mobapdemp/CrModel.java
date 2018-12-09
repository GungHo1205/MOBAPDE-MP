package com.example.josh.mobapdemp;

import android.widget.Button;

public class CrModel  {
    private String crName;
    private String crLocation;
    private String id;
    private String imageUrl;
    public CrModel (){

    }

    public CrModel(String id, String crName, String crLocation, String imageUrl) {
        this.crName = crName;
        this.crLocation = crLocation;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getID() {
        return id;
    }

    public String getCrName() {

        return crName;
    }
    public String getImageUrl() {

        return imageUrl;
    }


    public String getCrLocation() {
        return crLocation;
    }


    public void setID(String ID) {
        this.id = ID;
    }

    public void setCrName(String crName) {
        this.crName = crName;
    }

    public void setCrLocation(String crLocation) {
        this.crLocation = crLocation;
    }

}
