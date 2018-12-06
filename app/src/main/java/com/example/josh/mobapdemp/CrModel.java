package com.example.josh.mobapdemp;

public class CrModel  {
    private String crName;
    private String crLocation;
    private String id;

    public CrModel (){

    }

    public CrModel(String id, String crName, String crLocation) {
        this.crName = crName;
        this.crLocation = crLocation;
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public String getCrName() {

        return crName;
    }

    public String getCrLocation() {
        return crLocation;
    }

    public void setID(String ID) {
        this.id = id;
    }

    public void setCrName(String crName) {
        this.crName = crName;
    }

    public void setCrLocation(String crLocation) {
        this.crLocation = crLocation;
    }

}
