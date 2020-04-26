package edu.iit.cs445.Account;

import edu.iit.cs445.UIDGenerator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Rate {
    private String aid;
    private String rid;
    private String sent_by_id;
    private int rating;
    private String comment;
    private String sid;
    private String date;

    public Rate(String aid, String rid, String rid_date, String sent_by_id, int rating, String comment){
        this.aid = aid;
        this.rid = rid;
        this.sent_by_id = sent_by_id;
        this.rating = rating;
        this.comment = comment;
        this.sid = UIDGenerator.getUID();
        this.date = rid_date;
    }

    public String getSid() {
        return this.sid;
    }

    public String getAid() {
        return this.aid;
    }

    public int getRating() {
        return this.rating;
    }

    public String getSentById() {
        return this.sent_by_id;
    }

    public String getRid() {
        return this.rid;
    }

    public String getDate() {
        return this.date;
    }

    public String getComment() {
        return this.comment;
    }
}
