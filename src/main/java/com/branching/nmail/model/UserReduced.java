package com.branching.nmail.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserReduced {
    private int id;

    private String name;

    private String email;

    private String phone;

    private Date lastLogIn;

    private boolean isSuperAdmin;

    public UserReduced(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.lastLogIn = user.getLastLogIn();
        this.isSuperAdmin = user.isSuperAdmin();
    }

}