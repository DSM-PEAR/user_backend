package com.dsmpear.main.controller;

import com.dsmpear.main.payload.response.SearchListResponse;
import com.dsmpear.main.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public SearchListResponse searchProfile(@RequestParam("mode") String mode,
                                            @RequestParam("keyword") String keyword,
                                            Pageable page){
        return searchService.getSearchList(mode,keyword,page);
    }

}
