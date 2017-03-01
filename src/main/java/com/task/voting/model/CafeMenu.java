package com.task.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by Aspire on 08.02.2017.
 */
@Entity
@Table(name = "menus")
public class CafeMenu extends NamedEntity{

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

}

