package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.geekbrains.pocket.backend.domain.db.Group;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserChat;

import java.util.List;

public interface UserChatRepository extends MongoRepository<UserChat, ObjectId> {

    UserChat findFirstByUserAndGroup(User user, Group group);

    UserChat findFirstByUserAndDirect(User user, User direct);

    List<UserChat> findByGroup(Group group);

    Page<UserChat> findByGroup(Group group, Pageable pageable);

    List<UserChat> findByUser(User user);

    Page<UserChat> findByUser(User user, Pageable pageable);

    List<UserChat> findByUserId(ObjectId id);

    @Query("{'user':?0}")
    List<UserChat> findCustomByUser(User user);

    void deleteByUser(User user);

}
