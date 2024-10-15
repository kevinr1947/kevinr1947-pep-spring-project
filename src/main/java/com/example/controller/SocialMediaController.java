package com.example.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.*;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    /**
     * Parameterized Constructor - dependency injection performed automatically via constructor injeciton.
     * 
     * @param accountService - AccountService object for managing accounts in the database.
     * @param messageService - MessageService object for managing messages in the database.
     */
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * HTTP POST '/register' - Create a new account.
     * 
     * @param account - an Account object containing data for the account to be created.
     * @return a newly created Account persisted in the database.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    /**
     * HTTP POST '/login' - Validate login attempt.
     * 
     * @param account - an Account object containing a username and password to be checked.
     * @return the authenticated Account object if the login is successful.
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Account loginAccount(@RequestBody Account account) {
        return accountService.loginAccount(account);
    }

    /**
     * HTTP POST '/messages' - Create a new message.
     * 
     * @param message - a Message object containing data for the message to be created.
     * @return a newly created Message persisted in the database.
     */
    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public Message createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    /**
     * HTTP GET '/messages' - Retrieve all messages in the database.
     * 
     * @return a list of all Message objects. 
     */
    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    /**
     * HTTP GET '/messages/{message_id}' - Retrieve a message by its message_id
     * 
     * @param message_id - the id of the message to be retrieved.
     * @return a Message object with the given message_id, null if the message is not found.
     */
    @GetMapping("/messages/{message_id}")
    @ResponseStatus(HttpStatus.OK)
    public Message getMessageById(@PathVariable int message_id) {
        return messageService.getMessageById(message_id);
    }

    /**
     * HTTP DELETE '/messages/{message_id}' - Delete a message by its message_id.
     * 
     * @param message_id - the id of the message to be deleted.
     * @return 1 if the deletion was successful, null otherwise.
     */
    @DeleteMapping("/messages/{message_id}")   
    @ResponseStatus(HttpStatus.OK)
    public Integer deleteMessageById(@PathVariable int message_id) {
        return messageService.deleteMessageById(message_id);
    }

    /**
     * HTTP PATCH '/messages/{message_id}' - Update a message by its message_id.
     * 
     * @param message_id - the id of the message to be updated.
     * @param message - a Message object containing the new data to update the message with.
     * @return 1 if the message was successfully updated. 
     */
    @PatchMapping("/messages/{message_id}")
    @ResponseStatus(HttpStatus.OK)
    public Integer updateMessageById(@PathVariable int message_id, @RequestBody Message message) {
        return messageService.updateMessageById(message_id, message);
    }

    /**
     * HTTP GET '/account/{account_id}/messages' - Retrieve all messages from a specific account.
     * 
     * @param account_id - the id of the account to retrieve messages from.
     * @return a list of Message objects containing all messages posted by account_id.
     */
    @GetMapping("/accounts/{account_id}/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getMessagesByAccountId(@PathVariable int account_id) {
        return messageService.getMessagesByAccountId(account_id);
    }


    /***************************Exception Handlers********************************************/

    /**
     * HTTP CONFLICT (409) - Duplicate usernames detected.
     * 
     * @param ex - DuplicateUsernameException thrown when username already exists.
     * @return the error message with information about the exception.
     */
    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUsername(DuplicateUsernameException ex) {
        return ex.getMessage();
    }

    /**
     * HTTP BAD_REQUEST (400) - User input is invalid.
     * 
     * @param ex - InvalidInputException thrown when user input does not satisfy constraints.
     * @return the error message with information about the exception.
     */
    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidRegistration(InvalidInputException ex) {
        return ex.getMessage();
    }

    /**
     * HTTP UNAUTHORIZED (401) - User login attempt unsuccessful.
     * 
     * @param ex - InvalidLoginException thrown when user login attempt does not match existing account.
     * @return the error message with information about the exception.
     */
    @ExceptionHandler(InvalidLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleInvalidLogin(InvalidLoginException ex) {
        return ex.getMessage();
    }
}
