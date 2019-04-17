package ru.geekbrains.pocket.backend.domain.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.geekbrains.pocket.backend.util.validation.ValidEmail;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
@TypeAlias("users")
public class User {
    @Id
    private ObjectId id;

    @NotNull
    @NotEmpty
    @ValidEmail
    @Size(min = 6, max = 32)
    @Indexed(unique = true)
    private String email;

    @NotEmpty
    @JsonIgnore
    @Size(min = 8, max = 32)
    private String password;

    @NotNull
    @Valid
    private UserProfile profile;

    //@DBRef
//    @Transient
//    @NotNull
//    //@Valid
//    @JsonIgnore
//    private Collection<Role> roles = Role.getRoleUser();

    @Field("created_at")
    private Date createdAt = new Date();

    //private boolean enabled = false;

    //private boolean isUsing2FA = false;

    //private String secret = ""; //Base32.random();

    public User(String email, String password, UserProfile userProfile) {
        if (userProfile == null) userProfile = new UserProfile(email);
        if (userProfile.getUsername() == null || userProfile.getUsername().equals(""))
            userProfile.setUsername(email);
        this.email = email;
        this.password = password;
        this.profile = userProfile;
    }

    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.profile = new UserProfile(username);
    }

//    public User(String email, String password, String username, Collection<Role> roles) {
//        this.email = email;
//        this.password = password;
//        this.profile = new UserProfile(username);
//        this.roles = roles;
//    }

    @Override
    public String toString() {
        return "User{" +
                "'id':'" + id + "'" +
                ", 'email':'" + email + "'" +
                ", 'username':'" + profile.getUsername() + "'" +
                '}';
    }
}

/* http://qaru.site/questions/224920/regex-for-password-php
regexp = "^\\S*(?=\\S{6,})(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*[\\d])\\S*$"
     ^: привязано к началу строки
     \S*: любой набор символов
     (?=\S{8,}): не менее длины 6
     (?=\S*[a-z]): содержит хотя бы одну строчную букву
     (?=\S*[a-z]): и по крайней мере одно прописное письмо
     (?=\S*[\d]): и по крайней мере одно число
     $: привязано к концу строки
*/
/* http://j4web.ru/java-regex/validatsiya-parolya-s-pomoshhyu-regulyarnogo-vyrazheniya.html
((?=.*d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})
        (                        # Начало группы
        (?=.*d)               # Должен содержать цифру от 0 до 9
        (?=.*[a-z])            # Должен содержать символ латинницы в нижем регистре
        (?=.*[A-Z])            # Должен содержать символ латинницы в верхнем регистре
        (?=.*[@#$%])           # Должен содержать специальный символ из списка "@#$%"
        .                   # Совпадает с предыдущими условиями
        {6,20}              # Длина - от 6 до 20 символов
        )                        # Конец группы*/
