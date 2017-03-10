package com.task.voting.repository;

import com.task.voting.AuthorizedUser;
import com.task.voting.model.Cafe;
import com.task.voting.model.Vote;
import com.task.voting.model.VotePK;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
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
//        em.remove(vote);
        em.persist(vote);
        return vote;
    }

    @Transactional
    public Vote save(int userId, LocalDateTime ldt, int cafeId) {
        Vote found = get(userId, ldt.toLocalDate());

        if(found==null) {

            int result = em.createNativeQuery(
                    "INSERT INTO votes(user_id, date_time, cafe_id) VALUES(?,?,?)")
                    .setParameter(1, userId)
                    .setParameter(2, Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()), TemporalType.TIMESTAMP)
                    .setParameter(3, cafeId)
                    .executeUpdate();

            if(result==1){
                return get(userId, ldt.toLocalDate());
            } else {
                return null;
            }

        }else{

            found.setCafe(em.getReference(Cafe.class, cafeId));
            found.getId().setDateTime(ldt);
            return em.merge(found);

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
