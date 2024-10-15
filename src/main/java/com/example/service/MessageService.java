package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.InvalidInputException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    /**
     * Constructor - Dependency Injection.
     * 
     * @param messageRepository - JpaRepository for accessing the 'message' table in the database.
     * @param accountRepository - JpaRepository for accessing the 'account' table in the database.
     */
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Add a new message to the database.
     *
     * @param message - Message object representing a new message to be added to the database.
     * @return the newly added message if successful, including the message_id. 
     */
    public Message createMessage(Message message) {
        if (message.getMessageText().length() < 1) 
            throw new InvalidInputException("Message text cannot be blank.");
        if (message.getMessageText().length() > 255)
            throw new InvalidInputException("Message text cannot exceed 255 characters.");
        if (!accountRepository.existsById(message.getPostedBy()))
            throw new InvalidInputException("Message posted_by does not match existing account ID.");

        return messageRepository.save(message);
    }

    /**
     * Retrieve all messages.
     *
     * @return a List containing all messages in the database.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Get a message in the database.
     * 
     * @param message_id the ID of the message to be retrieved.
     * @return message object with values retrieved from message_id, null if message_id not found.
     */
    public Message getMessageById(int message_id) {
        return messageRepository.findById(message_id).orElse(null);
    }

    /**
     * Delete an existing message from the database.
     * 
     * @param message_id the ID of the message to be deleted.
     * @return 1 if the message was successfully deleted, null otherwise.
     */
    public Integer deleteMessageById(int message_id) {
        if (messageRepository.existsById(message_id)) {
            messageRepository.deleteById(message_id);
            return 1;
        }
        return null;
    }

    /**
     * Update an existing message from the database.
     *
     * @param message_id - the ID of the message to be modified.
     * @param message - an object containing all data that should replace the values contained by the existing message_id.
     * @return the newly updated message if the update was successful.
     */
    public Integer updateMessageById(int message_id, Message message) {
        if (!messageRepository.existsById(message_id))
            throw new InvalidInputException("Message id does not exist.");
        if (message.getMessageText().length() < 1) 
            throw new InvalidInputException("Message text cannot be blank.");
        if (message.getMessageText().length() > 255)
            throw new InvalidInputException("Message text cannot exceed 255 characters.");

        messageRepository.save(message);
        return 1;
    }

    /**
     * Retrieve all messages from a user given account ID.
     *
     * @param account_id - the ID of the account to get all messages from.
     * @return a List containing all messages posted by account_id.
     */
    public List<Message> getMessagesByAccountId(int account_id) {
        return messageRepository.findByPostedBy(account_id);
    }

    
}
