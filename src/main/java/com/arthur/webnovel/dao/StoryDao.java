package com.arthur.webnovel.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.arthur.webnovel.code.State;
import com.arthur.webnovel.entity.Member;
import com.arthur.webnovel.entity.Story;

@Repository
public class StoryDao extends DaoBase{
    private final static State[] storyState = { State.on, State.off };

    public Integer insert(Story story) {
        return (Integer) session().save(story);
    }

    public Story get(int storyId, Member loginUser) {
        Criteria q = session().createCriteria(Story.class);
        q.add(Restrictions.eq("id", storyId))
        .add(Restrictions.eq("member", loginUser))
        .add(Restrictions.in("state", storyState));

        return (Story) q.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<Story> list(Member loginUser) {
        Criteria q = session().createCriteria(Story.class);
        q.add(Restrictions.eq("member", loginUser))
        .add(Restrictions.in("state", storyState));

        return q.list();
    }

    public void update(Story story) {
        session().update(story);
    }
}
