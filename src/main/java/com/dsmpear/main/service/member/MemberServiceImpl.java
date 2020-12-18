package com.dsmpear.main.service.member;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.entity.userreport.UserReport;
import com.dsmpear.main.entity.userreport.UserReportRepository;
import com.dsmpear.main.exceptions.*;
import com.dsmpear.main.payload.request.MemberRequest;
import com.dsmpear.main.payload.response.MemberListResponse;
import com.dsmpear.main.payload.response.MemberResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final AuthenticationFacade authenticationFacade;

    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;
    private final UserReportRepository userReportRepository;

    @Override
    public MemberListResponse getMember(Integer reportId, Pageable page) {
        Page<Member> memberPage = memberRepository.findAllByReportId(reportId, page);

        List<MemberResponse> memberResponses = new ArrayList<>();

        reportRepository.findByReportId(reportId)
                .orElseThrow(ReportNotFoundException::new);

        for(Member member:memberPage){
            memberResponses.add(
                    MemberResponse.builder()
                            .memberId(member.getId())
                            .memberEmail(member.getUserEmail())
                            .memberName(member.getUserEmail())
                            .build()
            );
        }

        return MemberListResponse.builder()
                .totalElements(memberPage.getNumberOfElements())
                .totalPages(memberPage.getTotalPages())
                .memberResponses(memberResponses)
                .build();
    }

    @Override
    public void addMember(MemberRequest memberRequest) {
        String email = authenticationFacade.getUserEmail();

        Report report = reportRepository.findByReportId(memberRequest.getReportId())
                .orElseThrow(ReportNotFoundException::new);

        User user = userRepository.findByEmail(memberRequest.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        //요청한 user가 팀 멤버인지 확인하기
        memberRepository.findByReportIdAndUserEmail(report.getReportId(), email)
                .orElseThrow(UserNotMemberException::new);

        //팀 아이디랑 유저 이메일로 찾기
        memberRepository.findByReportIdAndUserEmail(report.getReportId(), memberRequest.getUserEmail())
                .ifPresent(m -> {
                    throw new MemberAlreadyIncludeException();
                });

        memberRepository.save(
                Member.builder()
                        .reportId(report.getReportId())
                        .userEmail(memberRequest.getUserEmail())
                        .report(reportRepository.findByReportId(report.getReportId()).get())
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .reportId(report.getReportId())
                        .userEmail(user.getEmail())
                        .build()
        );
    }

    @Override
    public void deleteMember(Integer memberId) {
        String email = authenticationFacade.getUserEmail();

        Member member=memberRepository.findById(memberId)
                .orElseThrow(UserNotMemberException::new);

        Report report = reportRepository.findByReportId(member.getReportId())
                .orElseThrow(ReportNotFoundException::new);

        //요청한 user가 팀 멤버인지 확인하기
        memberRepository.findByReportIdAndUserEmail(report.getReportId(), email)
                .orElseThrow(UserNotMemberException::new);

        UserReport userReport = userReportRepository.findByReportIdAndUserEmail(report.getReportId(), member.getUserEmail())
                .orElseThrow(UserNotMemberException::new);

        if(email.equals(member.getUserEmail())){
            throw new UserEqualsMemberException();
        }

        memberRepository.delete(member);

        userReportRepository.delete(userReport);
    }

}