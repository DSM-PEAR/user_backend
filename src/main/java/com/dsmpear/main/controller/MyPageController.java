package com.dsmpear.main.controller;

import com.dsmpear.main.payload.request.SetSelfIntroRequest;
import com.dsmpear.main.payload.response.ProfilePageResponse;
import com.dsmpear.main.payload.response.ProfileReportListResponse;
import com.dsmpear.main.service.mypage.mypage.MyPageService;
import com.dsmpear.main.service.mypage.mypageReport.MyPageReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user/profile")
@RestController
public class MyPageController {

    private final MyPageService myPageService;
    private final MyPageReportService myPageReportService;

    @GetMapping
    public ProfilePageResponse getMyPage(){
        return myPageService.getMyPage();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setSelfIntro(@RequestBody SetSelfIntroRequest request) {
        myPageService.setSelfIntro(request.getIntro(), request.getGithub());
    }

    @GetMapping("/report")
    public ProfileReportListResponse getReport(Pageable page){
        return myPageReportService.getReport(page);
    }

}
