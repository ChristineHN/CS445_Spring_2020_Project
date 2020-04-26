package edu.iit.cs445.Boundary;

import edu.iit.cs445.Account.Account;

import java.util.List;

public interface AccountInteractorB {

    Account findAccount(String aid);

    String createAccount(String first_name, String last_name, String phone, String picture);

    void updateAccount(String aid, String firstN, String lastN, String phone, String pic);

    void deleteAccount(String aid);

    List<Account> viewAllAccount();

    List<Account> searchAccounts(String key);

    void activateAccount(String aid);
}
