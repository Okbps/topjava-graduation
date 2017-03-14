package com.task.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;


@NamedQueries({
        @NamedQuery(name = Vote.DELETE, query = "DELETE FROM Vote v WHERE v.id=:votePK"),

        @NamedQuery(name = Vote.ALL_SORTED, query = "SELECT v FROM Vote v " +
                "LEFT JOIN FETCH v.cafe " +
                "LEFT JOIN FETCH v.id.user " +
                "WHERE v.id.user.id=:userId " +
                "ORDER BY v.id.dateTime"),

        @NamedQuery(name = Vote.BY_USER_DATE, query = "SELECT v FROM Vote v " +
                "LEFT JOIN FETCH v.cafe " +
                "LEFT JOIN FETCH v.id.user " +
                "WHERE v.id.user.id=:userId AND v.id.dateTime >= :startDateTime and v.id.dateTime <= :endDateTime " +
                "ORDER BY v.id.dateTime"),

        @NamedQuery(name = Vote.UPDATE, query = "UPDATE Vote v SET v.cafe = :cafe " +
                "WHERE v.id=:votePK"),

//        @NamedQuery(name = Vote.WINNER_BY_DATE, query = "SELECT c FROM Cafe c " +
//                "WHERE c.id =(SELECT v.cafe.id FROM Vote v GROUP BY v.id COUNT(v.id.user.id)) )"
        @NamedQuery(name = Vote.WINNER_BY_DATE, query = "SELECT v FROM Vote v " +
                "WHERE v.id.dateTime >= :startDateTime and v.id.dateTime <= :endDateTime " +
                "GROUP BY v.cafe " +
                "ORDER BY COUNT(v.id.user) DESC"
        )
})

@Entity
@Table(name="votes")
public class Vote {

    public static final String DELETE = "Vote.delete";
    public static final String UPDATE = "Vote.update";
    public static final String ALL_SORTED = "Vote.getAllSorted";
    public static final String BY_USER_DATE = "Vote.getByUserDate";
    public static final String WINNER_BY_DATE = "Cafe.getWinnerByDate";

    @EmbeddedId
    private VotePK id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "cafe_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cafe cafe;

    public Vote() {
    }

    public Vote(VotePK id, Cafe cafe) {
        this.id = id;
        this.cafe = cafe;
    }

    public Vote(User user, LocalDateTime ldt, Cafe cafe) {
        this.id = new VotePK(user, ldt);
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

    public void setUser(User user) {
        this.id.setUser(user);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "user="+(id==null?null:id.getUser())+
                ", dateTime="+(id==null?null:id.getDateTime())+
                ", cafe="+cafe;
    }
}
