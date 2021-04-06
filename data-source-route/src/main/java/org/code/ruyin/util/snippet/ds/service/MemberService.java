package org.code.ruyin.util.snippet.ds.service;

import org.code.ruyin.util.snippet.ds.dao.entity.Member;

import java.util.List;

/**
 * @author: hjxz
 * @date: 2021/4/6
 * @desc:
 */
public interface MemberService {


    int insert(Member member);

    int save(Member member);

    List<Member> selectAll();

    String getToken(String appId);

}
