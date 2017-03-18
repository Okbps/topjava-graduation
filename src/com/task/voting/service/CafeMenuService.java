package com.task.voting.service;

import com.task.voting.model.CafeMenu;
import com.task.voting.repository.CafeMenuRepository;
import com.task.voting.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.task.voting.util.ValidationUtil.checkNotFoundWithId;

/**
 * Created by Aspire on 01.03.2017.
 */
@SuppressWarnings("ALL")
@Service
public class CafeMenuService {
    @Autowired
    private CafeMenuRepository repository;

    public CafeMenu save(CafeMenu cafeMenu) {
        Assert.notNull(cafeMenu, "cafe menu must not be null");
        return repository.save(cafeMenu);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public CafeMenu get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<CafeMenu> getAll(Integer cafeId, LocalDate localDate) {
        return repository.getAll(cafeId, localDate);
    }

    public void update(CafeMenu user) {
        Assert.notNull(user, "user must not be null");
        repository.save(user);
    }
}
