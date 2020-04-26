package edu.iit.cs445.Boundary;

import edu.iit.cs445.Ride.CarInfo;
import edu.iit.cs445.Ride.DateTime;
import edu.iit.cs445.Ride.LocationInfo;
import edu.iit.cs445.Ride.Ride;

import java.util.List;

public interface RideInteractorB {
    String createRide(String aid, LocationInfo l, DateTime d, CarInfo c, int max_passengers, double amount_per_passenger, String conditions );
    void updateRide(String rid, LocationInfo l, DateTime d, CarInfo c, int max_passengers, double amount_per_passenger, String conditions);
    void deleteRide(String rid);
    List<Ride> viewAllRides();
    Ride getRideFromRid(String rid);
    int calculateRides(String aid);

}
