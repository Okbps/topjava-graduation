package com.task.voting.repository;

import com.task.voting.model.Cafe;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Aspire on 08.02.2017.
 */

@SuppressWarnings("ALL")
@Repository
@Transactional(readOnly = true)
public class CafeRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Cafe save(Cafe cafe) {
        if (cafe.isNew()) {
            em.persist(cafe);
            return cafe;
        } else {
            return em.merge(cafe);
        }
    }

    public Cafe get(int id) {
        return em.find(Cafe.class, id);
    }

    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Cafe.DELETE).setParameter("id", id).executeUpdate() != 0;
    }

    public List<Cafe> getAll() {
//        EntityGraph entityGraph = em.getEntityGraph(GRAPH_WITH_MENUS);
//        return em.createNamedQuery(Cafe.ALL_SORTED, Cafe.class).setHint("javax.persistence.fetchgraph", entityGraph).getResultList();
        return em.createNamedQuery(Cafe.ALL_SORTED, Cafe.class).getResultList();
    }
}
