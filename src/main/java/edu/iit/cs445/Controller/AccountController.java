package edu.iit.cs445.Controller;

import com.google.gson.*;
import edu.iit.cs445.Account.Account;
import edu.iit.cs445.Account.Rate;
import edu.iit.cs445.Boundary.AccountInteractorB;
import edu.iit.cs445.Boundary.RateInteractorB;
import edu.iit.cs445.Boundary.RequestInteractorB;
import edu.iit.cs445.Boundary.RideInteractorB;
import edu.iit.cs445.Interactor.AccountInteractor;
import edu.iit.cs445.Interactor.RateInteractor;
import edu.iit.cs445.Interactor.RequestInteractor;
import edu.iit.cs445.Interactor.RideInteractor;
import edu.iit.cs445.Request.Request;
import edu.iit.cs445.Ride.Ride;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("accounts")
public class AccountController {
    AccountInteractorB acc = new AccountInteractor();
    RateInteractorB rib = new RateInteractor();
    Gson g = new GsonBuilder().serializeNulls().create();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAccount(String json){
        JsonObject jso = new JsonParser().parse(json).getAsJsonObject();
        String first_name = jso.get("first_name").getAsString();
        String last_name = jso.get("last_name").getAsString();
        String phone = jso.get("phone").getAsString();
        if(!isPhoneValid(phone)){
            JsonObject error = new JsonObject();
            error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
            error.addProperty("title","Your request data didn't pass validation");
            error.addProperty("detail","Invalid phone number");
            error.addProperty("status", 400);
            error.addProperty("instance", "/accounts");
            return Response.status(Response.Status.BAD_REQUEST).entity(g.toJson(error)).build();
        }
        String picture = jso.get("picture").getAsString();
        Boolean is_active = jso.get("is_active").getAsBoolean();
        if(is_active){
            JsonObject error = new JsonObject();
            error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
            error.addProperty("title","Your request data didn't pass validation");
            error.addProperty("detail","Invalid  value for is_active");
            error.addProperty("status", 400);
            error.addProperty("instance", "/accounts");
            return Response.status(Response.Status.BAD_REQUEST).entity(g.toJson(error)).build();
        }
        String aid = acc.createAccount(first_name, last_name, phone, picture);
        JsonObject ret = new JsonObject();
        ret.addProperty("aid", aid);
        return Response.status(Response.Status.CREATED).entity(g.toJson(ret)).build();
    }

