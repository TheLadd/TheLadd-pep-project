package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// import com.azul.crs.client.Result;

import Util.ConnectionUtil;
import Model.Account;

public class AccountDAO {
    /*
     * TODO: create account
     */
    public Account createAccount(Account a) {
        Connection c = ConnectionUtil.getConnection();

        try {
            // Prepare and execute SQL statement
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            int rowsAffected = ps.executeUpdate();

            // Parse result for the generated account_id, create appropriate return value
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            while (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, a.getUsername(), a.getPassword());
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
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
                    rs.getInt("account_id"),
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
