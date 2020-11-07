package com.dsmpear.main.service.team;


import com.dsmpear.main.payload.request.TeamRequest;

public interface TeamService {
    //팀 멤버 가져오기
    void addTeam(TeamRequest teamRequest);
    void modifyTeam(Integer teamId,TeamRequest teamRequest);
    void deleteTeam(Integer teamId);
}
