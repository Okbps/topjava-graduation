package com.task.voting.util;

import com.task.voting.HasId;
import com.task.voting.util.exception.WrongTiming;
import com.task.voting.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.task.voting.util.DateTimeUtil.ZERO_HOUR;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void checkIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    public static void checkVoteTime(LocalDateTime ldt){
        if(!ldt.toLocalTime().isBefore(ZERO_HOUR)){
            throw new WrongTiming("Voting is ended");
        }

    }

    public static void checkResultTime(LocalDate ld){
        if(ld.equals(LocalDate.now()) && LocalTime.now().isBefore(ZERO_HOUR)){
            throw new WrongTiming("Results announced at "+ZERO_HOUR.format(DateTimeFormatter.ofPattern("hh:mm")));
        }

        if(ld.isAfter(LocalDate.now())){
            throw new WrongTiming("Voting haven't started yet");
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
