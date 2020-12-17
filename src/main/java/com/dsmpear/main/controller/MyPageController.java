package com.dsmpear.main.controller;

import com.dsmpear.main.payload.response.ProfilePageResponse;
import com.dsmpear.main.payload.response.ProfileReportListResponse;
import com.dsmpear.main.service.mypage.mypage.MyPageService;
import com.dsmpear.main.service.mypage.mypageReport.MyPageReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class MyPageController {

    private final MyPageService myPageService;
    private final MyPageReportService myPageReportService;

    @GetMapping
    public ProfilePageResponse getMyPage(){
        return myPageService.getMyPage();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setSelfIntro(@RequestParam String intro, @RequestParam String gitHub){
        myPageService.setSelfIntro(intro, gitHub);
    }

    @GetMapping("/report")
    public ProfileReportListResponse getReport(Pageable page){
        return myPageReportService.getReport(page);
    }

}
