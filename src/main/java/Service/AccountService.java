package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public Account register(Account acc) {
        if (acc.getUsername() == "" || acc.getPassword().length() < 4 || accountDAO.accountExists(acc) != null) {
            return null;
        } 
        return accountDAO.createAccount(acc);
    }

    public Account login(Account a) {
        return accountDAO.accountExists(a);
    }
}


