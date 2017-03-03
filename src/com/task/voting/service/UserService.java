package com.task.voting.service;

import com.task.voting.model.User;
import com.task.voting.repository.UserRepository;
import com.task.voting.util.exception.NotFoundException;
import com.task.voting.AuthorizedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.task.voting.util.ValidationUtil.checkNotFound;
import static com.task.voting.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    public User save(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }

    public AuthorizedUser loadUserByUsername(String name) throws UsernameNotFoundException {
        User u = repository.getByName(name.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + name + " is not found");
        }
        return new AuthorizedUser(u);
    }

    public User getByName(String name) throws NotFoundException {
        Assert.notNull(name, "name must not be null");
        return checkNotFound(repository.getByName(name), "name=" + name);
    }

}
