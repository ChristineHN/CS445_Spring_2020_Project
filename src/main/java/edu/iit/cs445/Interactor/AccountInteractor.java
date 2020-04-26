package edu.iit.cs445.Interactor;

import edu.iit.cs445.Account.Account;
import edu.iit.cs445.Boundary.AccountInteractorB;
import org.glassfish.grizzly.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class AccountInteractor implements AccountInteractorB {
    private static List<Account> acc = new ArrayList<>();
    public String createAccount(String firstN, String lastN, String phone, String pic){
        Account a = new Account(firstN, lastN, phone, pic);
        acc.add(a);
        return a.getAid();
    }

    public void activateAccount(String aid){
        for(Account a : acc){
            if(a.getAid().equals(aid)){
                a.setIsActive(true);
            }
        }
        return;
    }

    public void updateAccount(String aid, String firstN, String lastN, String phone, String pic){
        for(Account a : acc){
            if(a.getAid().equals(aid)){
                a.setFirstName(firstN);
                a.setLastName(lastN);
                a.setPhone(phone);
                a.setPicture(pic);
            }
        }
        return;
    }
    public void deleteAccount(String aid){
        Account a = findAccount(aid);
        acc.remove(a);
    }

    public List<Account> viewAllAccount(){
        return acc;
    }

    public List<Account> searchAccounts(String key){
        if (key.isEmpty()){
            return acc;
        }
        List<Account> ret = new ArrayList<>();
        for(Account a : acc){
            if(a.getFirstName().toLowerCase().contains(key) ||
                    a.getLastName().toLowerCase().contains(key)||
                    a.getPhone().toLowerCase().contains(key)){
                ret.add(a);
            }
        }
        return ret;
    }


    @Override
    public Account findAccount(String aid) {
        for(Account a : acc){
            if(a.getAid().equals(aid)){
                return a;
            }
        }
        return null;
    }
}

