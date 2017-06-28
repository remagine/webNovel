package com.arthur.webnovel.service;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arthur.webnovel.dao.StoryDao;
import com.arthur.webnovel.entity.Member;
import com.arthur.webnovel.entity.Story;

@Service
public class StoryService {

    @Autowired
    private StoryDao storyDao;

    @Transactional
    public Integer insert(Story story) {
        return storyDao.insert(story);
    }

    @Transactional
    public Story get(int storyId, Member loginUser) {
        Story result = storyDao.get(storyId, loginUser);
        Hibernate.initialize(result.getChapterList());
        return result;
    }

    @Transactional
    public List<Story> list(Member loginUser) {
        List<Story> storyList = storyDao.list(loginUser);
        for(Story story : storyList){
            Hibernate.initialize(story.getChapterList());
        }
        return storyList;
    }

    @Transactional
    public void update(Story story) {
        storyDao.update(story);
    }

}
