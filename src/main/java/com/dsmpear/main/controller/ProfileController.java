package com.dsmpear.main.controller;

import com.dsmpear.main.payload.response.ProfilePageResponse;
import com.dsmpear.main.payload.response.ProfileReportListResponse;
import com.dsmpear.main.service.profile.profile.ProfileService;
import com.dsmpear.main.service.profile.profileReport.ProfileReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/profile")
@RestController
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileReportService profileReportService;

    @GetMapping
    public ProfilePageResponse getProfile(@RequestParam("user-email") String userEmail) {
        return profileService.getProfile(userEmail);
    }

    @GetMapping("/report")
    public ProfileReportListResponse getReport(@RequestParam("user-email") String userEmail, Pageable page) {
        return profileReportService.getReport(userEmail, page);
    }

}
