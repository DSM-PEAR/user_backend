package com.dsmpear.main.controller;

import com.dsmpear.main.payload.response.ReportListResponse;
import com.dsmpear.main.payload.response.SearchProfileResponse;
import com.dsmpear.main.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/search")
@RequiredArgsConstructor
@RestController
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/profile")
    public SearchProfileResponse searchProfile(@RequestParam("keyword") String keyword,
                                               Pageable page) {
        return searchService.searchProfile(keyword,page);
    }

    @GetMapping("/report")
    public ReportListResponse reportListResponse(@RequestParam("keyword") String keyword,
                                                 Pageable page) {
        return searchService.searchReportByTitle(page,keyword);
    }

}
