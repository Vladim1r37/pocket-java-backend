package ru.geekbrains.pocket.backend.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserContact;
import ru.geekbrains.pocket.backend.repository.UserContactRepository;
import ru.geekbrains.pocket.backend.service.UserContactService;

import java.util.List;

@Service
public class UserContactServiselmpl implements UserContactService {
    @Autowired
    private UserContactRepository repository;

    @Override
    public UserContact createUserContact(UserContact userContact) {
        return repository.insert(userContact);
    }

    @Override
    public UserContact createUserContact(User user, User contact) {
        UserContact userContact = repository.findFirstByUserAndContact(user, contact);
        if (userContact == null) {
            userContact = new UserContact(user, contact);
            return repository.insert(userContact);
        } else
            return userContact;
    }

    @Override
    public UserContact createUserContact(User user, User contact, String byname) {
        UserContact userContact = repository.findFirstByUserAndContact(user, contact);
        if (userContact == null) {
            userContact = new UserContact(user, contact, byname);
            return repository.insert(userContact);
        } else
            return userContact;
    }

    @Override
    public void deleteByUser(User user) {
        repository.deleteByUser(user);
    }

    @Override
    public void delete(UserContact userContact) {
        repository.delete(userContact);
    }

    @Override
    public UserContact getUserContact(User user, User contact) {
        return repository.findFirstByUserAndContact(user, contact);
    }

    @Override
    public UserContact getUserContact(User user, ObjectId idContact) {
        return repository.findFirstByUserAndContact_Id(user,idContact);
//        Optional<User> contact = userRepository.findById(idContact);
//        if (contact.isPresent())
//            return repository.findFirstByUserAndContact(user, contact.get());
//        return null;
    }

    @Override
    public List<UserContact> getUserContacts(User user) {
        return repository.findByUser(user);
    }

    @Override
    public List<UserContact> getUserContacts(User user, Integer offset) {
//        Pageable pageable = PageRequest.of(offset, 10);
//        Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "contact"));
        //PageRequest request = new PageRequest(page,1,Sort.Direction.ASC,"country");
        Pageable pageable = PageRequest.of(offset, 10,
                Sort.by(Sort.Direction.ASC,"username", "byname"));
//                Sort.by("contact").descending().and(Sort.by("username")));
        Page<UserContact> page = repository.findByUser(user, pageable);
//        page.getContent().forEach(System.out::println);
//        pageable = page.nextPageable();
        return page.getContent();
    }

    @Override
    public UserContact updateUserContact(UserContact userContact) {
        return repository.save(userContact);
    }
}
