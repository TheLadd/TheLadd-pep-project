package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);

        // Account routes
        app.post("/register", this::accountRegisterHandler);
        app.post("login", this::accountLoginHandler);

        // Message routes
        app.post("messages", this::createNewMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("messages/{message_id}", this::updateMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /*
     * The registration will be successful if and only if the username is not blank, the password is at 
     *  least 4 characters long, and an Account with that username does not already exist. If all these conditions 
     *  are met, the response body should contain a JSON of the Account, including its account_id. The response 
     *  status should be 200 OK, which is the default. The new account should be persisted to the database. 
     *  If the registration is not successful, the response status should be 400. (Client error)
     */
    public void accountRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(ctx.body(), Account.class);
        Account accAdded = accountService.register(acc);

        if (accAdded != null) {
            ctx.json(om.writeValueAsString(accAdded));
            ctx.status(200);
        }
        else {
            ctx.status(400);
        }
    }

    /*
     * The login will be successful if and only if the username and password provided in the request body 
     *  JSON match a real account existing on the database. If successful, the response body should contain a 
     *  JSON of the account in the response body, including its account_id. The response status should be 200 OK, 
     *  which is the default.
     *  If the login is not successful, the response status should be 401. (Unauthorized)
    */
    public void accountLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(ctx.body(), Account.class);
        Account accReturned = accountService.login(acc);

        if (accReturned != null) {
            ctx.json(accReturned);
            ctx.status(200);
        }
        else {
            ctx.status(401);
        }
    }

    /*
     * The creation of the message will be successful if and only if the message_text is not blank, is under 
     *  255 characters, and posted_by refers to a real, existing user. If successful, the response body should 
     *  contain a JSON of the message, including its message_id. The response status should be 200, which is the 
     *  default. The new message should be persisted to the database. If the creation of the message is not 
     * successful, the response status should be 400. (Client error)
     */
    public void createNewMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message msg = om.readValue(ctx.body(), Message.class);
        Message msgCreated = messageService.createNewMessage(msg);

        if (msgCreated != null) {
            ctx.json(msgCreated);
            ctx.status(200);
        }
        else {
            ctx.status(400);
        }
    }

    /*
     * The response body should contain a JSON representation of a list containing all messages retrieved from 
     *  the database. It is expected for the list to simply be empty if there are no messages. The response status 
     *  should always be 200, which is the default.
     */
    public void getAllMessagesHandler(Context ctx) {
        List<Message> msgs = messageService.getAllMessages();
        ctx.json(msgs); // getAllMessages() should, at the very least, return an empty list
    }

    /*
    * The response body should contain a JSON representation of the message identified by the message_id. 
    *  It is expected for the response body to simply be empty if there is no such message. The response status 
    *  should always be 200, which is the default.
    */
   public void getMessageByIdHandler(Context ctx) {
        int id = Integer.valueOf(ctx.pathParam("message_id"));
        Message msg = messageService.getMessageById(id);
        if (msg != null) {
            ctx.json(msg);
        }
   }

   /*
     * The deletion of an existing message should remove an existing message from the database. If the message 
     *  existed, the response body should contain the now-deleted message. The response status should be 200, which 
     *  is the default.
     * If the message did not exist, the response status should be 200, but the response body should be empty. This 
     *  is because the DELETE verb is intended to be idempotent, ie, multiple calls to the DELETE endpoint should 
     *  respond with the same type of response.
     */
    public void deleteMessageByIdHandler(Context ctx) {
        int id = Integer.valueOf( ctx.pathParam("message_id") );
        Message msg = messageService.deleteMessageById(id);
        if (msg != null) {
            ctx.json(msg);
        }
    }

    /*
     * The update of a message should be successful if and only if the message id already exists and the new 
     *  message_text is not blank and is not over 255 characters. 
     * If the update is successful, the response body should contain the full updated message (including 
     *  message_id, posted_by, message_text, and time_posted_epoch), and the response status should be 200, which 
     *  is the default. The message existing on the database should have the updated message_text.
     * If the update of the message is not successful for any reason, the response status should be 400. (Client 
     *  error)
     */
    public void updateMessageByIdHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.valueOf( ctx.pathParam("message_id") );
        ObjectMapper om = new ObjectMapper();
        String msgBody = om.readTree( ctx.body() ).get("message_text").asText();
        Message msg = messageService.updateMessageById(id, msgBody);
        if (msg != null) {
            ctx.json(msg);
        } else {
            ctx.status(400);
        }
    }

    /*
     * The response body should contain a JSON representation of a list containing all messages posted by 
     *  a particular user, which is retrieved from the database. It is expected for the list to simply be empty 
     *  if there are no messages. The response status should always be 200, which is the default
    */
    public void getAllMessagesFromUserHandler(Context ctx) {
        int account_id = Integer.valueOf( ctx.pathParam("account_id") );
        List<Message> msgsFromUser = messageService.getAllMessagesFromUser(account_id);
        ctx.json(msgsFromUser);
    }
}