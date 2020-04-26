package edu.iit.cs445.Message;

import edu.iit.cs445.UIDGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    String aid;
    String msg;
    String rid;
    String date_time;
    String mid;

    public Message(String aid, String msg, String rid){
        this.aid = aid;
        this.rid = rid;
        this.msg = msg;
        this.mid = UIDGenerator.getUID();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy, HH:mm:ss");
        this.date_time = formatter.format(date);
    }


    public String getRid() {
        return this.rid;
    }

    public String getMid() {
        return this.mid;
    }

    public String getSentByAid() {
        return this.aid;
    }

    public String getDate() {
        return this.date_time;
    }

    public String getBody() {
        return this.msg;
    }
}
