package ru.clevertec.util;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import static ru.clevertec.util.Constants.ACCOUNT;

public class JPAUtil {

    private static final EntityManager ENTITY_MANAGER = buildEntityManager();

    private static EntityManager buildEntityManager() {
        return Persistence.createEntityManagerFactory(ACCOUNT).createEntityManager();
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER;
    }
}