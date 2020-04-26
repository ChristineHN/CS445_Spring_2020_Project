package edu.iit.cs445;

import edu.iit.cs445.Boundary.AccountInteractorB;
import edu.iit.cs445.Boundary.RideInteractorB;
import edu.iit.cs445.Interactor.AccountInteractor;
import edu.iit.cs445.Interactor.RideInteractor;
import edu.iit.cs445.Ride.CarInfo;
import edu.iit.cs445.Ride.DateTime;
import edu.iit.cs445.Ride.LocationInfo;
import edu.iit.cs445.Ride.Ride;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class RideTest {
    private AccountInteractorB ai = new AccountInteractor();
    private RideInteractorB ri = new RideInteractor();
    private String aid;
    private String rid;
    @Before
    public void setUp(){
        LocationInfo l = new LocationInfo("Chicago", "60605", "Hollywood", "20464");
        DateTime d = new DateTime("12-May-2020", "03:12:34");
        CarInfo c = new CarInfo("Audi", "A4", "Grey", "IL", "PJ2020");
        int max_passengers = 2;
        double amount_per_passenger = 15.00;
        String conditions = "no conditions";
        rid = ri.createRide(aid, l, d, c, max_passengers, amount_per_passenger, conditions);
        aid = ai.createAccount("foo","bar","111-222-4444", "http://test.com");
    }
    @After
    public void tearUp(){
        ai.viewAllAccount().clear();
        ri.viewAllRides().clear();
    }

    @Test
    public void createRide_should_create_a_new_ride(){
        LocationInfo l = new LocationInfo("Chicago", "60605", "Hollywood", "20464");
        DateTime d = new DateTime("12-May-2020", "03:12:34");
        CarInfo c = new CarInfo("Audi", "A4", "Grey", "IL", "PJ2020");
        int max_passengers = 2;
        double amount_per_passenger = 15.00;
        String conditions = "no conditions";
        String r = ri.createRide(aid, l, d, c, max_passengers, amount_per_passenger, conditions);
        Assert.assertEquals(2, ri.viewAllRides().size());
    }

    @Test
    public void updateRide_updates_info(){
        LocationInfo l = new LocationInfo("Chicago", "60616", "Barrington", "60010");
        DateTime d = new DateTime("31-Apr-2019", "11:45:52");
        CarInfo c = new CarInfo("BMW", "X5", "White", "IL", "IL11345");
        int max_passengers = 4;
        double amount_per_passenger = 13.00;
        String conditions = "No pets are allowed";
        ri.updateRide(rid, l, d, c, max_passengers, amount_per_passenger, conditions);
        Ride r = ri.getRideFromRid(rid);
        Assert.assertEquals("Chicago", r.getLocationInfo().getFromCity());
        Assert.assertEquals("60616", r.getLocationInfo().getFromZip());
        Assert.assertEquals("Barrington", r.getLocationInfo().getToCity());
        Assert.assertEquals("60010", r.getLocationInfo().getToZip());
        Assert.assertEquals("31-Apr-2019", r.getDateTime().getDate());
        Assert.assertEquals("11:45:52", r.getDateTime().getTime());
        Assert.assertEquals("BMW", r.getCarInfo().getMake());
        Assert.assertEquals("X5", r.getCarInfo().getModel());
        Assert.assertEquals("White", r.getCarInfo().getColor());
        Assert.assertEquals("IL", r.getCarInfo().getPlateState());
        Assert.assertEquals("IL11345", r.getCarInfo().getPlateSerial());
        Assert.assertEquals(4, r.getMaxPassengers());
        Assert.assertEquals(13.00, r.getAmountPerPassenger(), 0.1);
        Assert.assertEquals("No pets are allowed", r.getConditions());
    }

    @Test
    public void deleteRide(){
        ri.deleteRide(rid);
        Assert.assertEquals(0, ri.viewAllRides().size());
    }

    @Test
    public void getRideFromRid_gets_corresponding_ride(){
        Ride r = ri.getRideFromRid(rid);
        Assert.assertEquals("Chicago", r.getLocationInfo().getFromCity());
        Assert.assertEquals("60605", r.getLocationInfo().getFromZip());
        Assert.assertEquals("Hollywood", r.getLocationInfo().getToCity());
        Assert.assertEquals("20464", r.getLocationInfo().getToZip());
        Assert.assertEquals("12-May-2020", r.getDateTime().getDate());
        Assert.assertEquals("03:12:34", r.getDateTime().getTime());
        Assert.assertEquals("Audi", r.getCarInfo().getMake());
        Assert.assertEquals("A4", r.getCarInfo().getModel());
        Assert.assertEquals("Grey", r.getCarInfo().getColor());
        Assert.assertEquals("IL", r.getCarInfo().getPlateState());
        Assert.assertEquals("PJ2020", r.getCarInfo().getPlateSerial());
        Assert.assertEquals(2, r.getMaxPassengers());
        Assert.assertEquals(15.00, r.getAmountPerPassenger(), 0.1);
        Assert.assertEquals("no conditions", r.getConditions());
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void calculateRides_calculates_total_rides(){
        int total = ri.calculateRides(aid);
        Assert.assertEquals(0, total);
    }
}
