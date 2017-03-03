package com.task.voting.repository;

import com.task.voting.model.CafeMenu;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Aspire on 08.02.2017.
 */
@Repository
@Transactional(readOnly = true)
public class CafeMenuRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public CafeMenu save(CafeMenu cafeMenu) {
        if (cafeMenu.isNew()) {
            em.persist(cafeMenu);
            return cafeMenu;
        } else {
            return em.merge(cafeMenu);
        }
    }

    public CafeMenu get(int id) {
        return em.find(CafeMenu.class, id);
    }

    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(CafeMenu.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    public List<CafeMenu> getAll() {
        return em.createNamedQuery(CafeMenu.ALL_SORTED, CafeMenu.class).getResultList();
    }}
