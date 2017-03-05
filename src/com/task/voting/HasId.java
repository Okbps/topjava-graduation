package com.task.voting;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * gkislin
 * 12.02.2017
 */
public interface HasId {
    Integer getId();

    void setId(Integer id);

    @JsonIgnore
    default boolean isNew() {
        return (getId() == null);
    }
}
