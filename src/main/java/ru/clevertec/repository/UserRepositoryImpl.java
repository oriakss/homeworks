package ru.clevertec.repository;

import ru.clevertec.entity.User;
import ru.clevertec.mapper.UserMapper;
import ru.clevertec.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.clevertec.entity.UserRole.ADMIN;
import static ru.clevertec.entity.UserRole.USER;
import static ru.clevertec.util.Constants.ID;
import static ru.clevertec.util.Constants.USER_EMAIL;
import static ru.clevertec.util.Constants.USER_FIRSTNAME;
import static ru.clevertec.util.Constants.USER_LOGIN;
import static ru.clevertec.util.Constants.USER_PASSWORD;
import static ru.clevertec.util.Constants.USER_ROLE;
import static ru.clevertec.util.Constants.USER_SURNAME;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepository userRepository;
    private final EntityManager entityManager = JPAUtil.getEntityManager();
    private final EntityTransaction transaction = entityManager.getTransaction();
    private final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    private List<User> users;

    @Override
    public Optional<User> createUser(User user) {
        transaction.begin();
        entityManager.persist(user);

        CriteriaQuery<Long> userCriteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> userRoot = userCriteriaQuery.from(User.class);

        userCriteriaQuery
                .select(userRoot.get(ID))
                .where(criteriaBuilder.equal(userRoot.get(USER_LOGIN), user.getLogin()));
        Long userId = entityManager
                .createQuery(userCriteriaQuery)
                .getResultList()
                .stream()
                .findAny()
                .orElseThrow();

        transaction.commit();

        user.setId(userId);
        users.add(user);
        return Optional.of(user);
    }

    @Override
    public Optional<List<User>> readUsers() {
        if (users == null || users.isEmpty()) {
            transaction.begin();

            CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = userCriteriaQuery.from(User.class);

            userCriteriaQuery.select(userRoot);
            users = entityManager.createQuery(userCriteriaQuery).getResultList();

            transaction.commit();
        }
        return Optional.of(users);
    }

    @Override
    public Optional<User> updateUser(User updatedUser) {
        transaction.begin();

        CriteriaUpdate<User> userCriteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
        Root<User> userRoot = userCriteriaUpdate.from(User.class);

        userCriteriaUpdate
                .set(USER_FIRSTNAME, updatedUser.getFirstname())
                .set(USER_SURNAME, updatedUser.getSurname())
                .set(USER_EMAIL, updatedUser.getEmail())
                .set(USER_LOGIN, updatedUser.getLogin())
                .set(USER_PASSWORD, updatedUser.getPassword())
                .set(USER_ROLE, updatedUser.getUserRole())
                .where((criteriaBuilder.equal(userRoot.get(ID), updatedUser.getId())));
        entityManager.createQuery(userCriteriaUpdate).executeUpdate();

        transaction.commit();

        User user = users.stream()
                .filter(item -> Objects.equals(item.getId(), updatedUser.getId()))
                .findAny()
                .orElseThrow();
        int ind = users.indexOf(user);
        users.remove(user);
        users.add(ind, updatedUser);
        return Optional.of(user);
    }

    @Override
    public Optional<User> deleteUser(Long userId) {
        transaction.begin();

        CriteriaDelete<User> userCriteriaDelete = criteriaBuilder.createCriteriaDelete(User.class);
        Root<User> userRootDelete = userCriteriaDelete.from(User.class);

        userCriteriaDelete.where(criteriaBuilder.equal(userRootDelete.get(ID), userId));
        entityManager.createQuery(userCriteriaDelete).executeUpdate();

        transaction.commit();

        User user = users.stream()
                .filter(item -> Objects.equals(item.getId(), userId))
                .findAny()
                .orElseThrow();
        users.remove(user);
        return Optional.of(user);
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepositoryImpl();
        }
        return userRepository;
    }

    private UserRepositoryImpl() {
        if (readUsers().orElseThrow().isEmpty()) {
            UserMapper userMapper = UserMapper.getInstance();
            transaction.begin();

            entityManager.persist(userMapper.buildUserManually("Admin", "1", "admin@admin.com",
                    "admin", "admin", ADMIN));
            entityManager.persist(userMapper.buildUserManually("User", "1", "user1@user.com",
                    "user1", "user1", USER));
            entityManager.persist(userMapper.buildUserManually("User", "2", "user2@user.com",
                    "user2", "user2", USER));
            entityManager.persist(userMapper.buildUserManually("User", "3", "user3@user.com",
                    "user3", "user3", USER));
            entityManager.persist(userMapper.buildUserManually("User", "4", "user4@user.com",
                    "user4", "user4", USER));

            transaction.commit();
        }
    }
}