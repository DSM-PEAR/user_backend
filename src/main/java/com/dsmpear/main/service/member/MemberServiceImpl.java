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
public class MemberServiceImpl implements MemberService {

    private final AuthenticationFacade authenticationFacade;

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Override
    public void addMember(MemberRequest memberRequest) {
        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        userRepository.findByEmail(memberRequest.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        //멤버 이용해서 팀 찾기
        Team team=teamRepository.findById(memberRequest.getTeamId())
                .orElseThrow(TeamNotFoundException::new);

        //요청한 user가 팀 멤버인지 확인하기
        memberRepository.findByTeamIdAndUserEmail(team.getId(), user.getEmail())
                .orElseThrow(UserNotMemberException::new);

        //팀 아이디랑 유저 이메일로 찾기
        memberRepository.findByTeamIdAndUserEmail(team.getId(), memberRequest.getUserEmail())
                .ifPresent(m -> {
                    throw new MemberAlreadyIncludeException();
                });

        memberRepository.save(
                Member.builder()
                    .teamId(team.getId())
                    .userEmail(memberRequest.getUserEmail())
                    .build()
        );
    }

    @Override
    public void deleteMember(Integer memberId) {
        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Team team=teamRepository.findById(memberId)
                .orElseThrow(TeamNotFoundException::new);

        Member member=memberRepository.findById(memberId)
                .orElseThrow(UserNotMemberException::new);

        //만약 최초 생성자를 지우려고 하면, 삭제를 요청한 유저 이메일로 대체하기
        if(team.getUserEmail().equals(member.getUserEmail())){
            //메모리에서의 변경
            team.updateUser(user.getEmail());
            //디비 변경
            teamRepository.save(team);
        }

        if(user.getEmail().equals(member.getUserEmail())){
            throw new UserEqualsMemberException();
        }

        memberRepository.delete(member);
    }

}
