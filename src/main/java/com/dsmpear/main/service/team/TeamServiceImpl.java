package com.dsmpear.main.service.team;

import com.dsmpear.main.entity.team.Team;
import com.dsmpear.main.entity.team.TeamRepository;
import com.dsmpear.main.entity.user.User;
import com.dsmpear.main.entity.user.UserRepository;
import com.dsmpear.main.exceptions.TeamNotFoundException;
import com.dsmpear.main.exceptions.UserNotFoundException;
import com.dsmpear.main.payload.request.TeamRequest;
import com.dsmpear.main.security.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    @Override
    public void addTeam(TeamRequest teamRequest) {

        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        Team team=teamRepository.save(
          Team.builder()
                  .userEmail(user.getEmail())
                  .name(teamRequest.getName())
          .build()
        );

        /*memberRepository.save(
                Member.builder()
                        .teamId(team.getId())
                        .userEmail(user.getEmail())
                .build()
        );*/
    }

    @Override
    public void modifyTeam(Integer teamId, TeamRequest teamRequest) {

    }

    @Override
    public void deleteTeam(Integer teamId) {
        User user=userRepository.findByEmail(authenticationFacade.getUserEmail())
                .orElseThrow(UserNotFoundException::new);

        //접근자가 멤버인지 찾기
        // if(!user.getEmail().equals(memberRepository.findAllByTeamId(teamId)))

        Team team=teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        //memberRepository.deleteAllByTeamId(teamId);
        teamRepository.delete(team);
    }
}
