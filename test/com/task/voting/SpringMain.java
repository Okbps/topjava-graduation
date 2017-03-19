package com.task.voting;

import com.task.voting.util.PasswordUtil;

/**
 * Created by Aspire on 18.03.2017.
 */
public class SpringMain {
    public static void main(String[] args) {
        System.out.println(PasswordUtil.encode("admin"));
        System.out.println(PasswordUtil.encode("password"));
    }
}
