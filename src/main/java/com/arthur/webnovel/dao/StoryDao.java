package com.arthur.webnovel.dao;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.arthur.webnovel.entity.Story;

@Repository
public class StoryDao extends DaoBase{

    public Integer insert(Story story) {
        return (Integer) session().save(story);
    }

    public Story get(int storyId) {
        Criteria q = session().createCriteria(Story.class);

        return null;
    }
}
