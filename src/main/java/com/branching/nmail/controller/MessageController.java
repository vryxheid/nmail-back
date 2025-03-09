package com.branching.nmail.controller;

import com.branching.nmail.controller.models.SearchMessageRequest;
import com.branching.nmail.model.Message;
import com.branching.nmail.service.JWTService;
import com.branching.nmail.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    JWTService jwtService;

    @GetMapping(value = "/inbox")
    @Operation(summary = "Get list of messages received by the authenticated user")
    public ResponseEntity<List<Message>> getInboxMessages(@RequestHeader(value = "Authorization") String token) {
        try {
            String reducedToken = token.substring(7);
            int id = jwtService.extractUserId(reducedToken);

            return new ResponseEntity<>(messageService.getMessagesByRecipientId(id), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/sent")
    @Operation(summary = "Get list of messages sent by a user")
    public ResponseEntity<List<Message>> getMessagesBySenderId(@RequestHeader(value = "Authorization") String token) {
        try {
            String reducedToken = token.substring(7);
            int id = jwtService.extractUserId(reducedToken);

            return new ResponseEntity<>(messageService.getMessagesBySenderId(id), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/trash")
    @Operation(summary = "Get list of messages that a user sent to the trash")
    public ResponseEntity<List<Message>> getTrashByRecipientId(@RequestHeader(value = "Authorization") String token) {
        try {
            String reducedToken = token.substring(7);
            int id = jwtService.extractUserId(reducedToken);

            return new ResponseEntity<>(messageService.getTrashMessagesByRecipientId(id), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/message/{id}")
    @Operation(summary = "Get message by id")
    public ResponseEntity<Message> getMessageById(@RequestHeader(value = "Authorization") String token, @PathVariable int id) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);
            Message foundMessage = messageService.getMessageById(id);
            if (foundMessage.getSenderId() != userId && foundMessage.getRecipientId() != userId) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(401));
            }
            return new ResponseEntity<>(foundMessage, HttpStatusCode.valueOf(200));
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
    public ResponseEntity<Void> sendMessage(@RequestHeader(value = "Authorization") String token, Message message) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);
            if (message.getSenderId() != userId) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(401));
            }
            messageService.saveMessage(message);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/search-message")
    @Operation(summary = "Search in list of receives and sent messages")
    public ResponseEntity<List<Message>> searchMessages(@RequestHeader(value = "Authorization") String token, @RequestBody SearchMessageRequest searchMessageRequest) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);

            return new ResponseEntity<>(messageService.search(userId, searchMessageRequest.getSearchText(),
                    searchMessageRequest.isCaseSensitive()), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

}
