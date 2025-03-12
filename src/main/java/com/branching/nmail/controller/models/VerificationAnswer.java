package com.branching.nmail.controller.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class VerificationAnswer {
    private String jwtToken;
    private Date expiresAt;

    public VerificationAnswer(String JWTToken, Date expiresAt) {
        this.jwtToken = JWTToken;
        this.expiresAt = expiresAt;
    }
}
