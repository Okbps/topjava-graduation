package com.task.voting.model;

import javax.persistence.*;

/**
 * Created by Aspire on 08.02.2017.
 */
@NamedQueries({
        @NamedQuery(name = Cafe.DELETE, query = "DELETE FROM Cafe c WHERE c.id=:id"),
//        @NamedQuery(name = Cafe.ALL_SORTED, query = "SELECT c FROM Cafe c JOIN FETCH c.menus ORDER BY c.name"),
        @NamedQuery(name = Cafe.ALL_SORTED, query = "SELECT c FROM Cafe c ORDER BY c.name"),
})
@Entity
@Table(name = "cafes")
public class Cafe extends NamedEntity{

    public static final String GRAPH_WITH_MENUS = "Cafe.withMenus";
    public static final String DELETE = "Cafe.delete";
    public static final String ALL_SORTED = "Cafe.getAllSorted";

    public Cafe() {
    }

    public Cafe(Cafe c) {
        this(c.getId(), c.getName());
    }

    public Cafe(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cafe{id="+getId()+", name="+name+"}";
    }
}
