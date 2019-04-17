package ru.geekbrains.pocket.backend.service.impl;

import com.mongodb.MongoWriteException;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserProfile;
import ru.geekbrains.pocket.backend.enumeration.TokenStatus;
import ru.geekbrains.pocket.backend.exception.InvalidOldPasswordException;
import ru.geekbrains.pocket.backend.exception.UserAlreadyExistException;
import ru.geekbrains.pocket.backend.exception.UserNotFoundException;
import ru.geekbrains.pocket.backend.repository.UserChatRepository;
import ru.geekbrains.pocket.backend.repository.UserContactRepository;
import ru.geekbrains.pocket.backend.repository.UserRepository;
import ru.geekbrains.pocket.backend.security.token.JwtTokenUtil;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserChatRepository userChatRepository;
    @Autowired
    private UserContactRepository userContactRepository;
//    @Autowired
//    private UserTokenRepository userTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public User changeUserPassword(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public User createUserAccount(String email, String password, String name)
            throws UserAlreadyExistException, DuplicateKeyException, MongoWriteException {
        if (userRepository.findByEmail(email) != null) {
            throw new UserAlreadyExistException("There is an account with that email adress: " + email);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); //получаем хэш пароля
        //user.setUsing2FA(account.isUsing2FA());
        user.setProfile(new UserProfile(name));
        //user.setRoles(Arrays.asList(Role.ROLE_USER));
        return userRepository.insert(user);
    }

    @Override
    public void delete(ObjectId id) throws RuntimeException {
        deleteCascade(getUserById(id));
    }

    @Override
    public void delete(String email) {
        deleteCascade(getUserByEmail(email));
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    private void deleteCascade(User user) {
        if (user != null ) {
            userRepository.deleteById(user.getId());
            //TODO каскадное удаление
//            userTokenRepository.deleteByUser(user);
            userChatRepository.deleteByUser(user);
            userContactRepository.deleteByUser(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(ObjectId id) {
//        //TODO исправить
//        if (userRepository.findById(id).isPresent()) {
//            return userRepository.findById(id).orElseThrow(
//                            () -> new UserNotFoundException("User with id = " + id + " not found"));
//        }
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) throws RuntimeException {
        //User user2 = userRepository.findFirstByUsername(username);
//        User user = Optional.of(userRepository.findByProfileUsername(username)).orElseThrow(
//                () -> new UserNotFoundException("User with username = '" + username + "' not found"));
        return userRepository.findByProfileUsername(username);
    }

//    @Override
//    public List<Role> getRolesByUsername(String username) throws RuntimeException {
//        User user = Optional.of(userRepository.findByProfileUsername(username)).orElseThrow(
//                () -> new UserNotFoundException("User with username = '" + username + "' not found"));
//        return (List<Role>) user.getRoles();
//    }

    @Override
    public User insert(User user) throws RuntimeException {
//        if (user.getRoles() == null)
//            user.setRoles(Arrays.asList(Role.ROLE_USER));
        return userRepository.insert(user);
    }


    @Override
    public User update(User user) throws DuplicateKeyException, MongoWriteException {
        //try {
            return userRepository.save(user);
        //} catch (MongoWriteException ex) {
            //log.error(ex.getMessage());
        //}
        //return null;
    }

    @Override
    public User updateNameAndPassword(@NotNull User user, String name, String oldPassword, String newPassword)
            throws InvalidOldPasswordException, DuplicateKeyException, MongoWriteException {
        if (name != null) user.getProfile().setUsername(name);
        if (oldPassword != null && newPassword != null) {
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new InvalidOldPasswordException("The current password specified is incorrect!");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        return update(user);
    }

    public User validateUser(ObjectId id) throws UsernameNotFoundException {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User with id = " + id + " not found"));
    }

    public User validateUser(String username) throws UsernameNotFoundException {
        return Optional.of(userRepository.findByProfileUsername(username)).orElseThrow(
                () -> new UserNotFoundException("User with username = " + username + " not found"));
    }


    @Override
    public boolean isValidToken(String token, User user) {
        if (user == null) return false;
        if (token != null && !token.equals("")) {
            //проверяем токен
            return jwtTokenUtil.validateToken(token, user.getEmail());
        }
        return false;
    }

    @Override
    public String getEmailFromToken(String token){
        return jwtTokenUtil.getUsernameFromToken(token);
    }

    @Override
    public String getNewToken(User user){
        return jwtTokenUtil.generateToken(user);
    }

//    public boolean isValidToken2(String token, User user) {
//        if (user == null) return false;
//        String newToken = "";
//        if (token != null && !token.equals("")) {
//            //проверяем токен
//            if (jwtTokenUtil.validateToken(token, user.getEmail())) {
//                //if (getTokenStatus(token).equals(TokenStatus.EXPIRED)) {
//                return false;
//                //обновляем токен
//                //newToken = jwtTokenUtil.generateToken(user);
//            }
//            String email = jwtTokenUtil.getUsernameFromToken(token);
//        } else {
//            //токен не найден, создаём новый токен
//            return false;
//            //newToken = jwtTokenUtil.generateToken(user);
//            //Date expiryDate = jwtTokenUtil.getExpirationDateFromToken(newToken);
//        }
//        return true;
//    }
//    private TokenStatus getTokenStatus(String token) {
//        final Calendar cal = Calendar.getInstance();
//        if ((jwtTokenUtil.getExpirationDateFromToken(token).getTime()
//                - cal.getTime()
//                .getTime()) <= 0) {
//            return TokenStatus.EXPIRED;
//        }
//        return TokenStatus.VALID;
//    }

}

