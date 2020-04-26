package edu.iit.cs445;

import edu.iit.cs445.Boundary.AccountInteractorB;
import edu.iit.cs445.Boundary.RequestInteractorB;
import edu.iit.cs445.Boundary.RideInteractorB;
import edu.iit.cs445.Interactor.AccountInteractor;
import edu.iit.cs445.Interactor.RequestInteractor;
import edu.iit.cs445.Interactor.RideInteractor;
import edu.iit.cs445.Request.Request;
import edu.iit.cs445.Ride.CarInfo;
import edu.iit.cs445.Ride.DateTime;
import edu.iit.cs445.Ride.LocationInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestTest {
    private AccountInteractorB ai = new AccountInteractor();
    private RideInteractorB ri = new RideInteractor();
    private RequestInteractorB reqi = new RequestInteractor();
    private String driver;
    private String rider;
    private String rid;
    private String jid;

    @Before
    public void setUp(){
        driver = ai.createAccount("foo","bar","111-222-4444", "http://test.com");
        rider = ai.createAccount("asdf","zxcv","000-123-4567", "http://test123.com");

        LocationInfo l = new LocationInfo("Chicago", "60605", "Hollywood", "20464");
        DateTime d = new DateTime("12-May-2020", "03:12:34");
        CarInfo c = new CarInfo("Audi", "A4", "Grey", "IL", "PJ2020");
        int max_passengers = 2;
        double amount_per_passenger = 15.00;
        String conditions = "no conditions";
        rid = ri.createRide(driver, l, d, c, max_passengers, amount_per_passenger, conditions);
        int passengers = 1;
        jid = reqi.createRequest(rider, passengers, rid);
    }
    @After
    public void tearUp(){
        ai.viewAllAccount().clear();
        ri.viewAllRides().clear();
        reqi.viewAllRequest().clear();
    }

    @Test
    public void createRequest_creates_request(){
        int passengers = 1;
        String req = reqi.createRequest(rider, passengers, rid);
        Assert.assertEquals(2, reqi.viewAllRequest().size());
    }

    @Test
    public void confirm_request_returns_true_when_confirmed(){
        reqi.confirm_request(jid);
        Request r = reqi.getRequestRid(rid);
        Assert.assertEquals(true, r.getRideConfirmed());
    }

    @Test
    public void deny_request_returns_false_when_denied(){
        reqi.deny_request(jid);
        Request r = reqi.getRequestRid(rid);
        Assert.assertEquals(false, r.getRideConfirmed());
    }

    @Test
    public void viewAllRequest_show_all_request(){
        Assert.assertEquals(1, reqi.viewAllRequest().size());
    }

    @Test
    public void getRequestRid_returns_false_when_there_exists_request(){
        Request re = reqi.getRequestRid(rid);
        Assert.assertEquals(false, re.isNull());
    }

    @Test
    public void calculateRide_returnsss_total_rides(){
        int total = reqi.calculateRides(jid);
        Assert.assertEquals(0, total);
    }
}

