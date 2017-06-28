package com.arthur.webnovel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arthur.webnovel.dao.ChapterDao;
import com.arthur.webnovel.entity.Chapter;
import com.arthur.webnovel.entity.Story;

@Service
public class ChapterService {
    @Autowired
    private ChapterDao chapterDao;

    @Transactional
    public Integer insert(Chapter chapter) {
        return chapterDao.insert(chapter);
    }

    @Transactional
    public Chapter get(Story story, int chapterId) {
        return chapterDao.get(story, chapterId);
    }

    @Transactional
    public void update(Chapter chapter) {
        chapterDao.update(chapter);
    }
}
