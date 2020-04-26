package edu.iit.cs445.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import edu.iit.cs445.Account.Account;
import edu.iit.cs445.Account.Rate;
import edu.iit.cs445.Boundary.*;
import edu.iit.cs445.Interactor.*;
import edu.iit.cs445.Message.Message;
import edu.iit.cs445.Request.Request;
import edu.iit.cs445.Ride.CarInfo;
import edu.iit.cs445.Ride.DateTime;
import edu.iit.cs445.Ride.LocationInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.stream.Location;

import com.google.gson.JsonObject;
import edu.iit.cs445.Ride.Ride;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Path("rides")
public class RideController {
    RideInteractorB ride = new RideInteractor();
    AccountInteractorB acc = new AccountInteractor();
    RateInteractorB rate = new RateInteractor();
    RequestInteractorB rib = new RequestInteractor();
    MessageInteractorB mib = new MessageInteractor();
    Gson g = new Gson();
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRide(String json){
        JsonObject jso = new JsonParser().parse(json).getAsJsonObject();
        String aid = jso.get("aid").getAsString();
        Account at = acc.findAccount(aid);
        if(at.getIsActive() == false){
            JsonObject error = new JsonObject();
            error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
            error.addProperty("title","Your request data didn't pass validation");
            error.addProperty("detail","This account ("+ aid +") is not active, may not create a ride.");
            error.addProperty("status", 400);
            error.addProperty("instance", "/rides");
            return Response.status(Response.Status.BAD_REQUEST).entity(g.toJson(error)).build();
        }
        JsonObject l = jso.get("location_info").getAsJsonObject();
        LocationInfo location_info = this.extractLocationInfo(l);
        JsonObject d = jso.get("date_time").getAsJsonObject();
        DateTime dt = this.extractDateTime(d);
        JsonObject c = jso.get("car_info").getAsJsonObject();
        CarInfo ci = this.extractCarInfo(c);
        int max_passengers = jso.get("max_passengers").getAsInt();
        double amount_per_passenger = jso.get("amount_per_passenger").getAsDouble();
        String conditions = jso.get("conditions").getAsString();
        String rid = ride.createRide(aid, location_info, dt, ci, max_passengers, amount_per_passenger, conditions);
        JsonObject r = new JsonObject();
        r.addProperty("rid", rid);
        return Response.status(Response.Status.CREATED).entity(g.toJson(r)).build();
}
    @Path("{rid}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRide(String json, @PathParam("rid") String rid){
        JsonObject jso = new JsonParser().parse(json).getAsJsonObject();
        JsonObject l = jso.get("location_info").getAsJsonObject();
        LocationInfo location_info = this.extractLocationInfo(l);
        JsonObject d = jso.get("date_time").getAsJsonObject();
        DateTime dt = this.extractDateTime(d);
        JsonObject c = jso.get("car_info").getAsJsonObject();
        CarInfo ci = this.extractCarInfo(c);
        int max_passengers = jso.get("max_passengers").getAsInt();
        double amount_per_passenger = jso.get("amount_per_passenger").getAsDouble();
        String conditions = jso.get("conditions").getAsString();
        ride.updateRide(rid,location_info, dt, ci, max_passengers, amount_per_passenger, conditions);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Path("{rid}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRide(@PathParam("rid") String rid){
        ride.deleteRide(rid);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllRide(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("date") String date){
        List<Ride> allRide = ride.viewAllRides();
        if ((from == null && to == null && date == null) || (from.isEmpty() && to.isEmpty() && date.isEmpty())){
            JsonArray arr = new JsonArray();
            for(Ride ride : allRide){
                JsonObject obj = new JsonObject();
                obj.addProperty("rid", ride.getRId());
                JsonObject l = new JsonObject();
                l.addProperty("from_city", ride.getLocationInfo().getFromCity());
                l.addProperty("from_zip", ride.getLocationInfo().getFromZip());
                l.addProperty("to_city", ride.getLocationInfo().getToCity());
                l.addProperty("to_zip", ride.getLocationInfo().getToZip());
                obj.add("location_info", l);
                JsonObject d = new JsonObject();
                d.addProperty("date", ride.getDateTime().getDate());
                d.addProperty("time", ride.getDateTime().getTime());
                obj.add("date_time", d);
                arr.add(obj);
            }
            return Response.status(Response.Status.OK).entity(g.toJson(arr)).build();
        }
        else{
            final String pattern_from = from.isEmpty() ? "[a-zA-Z]+" : from;
            final String pattern_to = to.isEmpty() ? "[a-zA-Z]+" : to;
            final String pattern_date = date.isEmpty() ? "[0-9]{2}-[a-zA-Z]{3}-[0-9]{4}" : date;
            List<Ride> results = allRide.stream().filter(ride->ride.getLocationInfo().getFromCity().toLowerCase().matches(pattern_from) && ride.getLocationInfo().getToCity().toLowerCase().matches(pattern_to) && ride.getDateTime().getDate().toLowerCase().matches(pattern_date))
            .collect(Collectors.toList());
            JsonArray arr = new JsonArray();
            for(Ride ride : results){
                JsonObject obj = new JsonObject();
                obj.addProperty("rid", ride.getRId());
                JsonObject l = new JsonObject();
                l.addProperty("from_city", ride.getLocationInfo().getFromCity());
                l.addProperty("from_zip", ride.getLocationInfo().getFromZip());
                l.addProperty("to_city", ride.getLocationInfo().getToCity());
                l.addProperty("to_zip", ride.getLocationInfo().getToZip());
                obj.add("location_info", l);
                JsonObject d = new JsonObject();
                d.addProperty("date", ride.getDateTime().getDate());
                d.addProperty("time", ride.getDateTime().getTime());
                obj.add("date_time", d);
                arr.add(obj);
            }
            return Response.status(Response.Status.OK).entity(g.toJson(arr)).build();
        }

    }

    @Path("{rid}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewRideDetail(@PathParam("rid") String rid){
        Ride r = ride.getRideFromRid(rid);
        if(r.isNull()){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("rid", r.getRId());
        JsonObject l = new JsonObject();
        l.addProperty("from_city", r.getLocationInfo().getFromCity());
        l.addProperty("from_zip", r.getLocationInfo().getFromZip());
        l.addProperty("to_city", r.getLocationInfo().getToCity());
        l.addProperty("to_zip", r.getLocationInfo().getToZip());
        obj.add("location_info", l);
        JsonObject d = new JsonObject();
        d.addProperty("date", r.getDateTime().getDate());
        d.addProperty("time", r.getDateTime().getTime());
        obj.add("date_time", d);
        JsonObject c = new JsonObject();
        c.addProperty("make", r.getCarInfo().getMake());
        c.addProperty("model", r.getCarInfo().getModel());
        c.addProperty("color", r.getCarInfo().getColor());
        c.addProperty("plate_state", r.getCarInfo().getPlateState());
        c.addProperty("plate_serial", r.getCarInfo().getPlateSerial());
        obj.add("car_info", c);
        String aid = r.getAid();
        Account a = acc.findAccount(aid);
        obj.addProperty("max_passengers",r.getMaxPassengers());
        obj.addProperty("amount_per_passenger",r.getAmountPerPassenger());
        obj.addProperty("conditions",r.getConditions());
        obj.addProperty("driver", a.getFirstName());
        obj.addProperty("driver_picture", a.getPicture());
        obj.addProperty("rides", ride.calculateRides(aid));
        obj.addProperty("ratings", rate.calculateDriverRating(aid));
        obj.addProperty("average_rating", rate.calculateAvgDriverRating(aid));
        JsonArray comments = new JsonArray();
        List<Rate> cad = rate.getDriverComments(aid);
        for(Rate rate : cad){
            JsonObject o = new JsonObject();
            o.addProperty("rid", rate.getRid());
            o.addProperty("date", rate.getDate());
            o.addProperty("rating", rate.getRating());
            o.addProperty("comment", rate.getComment());

            comments.add(o);
        }
        obj.add("comments_about_driver",comments);
        return Response.status(Response.Status.OK).entity(g.toJson(obj)).build();
    }
    @Path("{rid}/join_requests")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestToJoinRide(String json, @PathParam("rid") String rid) {
        JsonObject a = new JsonParser().parse(json).getAsJsonObject();
        String aid = a.get("aid").getAsString();
        int passengers = a.get("passengers").getAsInt();
        if(!acc.findAccount(aid).getIsActive()){
            JsonObject error = new JsonObject();
            error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
            error.addProperty("title","Your request data didn't pass validation");
            error.addProperty("detail","This account (" + aid + ") is not active, may not create a join ride request.");
            error.addProperty("status", 400);
            error.addProperty("instance", "/rides/"+ rid +"/join_requests");

            return Response.status(Response.Status.BAD_REQUEST).entity(g.toJson(error)).build();
        }
        String jid = rib.createRequest(aid, passengers, rid);
        JsonObject ret = new JsonObject();
        ret.addProperty("jid", jid);
        return Response.status(Response.Status.CREATED).header("Location", "Location").entity(g.toJson(ret)).build();
    }

    @Path("{rid}/join_requests/{jid}")
    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response confirm_deny_aJoinRideRequest(String json,@PathParam("rid") String rid, @PathParam("jid") String jid ){
        JsonObject a = new JsonParser().parse(json).getAsJsonObject();
        String aid = a.get("aid").getAsString();
        if(a.has("ride_confirmed")){
            Boolean rideConfirm = a.get("ride_confirmed").getAsBoolean();
            if(rideConfirm == null){
                JsonObject error = new JsonObject();
                error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
                error.addProperty("title","Your request data didn't pass validation");
                error.addProperty("detail","Invalid value for ride_confirmed");
                error.addProperty("status", 400);
                error.addProperty("instance", "/rides/"+ rid +"/join_requests/"+ jid);
            }
            Request found = rib.viewAllRequest().stream().filter(request -> request.getJId().equals(jid)).findAny().orElse(null);
            found.setRide_confirmed(rideConfirm);
        }
        else if(a.has("pickup_confirmed")){
            Boolean pickupConfirm = a.get("pickup_confirmed").getAsBoolean();
            if(pickupConfirm == null || !pickupConfirm){
                JsonObject error = new JsonObject();
                error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
                error.addProperty("title","Your request data didn't pass validation");
                error.addProperty("detail","Invalid value for pickup_confirmed");
                error.addProperty("status", 400);
                error.addProperty("instance", "/rides/"+ rid +"/join_requests/"+ jid);
            }
            Request found = rib.viewAllRequest().stream().filter(request -> request.getJId().equals(jid)).findAny().orElse(null);
            found.setPickup_confirmed(pickupConfirm);
        }
        return Response.status(Response.Status.OK).build();
    }

    @Path("{rid}/messages")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMessageToRide(String json, @PathParam("rid") String rid){
        JsonObject a = new JsonParser().parse(json).getAsJsonObject();
        String aid = a.get("aid").getAsString();
        String msg = a.get("msg").getAsString();
        Message m = new Message(aid, msg, rid);
        mib.addMessageToRide(m);
        JsonObject ret = new JsonObject();
        ret.addProperty("mid", m.getMid());
        return Response.status(Response.Status.CREATED).header("Location", "Location").entity(g.toJson(ret)).build();
    }

    @Path("{rid}/messages")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllRideMessages(@PathParam("rid") String rid){
        JsonArray a = new JsonArray();
        for(Message m : mib.viewAllRideMessages(rid)){
            JsonObject b = new JsonObject();
            b.addProperty("mid", m.getMid());
            b.addProperty("sent_by_aid", m.getSentByAid());
            b.addProperty("date", m.getDate());
            b.addProperty("body", m.getBody());
            a.add(b);
        }
        return Response.status(Response.Status.OK).entity(g.toJson(a)).build();
    }

    private LocationInfo extractLocationInfo(JsonObject l) {
        String from_city = l.get("from_city").getAsString();
        String from_zip = l.get("from_zip").getAsString();
        String to_city = l.get("to_city").getAsString();
        String to_zip = l.get("to_zip").getAsString();
        LocationInfo li = new LocationInfo(from_city, from_zip, to_city, to_zip);
        return li;
    }
    private DateTime extractDateTime(JsonObject d){
        String date = d.get("date").getAsString();
        String time = d.get("time").getAsString();
        DateTime dt = new DateTime(date, time);
        return dt;
    }

    private CarInfo extractCarInfo(JsonObject c){
        String make = c.get("make").getAsString();
        String model = c.get("model").getAsString();
        String color = c.get("color").getAsString();
        String plate_state = c.get("plate_state").getAsString();
        String plate_serial = c.get("plate_serial").getAsString();
        CarInfo ci = new CarInfo(make, model, color, plate_state, plate_serial);
        return ci;
    }

}


