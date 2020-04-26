package edu.iit.cs445.Boundary;

import edu.iit.cs445.Account.Rate;

import java.util.Collection;
import java.util.List;

public interface RateInteractorB {

    int calculateDriverRating(String aid);

    Double calculateAvgDriverRating(String aid);

    List<Rate> getDriverComments(String aid);

    String rateAccount(String aid, String rid, String sent_by_id, int rating, String comment);

    List<Rate> getRiderComments(String aid);

    Double calculateAvgRiderRating(String aid);

    List<Rate> viewRateRider();

    List<Rate> viewRateDriver();
}
