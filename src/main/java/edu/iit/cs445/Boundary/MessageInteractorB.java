package edu.iit.cs445.Boundary;

import edu.iit.cs445.Message.Message;

import java.util.List;

public interface MessageInteractorB {
    void addMessageToRide(Message message);
    List<Message> viewAllRideMessages(String rid);
}

