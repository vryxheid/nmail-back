package com.branching.nmail.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Getter
@Setter
public class MessageWithEmails {
    @Autowired

    @Id
    private int id;

    private String subject;

    private String body;

    private String senderEmail;

    private String recipientEmail;

    private Date date;

    private boolean read;

    private boolean isTrash;

    public MessageWithEmails() {
    }

    public MessageWithEmails(int id, String subject, String body, String senderEmail, String recipientEmail, Date date, boolean read, boolean isTrash) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.senderEmail = senderEmail;
        this.recipientEmail = recipientEmail;
        this.date = date;
        this.read = read;
        this.isTrash = isTrash;
    }
}
