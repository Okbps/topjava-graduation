package com.task.voting;

import com.task.voting.matcher.ModelMatcher;
import com.task.voting.model.*;
import com.task.voting.to.CafeWithVotes;
import com.task.voting.util.PasswordUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.task.voting.model.BaseEntity.START_SEQ;

public class TestData {

    public static final ModelMatcher<Cafe> CAFE_MATCHER = ModelMatcher.of(Cafe.class);
    public static final ModelMatcher<CafeMenu> MENU_MATCHER = ModelMatcher.of(CafeMenu.class);
    public static final ModelMatcher<Vote> VOTE_MATCHER = ModelMatcher.of(Vote.class);
    public static final ModelMatcher<CafeWithVotes> CAFE_W_VOTES_MATCHER = ModelMatcher.of(CafeWithVotes.class);

    public static final int USER_ID = START_SEQ;
    public static final int CAFE1_ID = START_SEQ + 5;
    public static final int MENU_ID = START_SEQ + 8;

    public static final User USER1 = new User(USER_ID + 0, "Admin", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);
    public static final User USER2 = new User(USER_ID + 1, "Natallia", "password", Role.ROLE_USER);
    public static final User USER3 = new User(USER_ID + 2, "Andrey", "password", Role.ROLE_USER);
    public static final User USER4 = new User(USER_ID + 3, "Nadja", "password", Role.ROLE_USER);
    public static final User USER5 = new User(USER_ID + 4, "Vera", "password", Role.ROLE_USER);

    public static final Cafe CAFE1 = new Cafe(CAFE1_ID + 0, "Papa Johns");
    public static final Cafe CAFE2 = new Cafe(CAFE1_ID + 1, "Sbarro");
    public static final Cafe CAFE3 = new Cafe(CAFE1_ID + 2, "Dominos");

    public static final CafeMenu CAFE_MENU1 = new CafeMenu(MENU_ID + 0, CAFE1, LocalDateTime.of(2017, 2, 8, 10, 0), "Margarita", 7);
    public static final CafeMenu CAFE_MENU2 = new CafeMenu(MENU_ID + 1, CAFE1, LocalDateTime.of(2017, 2, 8, 10, 0), "Hawaii", 10);
    public static final CafeMenu CAFE_MENU3 = new CafeMenu(MENU_ID + 2, CAFE2, LocalDateTime.of(2017, 2, 8, 10, 0), "Diabolo", 9);
    public static final CafeMenu CAFE_MENU4 = new CafeMenu(MENU_ID + 3, CAFE2, LocalDateTime.of(2017, 2, 8, 10, 0), "Mexicana", 11);
    public static final CafeMenu CAFE_MENU5 = new CafeMenu(MENU_ID + 4, CAFE3, LocalDateTime.of(2017, 2, 8, 10, 0), "Cheese", 8);
    public static final CafeMenu CAFE_MENU6 = new CafeMenu(MENU_ID + 5, CAFE3, LocalDateTime.of(2017, 2, 8, 10, 0), "Pepperoni", 9);

    public static final Vote VOTE1 = new Vote(USER1, LocalDateTime.of(2017, 2, 8, 10, 0), CAFE1);

    public static final CafeWithVotes CWV1 = new CafeWithVotes(CAFE1,2);
    public static final CafeWithVotes CWV3 = new CafeWithVotes(CAFE3,2);

    public static final List<Cafe> CAFES = Arrays.asList(CAFE3, CAFE1, CAFE2);
    public static final List<CafeMenu> MENUS = Arrays.asList(CAFE_MENU5, CAFE_MENU3, CAFE_MENU2, CAFE_MENU1, CAFE_MENU4, CAFE_MENU6);
    public static final List<Vote> VOTES = Arrays.asList(VOTE1);
    public static final List<CafeWithVotes> CAFES_W_VOTES = Arrays.asList(CWV1, CWV3);

    public static Cafe getCreatedCafe() {
        return new Cafe(null, "New cafe");
    }
    public static Cafe getUpdatedCafe() {
        return new Cafe(CAFE1_ID, "Казачны замак");
    }

    public static CafeMenu getCreatedMenu() {
        return new CafeMenu(null, CAFE1, LocalDateTime.of(2017, 2, 9, 10, 0), "New dish", 6.99);
    }
    public static CafeMenu getUpdatedMenu() {
        return new CafeMenu(MENU_ID, CAFE1, LocalDateTime.of(2017, 2, 8, 10, 0), "Updated dish", 7.5);
    }

    public static Vote getCreatedVote(){
        return new Vote(null, LocalDateTime.of(2017, 3, 8, 12, 0), CAFE2);
    }
    public static Vote getUpdatedVote(){
        return new Vote(
                VOTE1.getId().getUser(),
                VOTE1.getId().getDateTime(),
                CAFE3);
    }
    public static Vote getCreatedLater(){
        return new Vote(
                VOTE1.getId().getUser(),
                VOTE1.getId().getDateTime().plusHours(1),
                CAFE2);
    }

    public static final ModelMatcher<User> USER_MATCHER = ModelMatcher.of(User.class,
            (expected, actual) -> expected == actual ||
                    (comparePassword(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );

    private static boolean comparePassword(String rawOrEncodedPassword, String password) {
        if (PasswordUtil.isEncoded(rawOrEncodedPassword)) {
            return rawOrEncodedPassword.equals(password);
        } else if (!PasswordUtil.isMatch(rawOrEncodedPassword, password)) {
            return false;
        }
        return true;
    }
}
