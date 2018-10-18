package com.example.demo.db.dao.impl;

import com.example.demo.db.dao.UserRepository;
import com.example.demo.db.models.CustomUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(CustomUser user) {
        entityManager.persist(user);
    }


    @Override
    public void deleteUser(CustomUser user) {
        entityManager.remove(entityManager.getReference(CustomUser.class, user.getId()));
    }

    @Override
    public void updateUser(CustomUser user) {
        entityManager.merge(user);
    }

    @Override
    public CustomUser getUserById(Long id) {
        return entityManager.find(CustomUser.class, id);
    }

    @Override
    public List<CustomUser> getAllUsers() {
        return entityManager.createNamedQuery(CustomUser.GET_ALL_USERS, CustomUser.class).getResultList();
    }

    @Override
    public CustomUser getUserByLogin(String login) { // как-то уродливо выглядит
        CustomUser user;
        try {
            user = entityManager.createQuery("SELECT cu FROM CustomUser cu WHERE cu.login = :login", CustomUser.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
        return user;
    }
}
