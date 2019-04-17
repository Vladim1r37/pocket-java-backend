package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserBlacklist;

import java.util.List;

@Repository
public interface UserBlacklistRepository extends MongoRepository<UserBlacklist, ObjectId> {

    List<UserBlacklist> findByUser(User user);

    Page<UserBlacklist> findByUser(User user, Pageable pageable);

    UserBlacklist findFirstByUserAndBanned(User user, User banned);

    UserBlacklist findFirstByUserAndBannedId(User user, ObjectId id);

}
