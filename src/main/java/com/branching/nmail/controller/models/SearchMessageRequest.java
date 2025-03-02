package com.branching.nmail.controller.models;

import lombok.Getter;

@Getter
public class SearchMessageRequest {
    private int userId;
    private String searchText;
    private boolean caseSensitive;
}
