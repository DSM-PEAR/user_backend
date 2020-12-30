package com.dsmpear.main.controller;

import com.dsmpear.main.payload.response.NoticeContentResponse;
import com.dsmpear.main.payload.response.NoticeListResponse;
import com.dsmpear.main.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public NoticeListResponse getNoticeList(Pageable page){
        return noticeService.getNoticeList(page);
    }

    @GetMapping("/{noticeId}")
    public NoticeContentResponse getNoticeContent(@PathVariable Integer noticeId) {
        return noticeService.getNoticeContent(noticeId);
    }

}
