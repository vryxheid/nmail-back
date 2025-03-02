package com.branching.nmail.utils;

import com.branching.nmail.model.Message;

import java.util.Comparator;

public class MessageComparatorByDate implements Comparator<Message> {
    @Override
    // Override the compare method to define custom
    // comparison logic
    public int compare(Message m1, Message m2) {
        if (!m1.isRead()) {
            if (!m2.isRead()) {
                return m1.getDate().compareTo(m2.getDate());
            }
            return -1;
        } else {
            if (!m2.isRead()) {
                return 1;
            }
            return m1.getDate().compareTo(m2.getDate());
        }
    }
}
