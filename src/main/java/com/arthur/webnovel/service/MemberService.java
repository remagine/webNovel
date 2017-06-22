package com.arthur.webnovel.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arthur.webnovel.code.Result;
import com.arthur.webnovel.code.State;
import com.arthur.webnovel.dao.MemberDao;
import com.arthur.webnovel.entity.Member;
import com.arthur.webnovel.util.BaseUtil;

@Service
public class MemberService {
    @Autowired
    private MemberDao memberDao;

    @Transactional
    public int insert(Member member) {
        return memberDao.insert(member);
    }

    @Transactional
    public Result<Member> authenticate(String email, String password) {
        Member member = select(email);
        if (member == null || State.on != member.getState()) {
            return Result.error();
        }
        if (StringUtils.equals(member.getPassword(), BaseUtil.getPassword(password))) {
            return Result.success(member);
        }
        return Result.error();
    }

    @Transactional
    public Member select(String userId) {
        return memberDao.select(userId);
    }

    @Transactional
    public Member select(Integer id) {
        return memberDao.select(id);
    }

    @Transactional
    public void update(Member memberForm) {
        memberDao.update(memberForm);
    }
}
