package DAO;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import org.h2.command.Prepared;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public Message createMessage(Message msg) {         
        Connection c = ConnectionUtil.getConnection();
        try {
            // Prepare and execute SQL query
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());
            int rowsAffected = ps.executeUpdate();

            // Parse result
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            while (pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                Message createdMsg = new Message(
                    generated_message_id,
                    msg.getPosted_by(),
                    msg.getMessage_text(),
                    msg.getTime_posted_epoch()
                );
                return createdMsg;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    /*
     * TODO: Get all messages
     */
    public List<Message> getAllMessages() {
        Connection c = ConnectionUtil.getConnection();
        try {
            // Prepare and execute query
            String sql = "SELECT * FROM message";
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Parse and return results
            List<Message> msgs = new ArrayList<>();
            Message temp;
            while (rs.next()) {
                temp = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                msgs.add(temp);
            }
            return msgs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * TODO: Get a single message by its ID
     */
    public Message getMessageById(int id) {
        return null;
    }

    /*
     * TODO: Delete a message by its ID
     */
    public Message deleteMessageById(int id) {
        return null;
    }

    /*
     * TODO: Update a message by its ID
     */
    public Message updateMessageById(int id) {
        return null;
    }

    /*
     * TODO: Get all messages from an account given its poster's account ID
     */
    public List<Message> getAllMessagesByAccountId(int accountId) {
        return null;
    }
}
