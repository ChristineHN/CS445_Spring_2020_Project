package edu.iit.cs445.Interactor;

import edu.iit.cs445.Account.Rate;
import edu.iit.cs445.Boundary.RateInteractorB;
import edu.iit.cs445.Boundary.RequestInteractorB;
import edu.iit.cs445.Boundary.RideInteractorB;
import edu.iit.cs445.Request.Request;
import edu.iit.cs445.Ride.Ride;

import java.util.ArrayList;
import java.util.List;

public class RateInteractor implements RateInteractorB {
    private static List<Rate> rateDriver = new ArrayList<>();
    private static List<Rate> rateRider = new ArrayList<>();

    public String rateAccount(String aid, String rid, String sent_by_id, int rating, String comment){
        RideInteractorB rib = new RideInteractor();
        Ride ride = new RideInteractor().getRideFromRid(rid);
        if(!isDriver(aid)){
            RequestInteractorB reqB = new RequestInteractor();
            for(Request req : reqB.viewAllRequest()){
                if(req.getAid().equals(aid)){
                    Rate rate = new Rate(aid, rid, ride.getDateTime().getDate(), sent_by_id,rating,comment);
                    rateRider.add(rate);
                    return rate.getSid();
                }
            }
        }
        else{
            for(Ride r : rib.viewAllRides()){
                if(r.getAid().equals(aid)){
                    Rate rate = new Rate(aid, rid,ride.getDateTime().getDate(), sent_by_id,rating,comment);
                    rateDriver.add(rate);
                    return rate.getSid();
                }
            }
        }
        return null;
    }

    public List<Rate> viewRateDriver(){
        return rateDriver;
    }

    public List<Rate> viewRateRider(){
        return rateRider;
    }

    @Override
    public List<Rate> getRiderComments(String aid) {
        List<Rate> lr = new ArrayList<>();
        for (Rate r : rateRider){
            if(r.getAid().equals(aid)){
                lr.add(r);
            }
        }
        return lr;
    }

    @Override
    public Double calculateAvgRiderRating(String aid) {
        int total = 0;
        Double avg = 0.0;
        for(Rate r : rateRider){
            if(r.getAid().equals(aid)){
                total++;
                avg += r.getRating();
            }
        }
        return (total==0 ? null : avg/total);
    }

    private boolean isDriver(String aid) {
        RideInteractorB rib = new RideInteractor();
        for(Ride r : rib.viewAllRides()){
            if(r.getAid().equals(aid)){
                return true;
            }
        }
        return false;
    }


    @Override
    public Double calculateAvgDriverRating(String aid) {
        int total = 0;
        Double avg = 0.0;
        for(Rate r : rateDriver){
            if(r.getAid().equals(aid)){
                total++;
                avg += r.getRating();
            }
        }
        return (total==0 ? null : avg/total);
    }

    @Override
    public List<Rate> getDriverComments(String aid) {
        List<Rate> lr = new ArrayList<>();
        for (Rate r : rateDriver){
            if(r.getAid().equals(aid)){
                lr.add(r);
            }
        }
        return lr;
    }

    @Override
    public int calculateDriverRating(String aid) {
        int total = 0;
        for(Rate r : rateDriver){
            if(r.getAid().equals(aid)){
                total++;
            }
        }
        return total;
    }
}
