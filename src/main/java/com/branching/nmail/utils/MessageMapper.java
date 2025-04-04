package com.branching.nmail.utils;

import com.branching.nmail.model.Message;
import com.branching.nmail.model.MessageWithEmails;
import com.branching.nmail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageMapper {

    @Autowired
    UserService userService;

    public MessageWithEmails mapMessageToMessageWithEmails(Message message) {

        String senderEmail = this.userService.getUserById(message.getSenderId()).getEmail();
        String recipientEmail = this.userService.getUserById(message.getRecipientId()).getEmail();
        return new MessageWithEmails(
                message.getId(),
                message.getSubject(),
                message.getBody(),
                senderEmail,
                recipientEmail,
                message.getDate(),
                message.isRead(),
                message.isTrash()
        );
    }

    public Message mapMessageWithEmailsToMessage(MessageWithEmails message) {
        int senderId = this.userService.getUserByEmail(message.getSenderEmail()).getId();
        int recipientId = this.userService.getUserByEmail(message.getRecipientEmail()).getId();
        return new Message(
                message.getId(),
                message.getSubject(),
                message.getBody(),
                senderId,
                recipientId,
                message.getDate(),
                message.isRead(),
                message.isTrash()
        );
    }

    public List<MessageWithEmails> mapMessagesToMessagesWithEmails(List<Message> messages) {
        ArrayList<MessageWithEmails> messagesWithEmails = new ArrayList<>();
        for (Message message : messages) {
            messagesWithEmails.add(mapMessageToMessageWithEmails(message));
        }
        return messagesWithEmails;
    }

    public List<Message> mapMessagesWithEmailsToMessages(List<MessageWithEmails> messagesWithEmails) {
        ArrayList<Message> messages = new ArrayList<>();
        for (MessageWithEmails messageWithEmails : messagesWithEmails) {
            messages.add(mapMessageWithEmailsToMessage(messageWithEmails));
        }
        return messages;
    }
}
