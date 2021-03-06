package org.code.ruyin.util.snippet.ds.service.impl;

import org.code.ruyin.util.snippet.ds.annotation.Master;
import org.code.ruyin.util.snippet.ds.dao.entity.Member;
import org.code.ruyin.util.snippet.ds.dao.entity.MemberExample;
import org.code.ruyin.util.snippet.ds.dao.mapper.MemberMapper;
import org.code.ruyin.util.snippet.ds.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: hjxz
 * @date: 2021/4/6
 * @desc:
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Transactional
    @Override
    public int insert(Member member) {
        return memberMapper.insert(member);
    }

    @Override
    public int save(Member member) {
        return memberMapper.insert(member);
    }

    @Override
    public List<Member> selectAll() {
        return memberMapper.selectByExample(new MemberExample());
    }

    @Master
    @Override
    public String getToken(String appId) {
        return "test for master db";
    }
}
