package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.util.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPub {
    private String id;

    @NotEmpty
    @ValidEmail
    private String email;

    private UserProfilePub profile;

    public UserPub(@NotNull User user) {
        this.id = user.getId().toString();
        this.email = user.getEmail();
        this.profile = new UserProfilePub(
                user.getId().toString(),
                user.getProfile().getUsername(),
                user.getProfile().getFullName());
    }

    @Override
    public String toString() {
        return "User{" +
                "'id':'" + id + "'" +
                ", 'email':'" + email + "'" +
                ", " + profile +
                '}';
    }

}