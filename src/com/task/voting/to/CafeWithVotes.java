package com.task.voting.to;

import com.task.voting.model.Cafe;
import com.task.voting.model.NamedEntity;

/**
 * Created by Aspire on 16.03.2017.
 */
@SuppressWarnings("ALL")
public class CafeWithVotes extends NamedEntity {
    private long votesCount;

    public CafeWithVotes() {
    }

    public CafeWithVotes(Integer id, String name, Long votesCount) {
        super(id, name);
        this.votesCount = votesCount;
    }

    public CafeWithVotes(Cafe cafe, long votesCount) {
        super(cafe.getId(), cafe.getName());
        this.votesCount = votesCount;
    }

    public long getVotesCount() {
        return votesCount;
    }
}
