package com.example.josh.mobapdemp;

import android.widget.Button;

public class CrModel {
    private String crName;
    private String crLocation;
    private String id;
    private String imageUrl;
    private Boolean hasBidet;
    private Boolean hasAircon;
    private Boolean hasToiletSeat;
    private Boolean hasTissue;

    public CrModel() {

    }

    public CrModel(String crName, String crLocation, String id, String imageUrl, Boolean hasBidet, Boolean hasAircon, Boolean hasToiletSeat, Boolean hasTissue) {
        this.crName = crName;
        this.crLocation = crLocation;
        this.id = id;
        this.imageUrl = imageUrl;
        this.hasBidet = hasBidet;
        this.hasAircon = hasAircon;
        this.hasToiletSeat = hasToiletSeat;
        this.hasTissue = hasTissue;
    }

    public String getCrName() {
        return crName;
    }

    public void setCrName(String crName) {
        this.crName = crName;
    }

    public String getCrLocation() {
        return crLocation;
    }

    public void setCrLocation(String crLocation) {
        this.crLocation = crLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getHasBidet() {
        return hasBidet;
    }

    public void setHasBidet(Boolean hasBidet) {
        this.hasBidet = hasBidet;
    }

    public Boolean getHasAircon() {
        return hasAircon;
    }

    public void setHasAircon(Boolean hasAircon) {
        this.hasAircon = hasAircon;
    }

    public Boolean getHasToiletSeat() {
        return hasToiletSeat;
    }

    public void setHasToiletSeat(Boolean hasToiletSeat) {
        this.hasToiletSeat = hasToiletSeat;
    }

    public Boolean getHasTissue() {
        return hasTissue;
    }

    public void setHasTissue(Boolean hasTissue) {
        this.hasTissue = hasTissue;
    }
}
