package com.task.voting.repository;

import com.task.voting.model.Vote;
import com.task.voting.to.CafeWithVotes;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Aspire on 08.02.2017.
 */
@SuppressWarnings("ALL")
@Repository
@Transactional(readOnly = true)
public class VoteRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Vote saveOrUpdate(Vote vote) {
        Vote found = getByDateTime(vote.getId().getUser().getId(),
                vote.getId().getDateTime(),
                vote.getId().getDateTime());

        if(found==null) {
            em.persist(vote);
            return vote;
        }else{
            return em.merge(vote);
        }
    }

    public Vote getByDateTime(int userId, LocalDateTime startDT, LocalDateTime endDT) {
        try {
            return em.createNamedQuery(Vote.BY_USER_DATE, Vote.class)
                    .setParameter("userId", userId)
                    .setParameter("startDateTime", startDT)
                    .setParameter("endDateTime", endDT)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public List<CafeWithVotes> getWinners(LocalDateTime startDT, LocalDateTime endDT){
        List<CafeWithVotes>list;

        try {
            TypedQuery<CafeWithVotes>tq = em.createNamedQuery(Vote.WINNER_BY_DATE, CafeWithVotes.class);

            list = tq.setParameter("startDateTime", startDT)
                    .setParameter("endDateTime", endDT)
                    .getResultList();

        }catch(NoResultException e) {
            list = new ArrayList<>();
        }

        if(!list.isEmpty()) {
            long votesMaxCount = list.get(0).getVotesCount();
            return list.stream().filter(c -> c.getVotesCount() == votesMaxCount).collect(Collectors.toList());
        }else {
            return list;
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
