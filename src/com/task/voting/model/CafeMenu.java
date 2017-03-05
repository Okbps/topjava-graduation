package com.task.voting.model;

import org.hibernate.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by Aspire on 08.02.2017.
 */
@NamedQueries({
        @NamedQuery(name = CafeMenu.DELETE, query = "DELETE FROM CafeMenu cm WHERE cm.id=:id"),
        @NamedQuery(name = CafeMenu.ALL_SORTED, query = "SELECT cm FROM CafeMenu cm JOIN FETCH cm.cafe ORDER BY cm.dateTime, cm.dish"),
//        @NamedQuery(name = CafeMenu.ALL_SORTED, query = "SELECT cm FROM CafeMenu cm ORDER BY cm.dateTime, cm.dish"),
})
@Entity
@Table(name = "menus")
public class CafeMenu extends BaseEntity{

    public static final String DELETE = "CafeMenu.delete";
    public static final String ALL_SORTED = "CafeMenu.getAllSorted";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cafe cafe;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "dish", nullable = false)
    @NotBlank
    private String dish;

    @Column(name = "price", nullable = false)
    @Range(min = 0, max = 5000)
    private int price;

    public CafeMenu(){}

    public CafeMenu(CafeMenu cafeMenu){
        this(cafeMenu.getCafe(), cafeMenu.getDateTime(), cafeMenu.getDish(), cafeMenu.getPrice());
    }

    public CafeMenu(Cafe cafe, LocalDateTime dateTime, String dish, int price) {
        this.cafe = cafe;
        this.dateTime = dateTime;
        this.dish = dish;
        this.price = price;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

