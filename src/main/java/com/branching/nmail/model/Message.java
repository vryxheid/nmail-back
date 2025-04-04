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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_id_generator")
    @SequenceGenerator(name = "messages_id_generator", sequenceName = "messages_seq", allocationSize = 1)
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

    @Column(name = "is_trash")
    private boolean isTrash;

    public Message() {
    }

    public Message(int id, String subject, String body, int senderId, int recipientId, Date date, boolean read, boolean isTrash) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.date = date;
        this.read = read;
        this.isTrash = isTrash;
    }
}
