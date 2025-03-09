package com.branching.nmail.controller;

import com.branching.nmail.model.Contact;
import com.branching.nmail.service.ContactService;
import com.branching.nmail.service.JWTService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContactController {
    @Autowired
    ContactService contactService;

    @Autowired
    JWTService jwtService;

    @GetMapping(value = "/contacts")
    @Operation(summary = "Get a user's contacts list")
    public ResponseEntity<List<Contact>> getContactsByRecipientId(@RequestHeader(value = "Authorization") String token) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);

            return new ResponseEntity<>(contactService.getContactListByUserId(userId), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/contact/{contactId}")
    @Operation(summary = "Get contact details of the contact with the specified id.")
    public ResponseEntity<Contact> getContactById(@RequestHeader(value = "Authorization") String token, @PathVariable int contactId) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);

            Contact foundContact = contactService.getContactById(contactId);
            if (foundContact.getOwnerId() != userId) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(401));
            }
            return new ResponseEntity<>(contactService.getContactById(contactId), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/contact")
    @Operation(summary = "Save a contact")
    public ResponseEntity<Void> saveContact(@RequestHeader(value = "Authorization") String token, Contact contact) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);

            if (contact.getOwnerId() != userId) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(401));
            }
            contactService.saveContact(contact);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @DeleteMapping(value = "/delete-contact/{contactId}")
    @Operation(summary = "Delete a contact by ID", description = "Deletes a contact by their unique identifier")
    public ResponseEntity<Void> deleteContact(@RequestHeader(value = "Authorization") String token, @PathVariable int contactId) {
        try {
            String reducedToken = token.substring(7);
            int userId = jwtService.extractUserId(reducedToken);
            Contact foundContact = contactService.getContactById(contactId);
            if (foundContact.getOwnerId() != userId) {
                return new ResponseEntity<>(HttpStatusCode.valueOf(401));
            }
            contactService.deleteContactById(contactId);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }

    }
}
