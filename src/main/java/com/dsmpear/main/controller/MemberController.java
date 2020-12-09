package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.MemberRequest;
import com.dsmpear.main.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addMember(@RequestBody MemberRequest memberRequest){
        memberService.addMember(memberRequest);
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable Integer memberId){
        memberService.deleteMember(memberId);
    }

}
