package edu.iit.cs445;

import edu.iit.cs445.Boundary.*;
import edu.iit.cs445.Interactor.*;
import edu.iit.cs445.Message.Message;
import edu.iit.cs445.Ride.CarInfo;
import edu.iit.cs445.Ride.DateTime;
import edu.iit.cs445.Ride.LocationInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {
    private AccountInteractorB ai = new AccountInteractor();
    private RideInteractorB ri = new RideInteractor();
    private RequestInteractorB reqi = new RequestInteractor();
    private MessageInteractorB mi = new MessageInteractor();

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
    public void addMesssageToRide(){
        Message m = new Message(driver, "hi", rid);
        mi.addMessageToRide(m);
        Assert.assertEquals(1, mi.viewAllRideMessages(rid).size());
    }

    @Test
    public void viewAllRideMessage(){
        Message m = new Message(driver, "hi", rid);
        mi.addMessageToRide(m);
        Assert.assertEquals(1, mi.viewAllRideMessages(rid).size());
        Assert.assertEquals(0, mi.viewAllRideMessages("asdf").size());
    }
}