    @Path("{aid}/status")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response activateAccount(String json, @PathParam("aid") String aid){
        Account a = acc.findAccount(aid);
        if(a == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        JsonObject jso = new JsonParser().parse(json).getAsJsonObject();
        Boolean is_active = jso.get("is_active").getAsBoolean();
        if(is_active == false || is_active == null){
            JsonObject error = new JsonObject();
            error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
            error.addProperty("title","Your request data didn't pass validation");
            error.addProperty("detail","Invalid  value for is_active");
            error.addProperty("status", 400);
            error.addProperty("instance", "/accounts/"+aid+"/status");
        }
        String phone = jso.get("phone").getAsString();
        if(!isPhoneValid(phone)){
            JsonObject error = new JsonObject();
            error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
            error.addProperty("title","Your request data didn't pass validation");
            error.addProperty("detail","Invalid phone number");
            error.addProperty("status", 400);
            error.addProperty("instance", "/accounts/"+aid+"/status");
            return Response.status(Response.Status.BAD_REQUEST).entity(g.toJson(error)).build();
        }
        acc.activateAccount(aid);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Path("{aid}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccount(String json, @PathParam("aid") String aid){
        Account a = acc.findAccount(aid);
        if(a == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        JsonObject jso = new JsonParser().parse(json).getAsJsonObject();
        String firstN = jso.get("first_name").getAsString();
        String lastN = jso.get("last_name").getAsString();
        String phone = jso.get("phone").getAsString();
        if(!isPhoneValid(phone)){
            JsonObject error = new JsonObject();
            error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
            error.addProperty("title","Your request data didn't pass validation");
            error.addProperty("detail","Invalid phone number");
            error.addProperty("status", 400);
            error.addProperty("instance", "/accounts");
            return Response.status(Response.Status.BAD_REQUEST).entity(g.toJson(error)).build();
        }
        String pic = jso.get("picture").getAsString();
        Boolean is_active = jso.get("is_active").getAsBoolean();
        if(is_active){
            JsonObject error = new JsonObject();
            error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
            error.addProperty("title","Your request data didn't pass validation");
            error.addProperty("detail","Invalid  value for is_active");
            error.addProperty("status", 400);
            error.addProperty("instance", "/accounts");
            return Response.status(Response.Status.BAD_REQUEST).entity(g.toJson(error)).build();
        }
        acc.updateAccount(aid, firstN, lastN, phone, pic);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Path("{aid}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAccount(@PathParam("aid") String aid){
        Account a = acc.findAccount(aid);
        if(a == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        acc.deleteAccount(aid);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewAllAccount(@QueryParam("key") String key){
        JsonArray ja = new JsonArray();
        if(key==null || key.isEmpty()) {
            for (Account a : acc.viewAllAccount()) {
                JsonObject b = new JsonObject();
                b.addProperty("aid", a.getAid());
                b.addProperty("name", a.getFirstName() + " " + a.getLastName());
                b.addProperty("date_created", a.getDate_created());
                b.addProperty("is_active", a.getIsActive());
                ja.add(b);
            }
            return Response.status(Response.Status.OK).entity(g.toJson(ja)).build();
        }
        else{
            for (Account a : acc.searchAccounts(key)) {
                JsonObject b = new JsonObject();
                b.addProperty("aid", a.getAid());
                b.addProperty("name", a.getFirstName() + " " + a.getLastName());
                b.addProperty("date_created", a.getDate_created());
                b.addProperty("is_active", a.getIsActive());
                ja.add(b);
            }
            return Response.status(Response.Status.OK).entity(g.toJson(ja)).build();
        }
    }

    @Path("{aid}/ratings")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response rateAccount(String json, @PathParam("aid") String aid){
        JsonObject a = new JsonParser().parse(json).getAsJsonObject();
        String rid = a.get("rid").getAsString();
        String sent_by_id = a.get("sent_by_id").getAsString();
        int rating = a.get("rating").getAsInt();
        String comment = a.get("comment").getAsString();
        if(!isSent_by_idValid(sent_by_id,rid) || !isSent_by_idValid(aid,rid)){
            JsonObject error = new JsonObject();
            error.addProperty("type","http://cs.iit.edu/~virgil/cs445/project/api/problems/data-validation" );
            error.addProperty("title","Your request data didn't pass validation");
            error.addProperty("detail","This account (" + sent_by_id+ ") didn't create this ride ("+ rid + ") nor was it a passenger");
            error.addProperty("status", 400);
            error.addProperty("instance", "/accounts/"+ aid +"/ratings");
            return Response.status(Response.Status.BAD_REQUEST).entity(g.toJson(error)).build();
        }

        String sid = rib.rateAccount(aid, rid, sent_by_id, rating, comment);
        JsonObject ret = new JsonObject();
        ret.addProperty("sid", sid);
        return Response.status(Response.Status.CREATED).header("Location", "Location").entity(g.toJson(ret)).build();
    }

    private boolean isSent_by_idValid(String sent_by_id, String rid) {

        RequestInteractorB reqIb = new RequestInteractor();
        Request req = reqIb.getRequestRid(rid);
        String passenger = req.getAid();
        RideInteractorB rideIb = new RideInteractor();
        Ride r = rideIb.getRideFromRid(rid);
        String creator = r.getAid();
        if(creator.equals(sent_by_id) || passenger.equals(sent_by_id)){
            return true;
        }
        return false;
    }

    @Path("{aid}/driver")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewDriverRatings(String json, @PathParam("aid") String aid) {
        JsonObject driver = new JsonObject();
        Account d = acc.findAccount(aid);
        driver.addProperty("aid", d.getAid());
        driver.addProperty("first_name", d.getFirstName());
        driver.addProperty("rides", new RideInteractor().calculateRides(aid) );
        driver.addProperty("ratings", rib.getDriverComments(aid).size());
        driver.addProperty("average_rating", rib.calculateAvgDriverRating(aid));

        JsonArray detail = new JsonArray();
        for(Rate r : rib.getDriverComments(aid)){
            JsonObject b = new JsonObject();
            b.addProperty("rid", r.getRid());
            b.addProperty("sent_by_id", r.getSentById());
            b.addProperty("first_name", acc.findAccount(r.getSentById()).getFirstName());
            b.addProperty("date", r.getDate());
            b.addProperty("rating", r.getRating());
            b.addProperty("comment", r.getComment());
            detail.add(b);
        }
        driver.add("detail", detail);
        return Response.status(Response.Status.OK).entity(g.toJson(driver)).build();
    }
    @Path("{aid}/rider")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewRiderRatings(String json, @PathParam("aid") String aid) {
        JsonObject rider = new JsonObject();
        Account d = acc.findAccount(aid);


        rider.addProperty("aid", d.getAid());
        rider.addProperty("first_name", d.getFirstName());
        rider.addProperty("rides", new RequestInteractor().calculateRides(aid));
        rider.addProperty("ratings", rib.getRiderComments(aid).size());
        rider.addProperty("average_rating", rib.calculateAvgRiderRating(aid));

        JsonArray detail = new JsonArray();
        for(Rate r : rib.getRiderComments(aid)){
            JsonObject b = new JsonObject();
            b.addProperty("rid", r.getRid());
            b.addProperty("sent_by_id", r.getSentById());
            b.addProperty("first_name", acc.findAccount(r.getSentById()).getFirstName());
            b.addProperty("date", r.getDate());
            b.addProperty("rating", r.getRating());
            b.addProperty("comment", r.getComment());
            detail.add(b);
        }
        rider.add("detail", detail);
        return Response.status(Response.Status.OK).entity(g.toJson(rider)).build();
    }


    private Boolean isPhoneValid(String phone){
        String[] phones = phone.split("-");
        if(!phones[0].matches("\\d{3}") || !phones[1].matches("\\d{3}")){
            return false;
        }
        if(!phones[2].matches("\\d{4}")){
            return false;
        }
        return true;
    }
}
