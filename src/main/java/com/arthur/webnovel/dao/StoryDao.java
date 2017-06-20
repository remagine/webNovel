package com.arthur.webnovel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.arthur.webnovel.entity.Member;
import com.arthur.webnovel.entity.Story;

@Repository
public class StoryDao extends DaoBase{

    public Integer insert(Story story) {
        return (Integer) session().save(story);
    }

    public Story get(int storyId, Member loginUser) {
        Criteria q = session().createCriteria(Story.class);
        q.add(Restrictions.eq("id", storyId))
        .add(Restrictions.eq("member", loginUser));

        return (Story) q.uniqueResult();
    }
}
