package com.dsmpear.main.service.member;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.team.Team;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.*;
import com.dsmpear.main.payload.request.MemberRequest;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Override
    public void setMember(MemberRequest memberRequest) {
        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Team team=teamRepository.findById(memberRequest.getTeamId())
                .orElseThrow(TeamNotFoundException::new);

        memberRepository.findByTeamIdAndUserEmail(team.getId(),user.getEmail())
                .ifPresent(member -> {throw new MemberAlreadyIncludeException();
                });

        memberRepository.save(
                Member.builder()
                        .teamId(team.getId())
                        .userEmail(user.getEmail())
                .build()
        );
    }

    @Override
    public void deleteMember(String userEmail) {
        //요청한 user가 팀 멤버인지 확인하기
        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotMemberException::new);

        Member member=memberRepository.findByUserEmail(user.getEmail())
                .orElseThrow(MemberNotFoundException::new);

        teamRepository.findById(member.getTeamId())
                .filter(team -> user.getEmail().equals(userEmail))
                .orElseThrow(TeamNotFoundException::new);

        memberRepository.delete(member);

    }
}
