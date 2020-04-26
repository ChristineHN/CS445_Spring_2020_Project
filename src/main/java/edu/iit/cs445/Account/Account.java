package edu.iit.cs445.Account;

import edu.iit.cs445.UIDGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Account {
    private String aid;
    private String first_name;
    private String last_name;
    private String phone;
    private String picture;
    private boolean is_active;
    private String date_created;

    public Account (String firstN, String lastN, String phone, String pic){
        this.aid = UIDGenerator.getUID();
        this.first_name = firstN;
        this.last_name = lastN;
        this.phone = phone;
        this.picture = pic;
        this.is_active = false;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy, HH:mm:ss");
        this.date_created = formatter.format(date);
    }

    public String getAid() {
        return aid;
    }

    public void setIsActive(boolean b) {
        this.is_active = b;
    }

    public void setFirstName(String firstN) {
        this.first_name = firstN;
    }

    public void setLastName(String lastN) {
        this.last_name = lastN;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPicture(String pic) {
        this.picture = pic;
    }

    public String getFirstName() {
        return this.first_name;
    }

    public String getLastName() {
        return this.last_name;
    }

    public String getPhone() {
        return this.phone;
    }

    public boolean getIsActive() {
        return this.is_active;
    }

    public String getPicture() {
        return this.picture;
    }

    public String getDate_created() {
        return this.date_created;
    }
}
