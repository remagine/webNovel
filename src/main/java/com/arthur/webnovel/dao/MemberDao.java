package com.arthur.webnovel.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.arthur.webnovel.entity.Member;

@Repository
public class MemberDao extends DaoBase{
    public int insert(Member member) {
        return (int) session().save(member);
    }

    public Member select(String email) {
        return (Member) session().createCriteria(Member.class).add(Restrictions.eq("email", email)).uniqueResult();
    }
}
