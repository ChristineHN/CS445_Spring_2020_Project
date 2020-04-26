package edu.iit.cs445;

import edu.iit.cs445.Account.Account;
import edu.iit.cs445.Boundary.AccountInteractorB;
import edu.iit.cs445.Interactor.AccountInteractor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AccountTest {

    private AccountInteractorB ai = new AccountInteractor();
    private String aid;
    @Before
    public void setUp(){
        aid = ai.createAccount("foo","bar","111-222-4444", "http://test.com");
    }
    @After
    public void tearUp(){
        ai.viewAllAccount().clear();
    }

    @Test
    public void activateAccount_should_change_isActive_to_true(){
        Account a = ai.findAccount(aid);
        Assert.assertFalse(a.getIsActive());
        ai.activateAccount(aid);
        Assert.assertTrue(a.getIsActive());
    }

    @Test
    public void updateAccount_updates_info(){
        ai.updateAccount(aid, "fn", "ln", "123-456-7890", "http://test1.com");
        Account a = ai.findAccount(aid);
        Assert.assertEquals("fn", a.getFirstName());
        Assert.assertEquals("ln", a.getLastName());
        Assert.assertEquals("123-456-7890", a.getPhone());
        Assert.assertEquals("http://test1.com", a.getPicture());
    }

    @Test
    public void deleteAccount_successfully_deletes_an_account(){
        ai.deleteAccount(aid);
        Assert.assertEquals(0, ai.viewAllAccount().size());
    }

    @Test
    public void searchAccounts_lookup_the_corresponding_account(){
        List<Account> a = ai.searchAccounts("foo");
        Assert.assertEquals(1, a.size());
    }
}
