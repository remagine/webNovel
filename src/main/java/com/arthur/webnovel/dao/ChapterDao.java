package com.arthur.webnovel.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.arthur.webnovel.entity.Chapter;
import com.arthur.webnovel.entity.Story;

@Repository
public class ChapterDao extends DaoBase{

    public Integer insert(Chapter chapter) {
        return (Integer) session().save(chapter);
    }

    public Chapter get(Story story, int chapterId){
        Criteria q = session().createCriteria(Chapter.class);
        q.add(Restrictions.eq("id", chapterId))
        .add(Restrictions.eq("story", story));

        return (Chapter) q.uniqueResult();
    }

    public void update(Chapter chapter) {
        session().update(chapter);
    }
}
