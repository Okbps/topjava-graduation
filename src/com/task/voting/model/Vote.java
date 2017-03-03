package com.task.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Created by Aspire on 08.02.2017.
 */
@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id.user.id=:userId and v.id.dateTime=:dateTime"),
        @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT v FROM Vote v " +
                "WHERE v.id.user.id=:userId " +
                "ORDER BY v.id.dateTime"),
})
@Entity
@Table(name="votes")
public class Vote {

    public static final String DELETE = "Vote.delete";
    public static final String ALL_SORTED = "Vote.getAllSorted";

    @EmbeddedId
    private VotePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cafe cafe;

    public Vote() {
    }

    public Vote(VotePK id, Cafe cafe) {
        this.id = id;
        this.cafe = cafe;
    }

    public VotePK getId() {
        return id;
    }
}
