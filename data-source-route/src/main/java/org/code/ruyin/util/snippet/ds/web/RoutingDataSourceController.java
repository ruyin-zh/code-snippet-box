package org.code.ruyin.util.snippet.ds.web;

import org.code.ruyin.util.snippet.ds.dao.entity.Member;
import org.code.ruyin.util.snippet.ds.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xzwang080
 * @desc
 * @date 2021/4/7
 */
@RestController
@RequestMapping("/api/test")
public class RoutingDataSourceController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/get")
    public List<Member> getAll(){
        return memberService.selectAll();
    }


    @PostMapping("/create")
    public int createMember(@RequestBody Member member){
        return memberService.insert(member);
    }


    @GetMapping("/getToken")
    public String getToken(){
        return memberService.getToken("test");
    }

}
