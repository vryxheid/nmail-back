package com.branching.nmail.repository;


import com.branching.nmail.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactDao extends JpaRepository<Contact, Integer> {
    /**
     * Finds the contacts that a certain user (owner) has saved.
     *
     * @param id The id of the contact owner.
     * @return A list of books matching the criteria.
     */
    List<Contact> findByOwnerId(int id);

    Contact findById(int id);
}
