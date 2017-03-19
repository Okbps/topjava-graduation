package com.task.voting.util;

import com.task.voting.model.User;

public class UserUtil {

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        return user;
    }
}
