package com.task.voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Aspire on 08.02.2017.
 */
@Repository
@Transactional(readOnly = true)
public class VoteRepository {
    @PersistenceContext
    private EntityManager em;
}
