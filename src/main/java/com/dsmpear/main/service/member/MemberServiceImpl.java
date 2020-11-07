package com.dsmpear.main.service.member;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.team.Team;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.MemberAlreadyIncludeException;
import com.dsmpear.main.exceptions.TeamNotFoundException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.exceptions.UserNotMemberException;
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
    public void addMember(MemberRequest memberRequest) {

        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        //요청한 user가 팀 멤버인지 확인하기
        Member member=memberRepository.findByUserEmail(memberRequest.getUserEmail())
                .orElseThrow(UserNotMemberException::new);

        //멤버 이용해서 팀 찾기
        Team team=teamRepository.findById(member.getTeamId())
                .filter(t -> user.getEmail().equals(t.getUserEmail()))
                .orElseThrow(TeamNotFoundException::new);

        //팀 아이디랑 유저 이메일로 찾기
        memberRepository.findByTeamIdAndUserEmail(team.getId(),user.getEmail())
                .ifPresent(m -> {throw new MemberAlreadyIncludeException();
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

        Member member=memberRepository.findByUserEmail(userEmail)
                .orElseThrow(UserNotMemberException::new);

        teamRepository.findById(member.getTeamId())
                .filter(team -> user.getEmail().equals(member.getUserEmail()))
                .orElseThrow(TeamNotFoundException::new);

        memberRepository.delete(member);

    }
}
