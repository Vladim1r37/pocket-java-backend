package ru.geekbrains.pocket.backend.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.db.Group;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserChat;
import ru.geekbrains.pocket.backend.repository.UserChatRepository;
import ru.geekbrains.pocket.backend.service.UserChatService;

import java.util.List;

@Service
public class UserChatServiceImpl implements UserChatService {
    @Autowired
    private UserChatRepository repository;

    @Override
    public UserChat createUserChat(UserChat userChat) {
        return repository.insert(userChat);
    }

    @Override
    public UserChat createUserChat(User user, User direct) {
        return repository.insert(new UserChat(user, direct));
    }

    @Override
    public UserChat createUserChat(User user, User direct, User sender) {
        return repository.insert(new UserChat(user, direct, sender));
    }

    @Override
    public UserChat getUserChat(ObjectId id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public UserChat getUserChat(User user, Group group) {
        return repository.findFirstByUserAndGroup(user, group);
    }

    @Override
    public UserChat getUserChat(User user, User direct) {
        return repository.findFirstByUserAndDirect(user, direct);
    }

    @Override
    public List<UserChat> getUserChats(Group group) {
        return repository.findByGroup(group);
    }

    @Override
    public List<UserChat> getUserChats(Group group, Integer offset) {
        Pageable pageable = PageRequest.of(offset, 10,
                Sort.by(Sort.Direction.ASC,"username", "group"));
        Page<UserChat> page = repository.findByGroup(group, pageable);
        return page.getContent();
    }

    @Override
    public List<UserChat> getUserChats(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<UserChat> getUserChats(User user, Integer offset) {
        Pageable pageable = PageRequest.of(offset, 10,
                Sort.by(Sort.Direction.ASC,"username", "direct"));
        Page<UserChat> page = repository.findByUser(user, pageable);
        return page.getContent();
    }

    @Override
    public void deleteAllUserChats() {
        repository.deleteAll();
    }

    @Override
    public void deleteUserChat(ObjectId id) {
        repository.deleteById(id);
    }
}
