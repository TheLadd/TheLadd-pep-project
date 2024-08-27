package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {
    /*
     * TODO: create account
     */
    public Account createAccount(Account a) {
        return null;
    }

    /*
     * TODO: Check if account exists
     */
    public Account accountExists(Account a) {
        Connection c = ConnectionUtil.getConnection();
        
        try {
            // Prepare and execute SQL statement
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ResultSet rs = ps.executeQuery();

            // Parse result
            while (rs.next()) {
                Account foundAcc = new Account(
                    rs.getString("username"),
                    rs.getString("password")
                );
                return foundAcc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
}
