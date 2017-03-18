package com.task.voting.util.exception;

/**
 * Created by Aspire on 10.03.2017.
 */
@SuppressWarnings("ALL")
public class WrongTiming extends RuntimeException{
    public WrongTiming(String message) {
        super(message);
    }
}
