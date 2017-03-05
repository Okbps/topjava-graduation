package com.task.voting.web.rest;

import com.task.voting.model.BaseEntity;
import com.task.voting.model.User;
import com.task.voting.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;

import javax.validation.ValidationException;
import java.util.List;

import static com.task.voting.util.ValidationUtil.checkIdConsistent;
import static com.task.voting.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService service;

    private boolean systemUserForbiddenModification;

    @Autowired
    public void setEnvironment(Environment environment) {
        systemUserForbiddenModification = false;
    }

    public void checkModificationAllowed(Integer id) {
        if (systemUserForbiddenModification && id < BaseEntity.START_SEQ + 2) {
            throw new ValidationException(messageSource.getMessage("user.modificationRestriction", null, LocaleContextHolder.getLocale()));
        }
    }

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get " + id);
        return service.get(id);
    }

    public User create(User user) {
        checkNew(user);
        log.info("create " + user);
        return service.save(user);
    }

    public void delete(int id) {
        log.info("delete " + id);
        checkModificationAllowed(id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("update " + user);
        checkIdConsistent(user, id);
        checkModificationAllowed(id);
        service.update(user);
    }

    public User getByName(String name) {
        log.info("getByName " + name);
        return service.getByName(name);
    }
}
