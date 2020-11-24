package com.dsmpear.main.controller;

import com.dsmpear.main.payload.response.ApplicationListResponse;
import com.dsmpear.main.payload.response.ProfilePageResponse;
import com.dsmpear.main.service.profile.profile.ProfileService;
import com.dsmpear.main.service.profile.profileReport.ProfileReportListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileReportListService profileReportListService;

    @GetMapping("/{userEmail}")
    public ProfilePageResponse getProfile(@PathVariable String userEmail){
        return profileService.getProfile(userEmail);
    }

    @GetMapping("/report/{userEmail}")
    public ApplicationListResponse getReport(@PathVariable String userEmail, Pageable page){
        return profileReportListService.getReport(userEmail, page);
    }

}
