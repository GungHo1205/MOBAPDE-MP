package com.example.josh.mobapdemp;

public class userModel {
    public String email;
    public int exp;
    public userModel(){

    }

    public userModel(String email, int exp){
        this.email = email;
        this.exp = exp;
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

}
