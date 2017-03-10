package com.task.voting.util.exception;

/**
 * Created by Aspire on 10.03.2017.
 */
public class LateVoteException extends RuntimeException{
    public LateVoteException(String message) {
        super(message);
    }
}
