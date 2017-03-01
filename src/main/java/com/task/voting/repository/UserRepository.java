package com.task.voting.repository;

import com.task.voting.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }

    public User get(int id) {
        return em.find(User.class, id);
    }

    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(User.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    public List<User> getAll() {
        return em.createNamedQuery(User.ALL_SORTED, User.class).getResultList();
    }
}
