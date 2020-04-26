package edu.iit.cs445.Boundary;
import edu.iit.cs445.Request.Request;

import java.util.List;

public interface RequestInteractorB {
    String createRequest(String aid, int passengers, String rid);
    void confirm_request(String jid);
    void deny_request(String jid);
    void confirm_passenger_pickup(String jid);
    int calculateRides(String aid);
    List<Request> viewAllRequest();

    Request getRequestRid(String rid);
}
