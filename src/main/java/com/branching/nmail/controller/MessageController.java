package com.branching.nmail.controller;

import com.branching.nmail.controller.models.SearchMessageRequest;
import com.branching.nmail.model.Message;
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

    @GetMapping(value = "/inbox/{id}")
    @Operation(summary = "Get list of messages received by a user")
    public ResponseEntity<List<Message>> getMessagesByRecipientId(@PathVariable int id) {
        try {
            return new ResponseEntity<>(messageService.getMessagesByRecipientId(id), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/sent/{id}")
    @Operation(summary = "Get list of messages sent by a user")
    public ResponseEntity<List<Message>> getMessagesBySenderId(@PathVariable int id) {
        try {
            return new ResponseEntity<>(messageService.getMessagesBySenderId(id), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/trash/{id}")
    @Operation(summary = "Get list of messages that a user sent to the trash")
    public ResponseEntity<List<Message>> getTrashByRecipientId(@PathVariable int id) {
        try {
            return new ResponseEntity<>(messageService.getTrashMessagesByRecipientId(id), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/message/{id}")
    @Operation(summary = "Get message by id")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(messageService.getMessageById(id), HttpStatusCode.valueOf(200));
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
    public ResponseEntity<Void> sendMessage(Message message) {
        try {
            messageService.saveMessage(message);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/search-message")
    @Operation(summary = "Search in list of receives and sent messages")
    public ResponseEntity<List<Message>> searchMessages(@RequestBody SearchMessageRequest searchMessageRequest) {
        try {
            return new ResponseEntity<>(messageService.search(searchMessageRequest.getUserId(), searchMessageRequest.getSearchText(),
                    searchMessageRequest.isCaseSensitive()), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

}
