package edu.iit.cs445.Interactor;

import edu.iit.cs445.Boundary.RideInteractorB;
import edu.iit.cs445.Request.NullRequest;
import edu.iit.cs445.Request.Request;
import edu.iit.cs445.Ride.*;

import java.util.ArrayList;
import java.util.List;

public class RideInteractor implements RideInteractorB {
    private static List<Ride> rides = new ArrayList<>();
    public String createRide(String aid, LocationInfo l, DateTime d, CarInfo c, int max_passengers, double amount_per_passenger, String conditions) {
        Ride newRide = new Ride(aid, l, d, c, max_passengers, amount_per_passenger, conditions);
        rides.add(newRide);
        return newRide.getRId();
    }

    public void updateRide(String rid, LocationInfo l, DateTime d, CarInfo c, int max_passengers, double amount_per_passenger, String conditions) {
        Ride r = this.getRideFromRid(rid);
        r.setLocationInfo(l);
        r.setDateTime(d);
        r.setCarInfo(c);
        r.setMaxPassenger(max_passengers);
        r.setAmountPerPassenger(amount_per_passenger);
        r.setConditions(conditions);
    }
    public void deleteRide(String rid) {
        Ride r = this.getRideFromRid(rid);
        rides.remove(r);
    }
    public List<Ride> viewAllRides(){
        return rides;
    }



    public Ride getRideFromRid(String rid) {
        for (Ride r : rides) {
            if (rid.equals(r.getRId())) {
                return r;
            }
        }
        return new NullRide();
    }

    @Override
    public int calculateRides(String aid) {
        int total = 0;
        for (Ride r : rides){
            Request req = new RequestInteractor().getRequestRid(r.getRId());
            if (r.getAid().equals(aid) && (!req.isNull() && req.getPickUpConfirmed())){
                total++;
            }
        }
        return total;
    }

}
