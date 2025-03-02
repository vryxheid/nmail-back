package com.branching.nmail.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "messages", schema = "nmail_schema")
public class Message {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    @Column(name = "sender_id")
    private int senderId;

    @Column(name = "recipient_id")
    private int recipientId;

    @Column(name = "date")
    private Date date;


    @Column(name = "read")
    private boolean read;

//    public Message() {
//    }
//
//    public Message(String subject, String body, int senderId, int recipientId, Date date, boolean read) {
//        this.subject = subject;
//        this.body = body;
//        this.senderId = senderId;
//        this.recipientId = recipientId;
//        this.date = date;
//        this.read = read;
//    }


}
