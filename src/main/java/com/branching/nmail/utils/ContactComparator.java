package com.branching.nmail.utils;

import com.branching.nmail.model.Contact;

import java.util.Comparator;

public class ContactComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact c1, Contact c2) {
        if (c1.isFavourite()) {
            if (c2.isFavourite()) {
                return c1.getName().compareTo(c2.getName());
            } else {
                return -1;
            }
        } else if (c2.isFavourite()) {
            return 1;
        } else {
            return c1.getName().compareTo(c2.getName());
        }
    }
}
