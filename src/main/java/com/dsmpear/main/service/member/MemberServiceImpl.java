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

import javax.transaction.Transactional;
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

    @Transactional
    public void addMember(MemberRequest memberRequest) {
        if(!authenticationFacade.isLogin()) {
            throw new PermissionDeniedException();
        }

        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Report report = reportRepository.findById(memberRequest.getReportId())
                .orElseThrow(ReportNotFoundException::new);

        User addUser = userRepository.findByEmail(memberRequest.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        //요청한 user가 팀 멤버인지 확인하기
        memberRepository.findByReportAndUserEmail(report, user.getEmail())
                .orElseThrow(UserNotMemberException::new);

        //팀 아이디랑 유저 이메일로 찾기
        memberRepository.findByReportAndUserEmail(report, memberRequest.getUserEmail())
                .ifPresent(m -> {
                    throw new MemberAlreadyIncludeException();
                });

        memberRepository.save(
                Member.builder()
                        .userEmail(memberRequest.getUserEmail())
                        .report(report)
                        .build()
        );

        userReportRepository.save(
                UserReport.builder()
                        .report(report)
                        .user(addUser)
                        .build()
        );
    }

    @Override
    public void deleteMember(Integer memberId) {
        if(!authenticationFacade.isLogin()) {
            throw new PermissionDeniedException();
        }
        User user = userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(UserNotMemberException::new);

        User findUser = userRepository.findByEmail(member.getUserEmail())
                .orElseThrow(UserNotAccessibleException::new);

        Report report = member.getReport();

        //요청한 user가 팀 멤버인지 확인하기
        memberRepository.findByReportAndUserEmail(report, user.getEmail())
                .orElseThrow(UserNotMemberException::new);

        UserReport userReport = userReportRepository.findByReportAndUser(report, findUser)
                .orElseThrow(UserNotMemberException::new);

        if(user.getEmail().equals(member.getUserEmail())){
            throw new UserEqualsMemberException();
        }

        memberRepository.delete(member);

        userReportRepository.delete(userReport);
    }

}