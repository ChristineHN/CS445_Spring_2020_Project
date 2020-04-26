package edu.iit.cs445.Request;

import edu.iit.cs445.UIDGenerator;

public class Request {
    private String aid;
    private int passengers;
    private Boolean ride_confirmed;
    private Boolean pickup_confirmed;
    private String jid;
    private String rid;

    public Request(){
        
    }
    public Request(String aid,int passengers, String rid){
        this.aid = aid;
        this.passengers = passengers;
        this.ride_confirmed = null;
        this.pickup_confirmed = null;
        this.jid = UIDGenerator.getUID();
        this.rid = rid;
    }
    public void setRide_confirmed(Boolean b){
        this.ride_confirmed = b;
    }

    public void setPickup_confirmed(Boolean b){
        this.pickup_confirmed = b;
    }

    public String getJId() {
        return this.jid;
    }
    public boolean isNull(){
        return false;
    }

    public String getAid() { return this.aid; }

    public Boolean getPickUpConfirmed() { return this.pickup_confirmed;
    }

    public String getRId() { return this.rid;
    }

    public boolean getRideConfirmed() { return this.ride_confirmed;
    }
}

