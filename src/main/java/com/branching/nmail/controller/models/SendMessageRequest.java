package com.branching.nmail.controller.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageRequest {

    private String subject;

    private String body;

    private int senderId;

    private String recipientEmail;


}
