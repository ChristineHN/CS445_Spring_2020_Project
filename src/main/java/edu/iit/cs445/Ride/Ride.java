package edu.iit.cs445.Ride;

import edu.iit.cs445.UIDGenerator;

import java.util.Date;

public class Ride {
    String aid;
    String rid;
    LocationInfo location_info;
    DateTime date_time;
    CarInfo car_info;
    int max_passengers;
    Double amount_per_passenger;
    String conditions;

    public Ride(String aid, LocationInfo location_info, DateTime date_time, CarInfo car_info, int max_passengers, double amount_per_passenger, String conditions){
        this.aid =aid;
        this.rid = UIDGenerator.getUID();
        this.location_info = location_info;
        this.date_time = date_time;
        this.car_info = car_info;
        this.max_passengers = max_passengers;
        this.amount_per_passenger = amount_per_passenger;
        this.conditions = conditions;
    }
    public Ride(){

    }

    public String getRId() {
        return rid;
    }

    public boolean isNull(){
        return false;
    }

    public void setLocationInfo(LocationInfo l) {
        this.location_info = l;
    }

    public void setDateTime(DateTime d) {
        this.date_time = d;
    }

    public void setCarInfo(CarInfo c) {
        this.car_info = c;
    }

    public void setMaxPassenger(int max_passengers) {
        this.max_passengers = max_passengers;
    }

    public void setAmountPerPassenger(double amount_per_passenger) {
        this.amount_per_passenger = amount_per_passenger;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public LocationInfo getLocationInfo() {
        return this.location_info;
    }

    public DateTime getDateTime() {
        return this.date_time;
    }

    public String getAid() {
        return this.aid;
    }

    public CarInfo getCarInfo() {
        return this.car_info;
    }

    public int getMaxPassengers() { return this.max_passengers;
    }

    public Double getAmountPerPassenger() { return this.amount_per_passenger;
    }

    public String getConditions() { return this.conditions;
    }
}
