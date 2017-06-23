package com.arthur.webnovel.service;

import java.util.List;

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
        return storyDao.get(storyId, loginUser);
    }

    @Transactional
    public List<Story> list(Member loginUser) {
        return storyDao.list(loginUser);
    }

    @Transactional
    public void update(Story story) {
        storyDao.update(story);
    }

}
