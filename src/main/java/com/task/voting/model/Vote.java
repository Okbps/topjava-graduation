package com.task.voting.model;

import javax.persistence.*;

/**
 * Created by Aspire on 08.02.2017.
 */
@Entity
@Table(name="votes")
public class Vote {
    @EmbeddedId
    private VotePK id;
}
