package com.task.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id.user.id=:userId and v.id.dateTime=:dateTime"),
        @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT v FROM Vote v JOIN FETCH v.cafe " +
                "WHERE v.id.user.id=:userId " +
                "ORDER BY v.id.dateTime"),
        @NamedQuery(name = Vote.BY_USER_DATETIME, query = "SELECT v FROM Vote v " +
                "JOIN FETCH v.cafe " +
//                "LEFT JOIN FETCH v.id.dateTime " +
                "LEFT JOIN FETCH v.id.user " +
                "WHERE v.id.user.id=:userId AND v.id.dateTime=:dateTime " +
                "ORDER BY v.id.dateTime"),
        @NamedQuery(name = Vote.UPDATE, query = "UPDATE Vote v SET v.cafe = :cafe " +
                "where v.id=:votePK")
})
@Entity
@Table(name="votes")
public class Vote {

    public static final String DELETE = "Vote.delete";
    public static final String UPDATE = "Vote.update";
    public static final String ALL_SORTED = "Vote.getAllSorted";
    public static final String BY_USER_DATETIME = "Vote.getByUserDateTime";

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

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }
}
