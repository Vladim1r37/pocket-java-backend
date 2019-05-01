package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserMessage;

import java.util.List;

@Repository
public interface UserMessageRepository extends MongoRepository<UserMessage, ObjectId> {

    UserMessage findFirstBySenderAndRecipientAndText(User sender, User recipient, String text);

    List<UserMessage> findBySender(User sender);

    List<UserMessage> findByRecipient(User recipient);

    List<UserMessage> findBySenderOrRecipient(User sender, User recipient);

    Page<UserMessage> findBySenderOrRecipient(User sender, User recipient, Pageable pageable);

//    @Query(value = "{ sender:{'$ref' : 'users', '$id' : ?1 } }")
//    @Query("{$or: [{recipient:{'$ref' : 'users', '$id': ?0 }}," +
//            "{recipient:{'$ref' : 'users', '$id': ?1 }}]}")
    //{'sender' : {$ne: {'$ref' : 'users', '$id': ?1 }} }},
//    @Query("{ $and:[{$or: [{sender:{'$ref' : 'users', '$id': ?0 }}," +
//            "{sender:{'$ref' : 'users', '$id': ?1 }}]}, " +
//            "{$or: [{recipient:{'$ref' : 'users', '$id': ?0 }}," +
//            "{recipient:{'$ref' : 'users', '$id': ?1 }}]}] }")
    @Query("{ $or:[ " +
            "{ $and:[ {sender:{'$ref' : 'users', '$id': ?0 }}," +
            "{recipient:{'$ref' : 'users', '$id': ?1 }} ] }, " +
            "{ $and:[ {sender:{'$ref' : 'users', '$id': ?1 }}," +
            "{recipient:{'$ref' : 'users', '$id': ?0 }} ] } " +
            "] }")
    Page<UserMessage> getCorrespondence(ObjectId firstUser, ObjectId secondUser, Pageable pageable);

    List<UserMessage> findBySenderAndReadFalse(User recipient);

    List<UserMessage> findByRecipientAndReadFalse(User recipient);

}
