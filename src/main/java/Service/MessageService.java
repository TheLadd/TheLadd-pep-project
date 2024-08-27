package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    MessageService() {
        messageDAO = new MessageDAO();
    }

    /*
     * TODO: The creation of the message will be successful if and only if the message_text is not blank, is under 
     *  255 characters, and posted_by refers to a real, existing user. If successful, the response body should 
     *  contain a JSON of the message, including its message_id. The response status should be 200, which is the 
     *  default. The new message should be persisted to the database. If the creation of the message is not 
     * successful, the response status should be 400. (Client error)
     */
    public Message createNewMessage(Message msg) {
        return null;
    }

    /*
     * TODO: The response body should contain a JSON representation of a list containing all messages retrieved from 
     *  the database. It is expected for the list to simply be empty if there are no messages. The response status 
     *  should always be 200, which is the default.
     */
    public List<Message> getAllMessages() {
        return null;
    }

    /*
     * TODO: The response body should contain a JSON representation of the message identified by the message_id. 
     *  It is expected for the response body to simply be empty if there is no such message. The response status 
     *  should always be 200, which is the default.
     */
    public Message getMessageById(int id) {
        return null;
    }

    /*
     * TODO: The deletion of an existing message should remove an existing message from the database. If the message 
     *  existed, the response body should contain the now-deleted message. The response status should be 200, which 
     *  is the default.
     * If the message did not exist, the response status should be 200, but the response body should be empty. This 
     *  is because the DELETE verb is intended to be idempotent, ie, multiple calls to the DELETE endpoint should 
     *  respond with the same type of response.
     */
    public Message deleteMessageById(int id) {
        return null;
    }

    /*
     * TODO: The update of a message should be successful if and only if the message id already exists and the new 
     *  message_text is not blank and is not over 255 characters. 
     * If the update is successful, the response body should contain the full updated message (including 
     *  message_id, posted_by, message_text, and time_posted_epoch), and the response status should be 200, which 
     *  is the default. The message existing on the database should have the updated message_text.
     * If the update of the message is not successful for any reason, the response status should be 400. (Client 
     *  error)
     */
    public Message updateMessageById(int id) {
        return null;
    }

    /*
     * TODO: The response body should contain a JSON representation of a list containing all messages posted by 
     *  a particular user, which is retrieved from the database. It is expected for the list to simply be empty 
     *  if there are no messages. The response status should always be 200, which is the default
    */
    public List<Message> getAllMessagesFromUser(int accountId) {
        return null;
    }
}