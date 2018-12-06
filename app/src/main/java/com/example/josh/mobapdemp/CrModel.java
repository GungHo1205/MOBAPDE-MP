package com.example.josh.mobapdemp;

public class CrModel  {
    private String crName;
    private String crLocation;
    private String ID;

    public CrModel(String ID, String crName, String crLocation) {
        this.crName = crName;
        this.crLocation = crLocation;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public String getCrName() {

        return crName;
    }

    public String getCrLocation() {
        return crLocation;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setCrName(String crName) {
        this.crName = crName;
    }

    public void setCrLocation(String crLocation) {
        this.crLocation = crLocation;
    }

}
