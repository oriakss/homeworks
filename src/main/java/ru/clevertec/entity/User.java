package ru.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static ru.clevertec.util.Constants.EMAIL_COLUMN;
import static ru.clevertec.util.Constants.FIRSTNAME_COLUMN;
import static ru.clevertec.util.Constants.ID_COLUMN;
import static ru.clevertec.util.Constants.LOGIN_COLUMN;
import static ru.clevertec.util.Constants.PASSWORD_COLUMN;
import static ru.clevertec.util.Constants.PERSON;
import static ru.clevertec.util.Constants.ROLE_COLUMN;
import static ru.clevertec.util.Constants.SURNAME_COLUMN;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = PERSON)
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = ID_COLUMN)
    private Long id;

    @Column(name = FIRSTNAME_COLUMN)
    private String firstname;

    @Column(name = SURNAME_COLUMN)
    private String surname;

    @Column(name = EMAIL_COLUMN)
    private String email;

    @Column(name = LOGIN_COLUMN)
    private String login;

    @Column(name = PASSWORD_COLUMN)
    private String password;

    @Enumerated(STRING)
    @Column(name = ROLE_COLUMN)
    private UserRole userRole;
}