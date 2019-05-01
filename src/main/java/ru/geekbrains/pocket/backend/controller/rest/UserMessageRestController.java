package ru.geekbrains.pocket.backend.controller.rest;

import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserMessage;
import ru.geekbrains.pocket.backend.domain.pub.MessageCollection;
import ru.geekbrains.pocket.backend.domain.pub.MessagePub;
import ru.geekbrains.pocket.backend.service.UserMessageService;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserMessageRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private HttpRequestComponent httpRequestComponent;

    @GetMapping("/{idUser}/messages") //Получить историю переписки
    //пользователь от которого пришел запрос может получить историю переписки только в которой он учавствовал
    public ResponseEntity<?> getMessages(@PathVariable String idUser,
                                         @RequestParam("offset") Integer offset,
                                         HttpServletRequest request) {
        User currentUser = httpRequestComponent.getUserFromToken(request);
        if (currentUser != null) {
            User secondUser = userService.getUserById(new ObjectId(idUser));
            List<UserMessage> messages;
            if (secondUser != null) {
                messages = userMessageService.getAllMessagesUser(currentUser, secondUser, offset);

                MessageCollection messageCollection = new MessageCollection();
                messageCollection.setUserMessages(offset, messages);
                return new ResponseEntity<>(messageCollection, HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{idUser}/messages/{idMessage}") //Получить конкретное сообщение
    public ResponseEntity<?> findMessage(@PathVariable String idUser,
                                         @PathVariable String idMessage,
                                         HttpServletRequest request) {
        User currentUser = httpRequestComponent.getUserFromToken(request);
        if (currentUser != null) {
            String idCurrentUser = currentUser.getId().toString();
            UserMessage message = userMessageService.getMessage(new ObjectId(idMessage));
            if (message != null && message.getSender() != null && idUser != null && !idUser.equals("")) {
                if (idUser.equals(message.getSender().getId().toString())
                        || idUser.equals(message.getRecipient().getId().toString()))
                    //TODO проверка что текущий юзер получает сообщение в котором он отправитель или получатель
                    if (idCurrentUser.equals(message.getSender().getId().toString())
                            || idCurrentUser.equals(message.getRecipient().getId().toString()))
                        return new ResponseEntity<>(new MessagePub(message), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
