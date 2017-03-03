package com.task.voting.repository;

import com.task.voting.model.Vote;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Aspire on 08.02.2017.
 */
@Repository
@Transactional(readOnly = true)
public class VoteRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Vote save(Vote vote) {
        em.remove(vote);
        em.persist(vote);
        return vote;
    }

    public Vote get(int id) {
        return em.find(Vote.class, id);
    }

    @Transactional
    public boolean delete(int userId, LocalDateTime dateTime) {
        return em.createNamedQuery(Vote.DELETE)
                .setParameter("userId", userId)
                .setParameter("dateTime", dateTime)
                .executeUpdate() != 0;
    }

    public List<Vote> getAll(int userId) {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
