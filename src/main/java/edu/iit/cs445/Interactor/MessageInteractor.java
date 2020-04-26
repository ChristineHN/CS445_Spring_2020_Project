package edu.iit.cs445.Interactor;

import edu.iit.cs445.Boundary.MessageInteractorB;
import edu.iit.cs445.Message.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageInteractor implements MessageInteractorB {
    private static List<Message> msg = new ArrayList<>();
    public void addMessageToRide(Message message){
        msg.add(message);
    }
    public List<Message> viewAllRideMessages(String rid){
        List<Message> ret = new ArrayList<>();
        for(Message m : msg){
            if(m.getRid().equals(rid)){
                ret.add(m);
            }
        }
        return ret;
    }
}
