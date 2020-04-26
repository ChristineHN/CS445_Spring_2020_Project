package edu.iit.cs445;

import edu.iit.cs445.Account.Rate;
import edu.iit.cs445.Boundary.AccountInteractorB;
import edu.iit.cs445.Boundary.RateInteractorB;
import edu.iit.cs445.Boundary.RequestInteractorB;
import edu.iit.cs445.Boundary.RideInteractorB;
import edu.iit.cs445.Interactor.AccountInteractor;
import edu.iit.cs445.Interactor.RateInteractor;
import edu.iit.cs445.Interactor.RequestInteractor;
import edu.iit.cs445.Interactor.RideInteractor;
import edu.iit.cs445.Ride.CarInfo;
import edu.iit.cs445.Ride.DateTime;
import edu.iit.cs445.Ride.LocationInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class RateTest {
    private AccountInteractorB ai = new AccountInteractor();
    private RideInteractorB ri = new RideInteractor();
    private RequestInteractorB reqi = new RequestInteractor();
    private RateInteractorB rai = new RateInteractor();
    private String driver;
    private String rider;
    private String rid;
    private String jid;
    private String sid;

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
        String sent_by_id = "67";
        int rating = 5;
        String comment = "nice";
        sid = rai.rateAccount(driver, rid, rider, rating, comment);
    }
    @After
    public void tearUp(){
        ai.viewAllAccount().clear();
        ri.viewAllRides().clear();
        reqi.viewAllRequest().clear();
        rai.viewRateDriver().clear();
        rai.viewRateRider().clear();
    }

    @Test
    public void rateAccount_creates_rating(){
        String sid1 = rai.rateAccount(rider, rid, driver, 3, "nice");
        Assert.assertEquals(1, rai.viewRateRider().size());
        Assert.assertEquals(1, rai.viewRateDriver().size());
    }

    @Test
    public void getRiderComments(){
        List<Rate> r = rai.getRiderComments(rider);
        Assert.assertEquals(0, r.size());
    }

    @Test
    public void calculateAvgRiderRating(){
        Double avg = rai.calculateAvgRiderRating(rider);
        Assert.assertNull(avg);
    }

    @Test
    public void calculateAvgDriverRating(){
        Double avg = rai.calculateAvgDriverRating(driver);
        Assert.assertEquals(5.0, avg, 0.1);
    }

    @Test
    public void getDriverComments(){
        List<Rate> r = rai.getDriverComments(driver);
        Assert.assertEquals(1, r.size());
    }

}
