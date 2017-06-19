package com.arthur.webnovel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arthur.webnovel.dao.StoryDao;
import com.arthur.webnovel.entity.Story;

@Service
public class StoryService {

    @Autowired
    private StoryDao storyDao;

    @Transactional
    public Integer insert(Story story) {
        return storyDao.insert(story);
    }

}
