package com.task.voting.service;

import com.task.voting.model.Cafe;
import com.task.voting.repository.CafeRepository;
import com.task.voting.util.ValidationUtil;
import com.task.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Aspire on 01.03.2017.
 */
@Service
public class CafeService {
    @Autowired
    private CafeRepository repository;

    public Cafe save(Cafe cafe) {
        Assert.notNull(cafe, "cafe must not be null");
        return repository.save(cafe);
    }

    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(repository.delete(id), id);
    }

    public Cafe get(int id) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id), id);
    }

    public List<Cafe> getAll() {
        return repository.getAll();
    }

    public void update(Cafe cafe) {
        Assert.notNull(cafe, "cafe must not be null");
        repository.save(cafe);
    }
}
