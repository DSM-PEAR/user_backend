package com.dsmpear.main.service.member;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
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
    private final ReportRepository reportRepository;

    @Override
    public void addMember(MemberRequest memberRequest) {
        String userEmail = authenticationFacade.getUserEmail();

        Report report = reportRepository.findByReportId(memberRequest.getReportId())
                .orElseThrow(ReportNotFoundException::new);

        //요청한 user가 팀 멤버인지 확인하기
        memberRepository.findByReportIdAndUserEmail(report.getReportId(), userEmail)
                .orElseThrow(UserNotMemberException::new);

        userRepository.findByEmail(memberRequest.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        //팀 아이디랑 유저 이메일로 찾기
        memberRepository.findByReportIdAndUserEmail(report.getReportId(), memberRequest.getUserEmail())
                .ifPresent(m -> {
                    throw new MemberAlreadyIncludeException();
                });

        memberRepository.save(
                Member.builder()
                    .reportId(report.getReportId())
                    .userEmail(memberRequest.getUserEmail())
                    .build()
        );
    }

    @Override
    public void deleteMember(Integer memberId) {
        String userEmail = authenticationFacade.getUserEmail();

        Member member=memberRepository.findById(memberId)
                .orElseThrow(UserNotMemberException::new);

        if(userEmail.equals(member.getUserEmail())){
            throw new UserEqualsMemberException();
        }

        memberRepository.delete(member);
    }

}
