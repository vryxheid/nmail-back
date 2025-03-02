package com.branching.nmail.controller;

import com.branching.nmail.model.Contact;
import com.branching.nmail.service.ContactService;
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

    @GetMapping(value = "/contacts/{id}")
    @Operation(summary = "Get a user's contacts list")
    public ResponseEntity<List<Contact>> getContactsByRecipientId(@PathVariable int id) {
        try {
            return new ResponseEntity<>(contactService.getContactListByUserId(id), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @GetMapping(value = "/contact/{id}")
    @Operation(summary = "Get contact details of the contact with the specified id.")
    public ResponseEntity<Contact> getContactById(@PathVariable int id) {
        try {
            return new ResponseEntity<>(contactService.getContactById(id), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping(value = "/contact")
    @Operation(summary = "Save a contact")
    public ResponseEntity<Void> saveContact(Contact contact) {
        try {
            contactService.saveContact(contact);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(500));
        }
    }

    @DeleteMapping(value = "/delete-contact/{id}")
    @Operation(summary = "Delete a contact by ID", description = "Deletes a contact by their unique identifier")
    public void deleteUser(@PathVariable int id) {
        contactService.deleteContactById(id);
    }
}
