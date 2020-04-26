package edu.iit.cs445.Ride;

public class LocationInfo {
    String from_city;
    String from_zip;
    String to_city;
    String to_zip;

    public LocationInfo (String from_city, String from_zip, String to_city, String to_zip){
        this.from_city = from_city;
        this.from_zip = from_zip;
        this.to_city = to_city;
        this.to_zip = to_zip;
    }

    public String getFromCity() {
        return from_city;
    }

    public String getToCity() {
        return to_city;
    }

    public String getFromZip() {
        return from_zip;
    }
    public String getToZip() {
        return to_zip;
    }
}
