package com.task.voting.repository;

import com.task.voting.model.Vote;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
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
    public Vote saveOrUpdate(Vote vote) {
        Vote found = get(vote.getId().getUser().getId(), vote.getId().getDateTime().toLocalDate());

        if(found==null) {
            em.persist(vote);
            return vote;
        }else{
            return em.merge(vote);
        }
    }

    public Vote get(int userId, LocalDate localDate) {
        try {
            return em.createNamedQuery(Vote.BY_USER_DATE, Vote.class)
                    .setParameter("userId", userId)
                    .setParameter("startDateTime", localDate.atTime(LocalTime.MIN))
                    .setParameter("endDateTime", localDate.atTime(LocalTime.MAX))
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Transactional
    public boolean delete(Vote vote) {
        return em.createNamedQuery(Vote.DELETE)
                .setParameter("votePK", vote.getId())
                .executeUpdate() != 0;
    }

    public List<Vote> getAll(int userId) {
        return em.createNamedQuery(Vote.ALL_SORTED, Vote.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
