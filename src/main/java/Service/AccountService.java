package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /*
     * TODO: The registration will be successful if and only if the username is not blank, the password is at 
     *  least 4 characters long, and an Account with that username does not already exist. If all these conditions 
     *  are met, the response body should contain a JSON of the Account, including its account_id. The response 
     *  status should be 200 OK, which is the default. The new account should be persisted to the database. 
     *  If the registration is not successful, the response status should be 400. (Client error)
     */
    public Account register(Account acc) {
        if (acc.getUsername() == null || acc.getPassword().length() < 4 || accountDAO.accountExists(acc) != null) {
            return null;
        } 

        return accountDAO.createAccount(acc);
    }

    /*
     * TODO: The login will be successful if and only if the username and password provided in the request body 
     *  JSON match a real account existing on the database. If successful, the response body should contain a 
     *  JSON of the account in the response body, including its account_id. The response status should be 200 OK, 
     *  which is the default.
     *  If the login is not successful, the response status should be 401. (Unauthoriz
    */
    public Account login(Account a) {
        return accountDAO.accountExists(a);
    }
}


