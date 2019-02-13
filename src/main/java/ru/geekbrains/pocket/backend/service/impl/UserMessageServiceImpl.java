package ru.geekbrains.pocket.backend.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserMessage;
import ru.geekbrains.pocket.backend.exception.UserMessageNotFoundException;
import ru.geekbrains.pocket.backend.repository.UserMessageRepository;
import ru.geekbrains.pocket.backend.service.UserMessageService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    private UserMessageRepository userMessageRepository;

    @Override
    public UserMessage createMessage(UserMessage message) {
        message.setSent_at(new Date());
        message.setRead(false);
        return userMessageRepository.insert(message);
    }

    public UserMessage createMessage(User sender, User recipient, String text) {
        UserMessage messageNew = new UserMessage(sender, recipient, text);
        messageNew.setSent_at(new Date());
        return userMessageRepository.insert(messageNew);
    }

    @Override
    public void deleteMessage(UserMessage message) {
        userMessageRepository.delete(message);
    }

    @Override
    public void deleteAllMessages() {
        userMessageRepository.deleteAll();
    }

    @Override
    public UserMessage getMessage(ObjectId id) {
        UserMessage userMessage = userMessageRepository.findById(id).orElseThrow(
                () -> new UserMessageNotFoundException("User message with id = " + id + " not found"));
        return userMessage;
    }

    @Override
    public List<UserMessage> getAllMessagesUser(User user) {
        return userMessageRepository.findBySenderOrRecipient(user, user);
    }

    @Override
    public List<UserMessage> getMessagesBySender(User sender) {
        return userMessageRepository.findBySender(sender);
    }

    @Override
    public List<UserMessage> getMessagesByRecipient(User recipient) {
        return userMessageRepository.findByRecipient(recipient);
    }

    @Override
    public List<UserMessage> getUnreadMessagesFromUser(User sender) {
        return userMessageRepository.findBySenderAndReadFalse(sender);
    }

    @Override
    public List<UserMessage> getUnreadMessagesToUser(User recipient) {
        return userMessageRepository.findByRecipientAndReadFalse(recipient);
    }

    public UserMessage sendMessageFromTo(User sender, User recipient, String message) {
        UserMessage messageNew = new UserMessage(sender, recipient, message);
        messageNew.setSent_at(new Date());
        messageNew.setRead(false);
        return userMessageRepository.save(messageNew);
    }

    @Override
    public UserMessage updateMessage(UserMessage message) {
        return null;
    }

}
