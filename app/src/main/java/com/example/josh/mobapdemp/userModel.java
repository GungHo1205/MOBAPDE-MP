package com.example.josh.mobapdemp;

public class userModel {
    String email;
    int exp;
    String ID;
    public userModel(){

    }
    public userModel(String email, int exp, String ID){
        this.email = email;
        this.exp = exp;
        this.ID = ID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getEmail() {
        return email;
    }

    public int getExp() {
        return exp;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
