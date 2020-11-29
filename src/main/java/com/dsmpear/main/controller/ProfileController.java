package com.dsmpear.main.controller;

import com.dsmpear.main.payload.response.ApplicationListResponse;
import com.dsmpear.main.payload.response.ProfilePageResponse;
import com.dsmpear.main.service.profile.profile.ProfileService;
import com.dsmpear.main.service.profile.profileReport.ProfileReportListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileReportListService profileReportListService;

    @GetMapping
    public ProfilePageResponse getProfile(@RequestParam("user-email") String userEmail){
        return profileService.getProfile(userEmail);
    }

    @GetMapping("/report")
    public ApplicationListResponse getReport(@RequestParam("user-email") String userEmail, Pageable page){
        return profileReportListService.getReport(userEmail, page);
    }

}
