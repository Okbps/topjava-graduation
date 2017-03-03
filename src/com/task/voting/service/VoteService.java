package com.task.voting.service;

import com.task.voting.model.Vote;
import com.task.voting.repository.VoteRepository;
import com.task.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

import static com.task.voting.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Aspire on 01.03.2017.
 */
@Service
public class VoteService {

    @Autowired
    private VoteRepository repository;

    public Vote save(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote);
    }

    public void delete(int userId, LocalDateTime dateTime) {
        checkNotFoundWithId(repository.delete(userId, dateTime), userId);
    }

    public Vote get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Vote user) {
        Assert.notNull(user, "vote must not be null");
        repository.save(user);
    }
}
