package com.branching.nmail.controller;

import com.branching.nmail.controller.models.SearchMessageRequest;
import com.branching.nmail.controller.models.SendMessageRequest;
import com.branching.nmail.model.Message;
import com.branching.nmail.model.MessageWithEmails;
import com.branching.nmail.service.JWTService;
import com.branching.nmail.service.MessageService;
import com.branching.nmail.utils.MessageMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    JWTService jwtService;

    @Autowired
    MessageMapper messageMapper;

    @GetMapping(value = "/inbox")
    @Operation(summary = "Get list of messages received by the authenticated user")
    public ResponseEntity<List<MessageWithEmails>> getInboxMessages(@RequestHeader(value = "Authorization") String token) {
        try {
            String reducedToken = token.substring(7);
            int id = jwtService.extractUserId(reducedToken);
            List<Message> messages = messageService.getMessagesByRecipientId(id);
            return new ResponseEntity<>(messageMapper.mapMessagesToMessagesWithEmails(messages), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/sent")
    @Operation(summary = "Get list of messages sent by a user")
    public ResponseEntity<List<MessageWithEmails>> getMessagesBySenderId(@RequestHeader(value = "Authorization") String token) {
        try {
            String reducedToken = token.substring(7);
            int id = jwtService.extractUserId(reducedToken);
            List<Message> messages = messageService.getMessagesBySenderId(id);
            return new ResponseEntity<>(messageMapper.mapMessagesToMessagesWithEmails(messages), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/trash")
    @Operation(summary = "Get list of messages that a user sent to the trash")
    public ResponseEntity<List<MessageWithEmails>> getTrashByRecipientId(@RequestHeader(value = "Authorization") String token) {
        try {
            String reducedToken = token.substring(7);
            int id = jwtService.extractUserId(reducedToken);
            List<Message> messages = messageService.getTrashMessagesByRecipientId(id);
            return new ResponseEntity<>(messageMapper.mapMessagesToMessagesWithEmails(messages), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/message/{id}")
    @Operation(summary = "Get message by id")
    public ResponseEntity<MessageWithEmails> getMessageById(@RequestHeader(value = "Authorization") String token, @PathVariable int id) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);
            Message foundMessage = messageService.getMessageById(id);
            if (foundMessage.getSenderId() != userId && foundMessage.getRecipientId() != userId) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(401));
            }
            return new ResponseEntity<>(messageMapper.mapMessageToMessageWithEmails(foundMessage), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/delete-message/{id}")
    @Operation(summary = "Delete message by id")
    public ResponseEntity<Void> deleteMessageById(@PathVariable int id) {
        try {
            messageService.deleteMessageById(id);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/send-message")
    @Operation(summary = "Send a message")
    public ResponseEntity<Void> sendMessage(@RequestHeader(value = "Authorization") String token, @RequestBody SendMessageRequest message) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);
            if (message.getSenderId() != userId) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(401));
            }
            MessageWithEmails messageWithEmails = new MessageWithEmails(
                    0,
                    message.getSubject(),
                    message.getBody(),
                    jwtService.extractUsername(reducedToken),
                    message.getRecipientEmail(),
                    new Date(),
                    false,
                    false);
            messageService.saveMessage(messageMapper.mapMessageWithEmailsToMessage(messageWithEmails));
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/search-message")
    @Operation(summary = "Search in list of receives and sent messages")
    public ResponseEntity<List<MessageWithEmails>> searchMessages(@RequestHeader(value = "Authorization") String token, @RequestBody SearchMessageRequest searchMessageRequest) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);
            List<Message> messages = messageService.search(userId, searchMessageRequest.getSearchText(),
                    searchMessageRequest.isCaseSensitive());
            return new ResponseEntity<>(messageMapper.mapMessagesToMessagesWithEmails(messages), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

}
