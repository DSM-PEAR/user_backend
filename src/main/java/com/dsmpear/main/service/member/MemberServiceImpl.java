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

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Override
    public void setMember(MemberRequest memberRequest) {
        //요청한 user가 팀 멤버인지 확인하기

        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Team team=teamRepository.findById(memberRequest.getTeamId())
                .filter(t -> user.getEmail().equals(t.getUserEmail()))
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
                .orElseThrow(UserNotFoundException::new);

        User member=userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotMemberException::new);

        String memberEmail=member.getEmail();

        if(user.getEmail().equals(memberEmail)) throw new UserNotFoundException();

        memberRepository.findByTeamIdAndUserEmail()

        List<Member> members=memberRepository.find

        teamRepository.findById(member.getTeamId())
                .filter(team -> user.getEmail().equals(userEmail))
                .orElseThrow(TeamNotFoundException::new);

        memberRepository.delete(member);

    }
}
