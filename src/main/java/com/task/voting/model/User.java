package com.task.voting.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.*;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries({
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = User.ALL_SORTED, query = "SELECT u FROM User u ORDER BY u.name, u.email"),
})
@Entity
@Table(name = "users")
public class User extends NamedEntity {

    public static final String DELETE = "User.delete";
    public static final String ALL_SORTED = "User.getAllSorted";

    @Column(name = "password", nullable = false)
    @NotBlank
    @Length(min = 5)
    private String password;

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

    public User() {
    }

    public User(User u) {
        this(u.getId(), u.getName(), u.getPassword(), u.getRoles());
    }

    public User(Integer id, String name, String password, Role role, Role... roles) {
        this(id, name, password, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String password, Set<Role> roles) {
        super(id, name);
        this.password = password;
        setRoles(roles);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setRoles(Collection<Role> roles) {
        if(CollectionUtils.isEmpty(roles)) {
            this.roles = Collections.emptySet();
        }else{
            this.roles = EnumSet.copyOf(roles);
        };
    }

    @Override
    public String toString() {
        return "User (" +
                "id=" + getId() +
                ", name=" + name +
                ", roles=" + roles +
                ')';
    }
}
