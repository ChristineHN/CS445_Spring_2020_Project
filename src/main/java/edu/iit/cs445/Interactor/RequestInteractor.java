package edu.iit.cs445.Interactor;

import edu.iit.cs445.Boundary.RequestInteractorB;
import edu.iit.cs445.Request.NullRequest;
import edu.iit.cs445.Request.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestInteractor implements RequestInteractorB {
    private static List<Request> req = new ArrayList<>();
    public String createRequest(String aid, int passengers, String rid){
        Request newRequest = new Request(aid, passengers, rid);
        req.add(newRequest);
        return newRequest.getJId();
    }

    public void confirm_request(String jid){
        Request r = this.getRequestFromJid(jid);
        r.setRide_confirmed(true);
    }
    public void deny_request(String jid){
        Request r = this.getRequestFromJid(jid);
        r.setRide_confirmed(false);
    }
    public void confirm_passenger_pickup(String jid){
       Request r = this.getRequestFromJid(jid);
       r.setPickup_confirmed(true);
    }

    @Override
    public List<Request> viewAllRequest() {
        return req;
    }

    @Override
    public Request getRequestRid(String rid) {
        for(Request r : req){
            if(rid.equals(r.getRId())){
                return r;
            }
        }
        return new NullRequest();

    }

    private Request getRequestFromJid(String jid){
            for(Request r : req){
                if(jid.equals(r.getJId())){
                    return r;
                }
            }
            return new NullRequest();
    }


    public int calculateRides(String aid) {
        int total = 0;
        for (Request r : req){
            if (r.getAid().equals(aid) && (!r.isNull() && r.getPickUpConfirmed())){
                total++;
            }
        }
        return total;
    }


}
