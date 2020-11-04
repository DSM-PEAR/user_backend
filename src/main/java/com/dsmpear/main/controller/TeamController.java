package com.dsmpear.main.controller;


import com.dsmpear.main.domain.team.dto.request.TeamRequest;
import com.dsmpear.main.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public void addTeam(@RequestBody TeamRequest teamRequest){
        teamService.addTeam(teamRequest);
    }

    @PutMapping("/{teamId}")
    public void modifyTeam(@PathVariable Integer teamId,
            @RequestParam TeamRequest teamRequest){
        teamService.modifyTeam(teamId, teamRequest);
    }

    @DeleteMapping("/{teamId}")
    public void deleteTeam(@PathVariable Integer teamId){
        teamService.deleteTeam(teamId);
    }

}
