package com.arthur.webnovel.dao;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.arthur.webnovel.code.State;
import com.arthur.webnovel.entity.Member;

@Repository
public class MemberDao extends DaoBase{
    public int insert(Member member) {
        return (int) session().save(member);
    }

    public Member select(String email) {
        return (Member) session()
                .createCriteria(Member.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("state", State.on))
                .uniqueResult();
    }

    public void update(Member memberForm) {
        session().update(memberForm);
    }

    public Member select(Integer id) {
        return (Member) session()
                .createCriteria(Member.class)
                .add(Restrictions.eq("id", id))
                .add(Restrictions.eq("state", State.on))
                .uniqueResult();
    }
}
