package com.branching.nmail.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "contacts", schema = "nmail_schema")
public class Contact {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "favourite")
    private boolean favourite;

    @Column(name = "owner_id")
    private int ownerId; // Email of the owner

    public Contact() {
    }

    ;

    public Contact(String phone, int id, String name, String email, boolean favourite, int ownerId) {
        this.phone = phone;
        this.id = id;
        this.name = name;
        this.email = email;
        this.favourite = favourite;
        this.ownerId = ownerId;
    }
}
