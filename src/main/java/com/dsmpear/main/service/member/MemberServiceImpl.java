package com.dsmpear.main.service.member;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
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
    private final ReportRepository reportRepository;

    @Override
    public void addMember(MemberRequest memberRequest) {
        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Report report = reportRepository.findByReportId(memberRequest.getReportId())
                .orElseThrow(ReportNotFoundException::new);

        //요청한 user가 팀 멤버인지 확인하기
        memberRepository.findByReportIdAndUserEmail(report.getReportId(), user.getEmail())
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
<<<<<<< HEAD
<<<<<<< HEAD
=======
                        .report(reportRepository.findByReportId(report.getReportId()).get())
>>>>>>> d46ff744b60a23a25af807ee4833722e28fef005
=======
                        .report(reportRepository.findByReportId(report.getReportId()).get())
>>>>>>> b5c128b249e9bede1b97a1c34e0ddd6a474173d3
                        .build()
        );
    }

    @Override
    public void deleteMember(Integer memberId) {
        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);



        Member member=memberRepository.findById(memberId)
                .orElseThrow(UserNotMemberException::new);

        if(user.getEmail().equals(member.getUserEmail())){
            throw new UserEqualsMemberException();
        }

        memberRepository.delete(member);
    }

}
