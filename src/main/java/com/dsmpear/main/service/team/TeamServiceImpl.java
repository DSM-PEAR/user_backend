package com.dsmpear.main.service.team;

import com.dsmpear.main.entity.member.Member;
import com.dsmpear.main.entity.member.MemberRepository;
import com.dsmpear.main.entity.report.Report;
import com.dsmpear.main.entity.report.ReportRepository;
import com.dsmpear.main.entity.team.Team;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.ReportNotFoundException;
import com.dsmpear.main.exceptions.TeamNotFoundException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.exceptions.UserNotMemberException;
import com.dsmpear.main.payload.request.TeamRequest;
import com.dsmpear.main.payload.response.MemberResponse;
import com.dsmpear.main.payload.response.TeamResponse;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;

    @Override
    public TeamResponse getTeam(Integer reportId) {
        userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);

        Team team = teamRepository.findAllByReportId(reportId)
                .orElseThrow(TeamNotFoundException::new);

        List<MemberResponse> memberResponses = new ArrayList<>();

        for(Member member : memberRepository.findAllByTeamId(team.getId())){
            User memberUser = userRepository.findByEmail(member.getUserEmail())
                    .orElseThrow(UserNotMemberException::new);

            memberResponses.add(
              MemberResponse.builder()
                      .memberEmail(memberUser.getEmail())
                      .memberName(memberUser.getName())
                      .build()
            );
        }

        return TeamResponse.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .memberList(memberResponses)
                .build();
    }

    @Override
    public void addTeam(TeamRequest teamRequest) {

        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Report report=reportRepository.findById(teamRequest.getReportId())
                .orElseThrow(ReportNotFoundException::new);

           Team team=teamRepository.save(
                   Team.builder()
                          .reportId(report.getReportId())
                          .userEmail(user.getEmail())
                          .name(Optional.ofNullable(teamRequest.getName()).orElseGet(user::getName))
                          .build()
            );

        memberRepository.save(
          Member.builder()
                  .teamId(team.getId())
                  .userEmail(user.getEmail())
                .build()
        );
    }

    @Override
    public void modifyName(Integer teamId,String name) {
        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        memberRepository.findByTeamIdAndUserEmail(teamId,user.getEmail())
                .orElseThrow(UserNotMemberException::new);

        Team team=teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        team.updateName(name);

        teamRepository.save(team);
    }
}
