package com.branching.nmail.service;

import com.branching.nmail.model.Contact;
import com.branching.nmail.repository.ContactDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    ContactDao contactDao;

    public Contact saveContact(Contact contact) {
        return contactDao.save(contact);
    }

    public void deleteContactById(int id) {
        contactDao.deleteById(id);
    }

    public List<Contact> getContactListByUserId(int userId) {
        return contactDao.findByOwnerId(userId);
    }

    public Contact getContactById(int id) {
        return contactDao.findById(id);
    }
}
