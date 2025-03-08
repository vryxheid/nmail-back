package com.branching.nmail.service;

import com.branching.nmail.model.Message;
import com.branching.nmail.repository.MessageDao;
import com.branching.nmail.utils.ISearchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements ISearchable<Message> {
    @Autowired
    MessageDao messageDao;

    public Message getMessageById(int id) {
        return messageDao.findById(id);
    }

    public Message saveMessage(Message message) {
        return messageDao.save(message);
    }

    public void deleteMessageById(int id) {
        messageDao.deleteById(id);
    }

    public List<Message> getMessagesBySenderId(int senderId) {
        return messageDao.findMessageBySenderIdAndIsTrashFalse(senderId);
    }

    public List<Message> getMessagesByRecipientId(int recipientId) {
        return messageDao.findMessageByRecipientIdAndIsTrashFalse(recipientId);
    }

    public List<Message> getTrashMessagesByRecipientId(int recipientId) {
        return messageDao.findMessageByRecipientIdAndIsTrashTrue(recipientId);
    }

    public List<Message> search(int userId, String searchText, boolean caseSensitive) {
        List<Message> result;
        if (caseSensitive) {
            result = messageDao.searchInSubjectOrBodyCaseSensitive(userId, searchText);
        } else {
            result = messageDao.searchInSubjectOrBodyCaseInsensitive(userId, searchText);
        }
        return result;
    }
}
