package com.dsmpear.main.service.team;


import com.dsmpear.main.payload.request.TeamRequest;
import com.dsmpear.main.payload.response.TeamResponse;

public interface TeamService {
    TeamResponse getTeam(Integer reportId);
    void addTeam(TeamRequest teamRequest);
    void modifyName(Integer reportId,String name);
}
