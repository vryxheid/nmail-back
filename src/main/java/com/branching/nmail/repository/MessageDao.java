package com.branching.nmail.repository;

import com.branching.nmail.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<Message, Integer> {
    public List<Message> findMessageByRecipientId(int id);

    public List<Message> findMessageBySenderId(int id);

    public Message findById(int id);

    //    public List<Message> getNewMessages(User user);

    @Query("SELECT m FROM Message m WHERE (lower(m.subject) LIKE lower(concat('%', :search, '%')) " +
            "or lower(m.body) LIKE lower(concat('%', :search, '%'))) " +
            "and (m.senderId = :userId or m.recipientId = :userId)")
    public List<Message> searchInSubjectOrBodyCaseInsensitive(@Param("userId") int userId, @Param("search") String search);

    @Query("SELECT m FROM Message m WHERE (m.subject LIKE concat('%', :search, '%') " +
            "or m.body LIKE concat('%', :search, '%')) " +
            "and (m.senderId = :userId or m.recipientId = :userId)")
    public List<Message> searchInSubjectOrBodyCaseSensitive(@Param("userId") int userId, @Param("search") String search);
}
