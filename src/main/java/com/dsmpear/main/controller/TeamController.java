package com.dsmpear.main.controller;


import com.dsmpear.main.payload.request.TeamRequest;
import com.dsmpear.main.payload.response.TeamResponse;
import com.dsmpear.main.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/{reportId}")
    public TeamResponse getTeam(@PathVariable Integer reportId){
        return teamService.getTeam(reportId);
    }

    @PostMapping
    public void addTeam(@RequestBody TeamRequest teamRequest){
        teamService.addTeam(teamRequest);
    }

    @PatchMapping("/{teamId}")
    public void modifyTeam(
            @PathVariable Integer teamId,
            @RequestParam String name){
        teamService.modifyName(teamId,name);
    }
}
