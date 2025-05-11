package ru.parog.magauserservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.parog.onlinelearningplatformmodel.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {

    public static final String TABLE_NAME = "users";

    static class ColumnNames {
        public static final String USER_CN_EMAIL = "email";
        public static final String USER_CN_USERNAME = "username";
        public static final String USER_CN_PASSWORD = "password";
        public static final String USER_CN_FIRST_NAME = "first_name";
        public static final String USER_CN_LAST_NAME = "last_name";
        public static final String USER_CN_IS_ACTIVE = "is_active";
        public static final String USER_CN_ROLE = "role";
        public static final String USER_CN_BIO = "bio";
    }

    @Email
    @Column(name = ColumnNames.USER_CN_EMAIL, nullable = false, unique = true)
    private String email;

    @Column(name = ColumnNames.USER_CN_USERNAME, nullable = false, unique = true)
    private String username;

    @Column(name = ColumnNames.USER_CN_PASSWORD, nullable = false)
    private String password;

    @Column(name = ColumnNames.USER_CN_FIRST_NAME)
    private String firstName;

    @Column(name = ColumnNames.USER_CN_LAST_NAME)
    private String lastName;

    @Column(name = ColumnNames.USER_CN_BIO, columnDefinition = "TEXT")
    private String bio;

    @Column(name = ColumnNames.USER_CN_IS_ACTIVE, nullable = false)
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
