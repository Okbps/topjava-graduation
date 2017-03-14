package com.task.voting.service;

import com.task.voting.model.Cafe;
import com.task.voting.model.User;
import com.task.voting.model.Vote;
import com.task.voting.repository.VoteRepository;
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

    public void delete(Vote vote, User user) {
        vote.setUser(user);
        repository.delete(vote);
    }

    public Vote get(int userId, LocalDateTime startDT, LocalDateTime endDT) {
        return repository.getByDateTime(userId, startDT, endDT);
    }

    public Cafe getWinner(LocalDateTime startDT, LocalDateTime endDT) {
        return repository.getWinner(startDT, endDT);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        repository.saveOrUpdate(vote);
    }

    public Vote saveOrUpdate(Vote vote, User user){
        vote.setUser(user);
        return repository.saveOrUpdate(vote);
    }
}
