package com.dsmpear.main.service.notice;

import com.dsmpear.main.payload.response.NoticeContentResponse;
import com.dsmpear.main.payload.response.NoticeListResponse;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    NoticeListResponse getNoticeList(Pageable page);
    NoticeContentResponse getNoticeContent(Integer noticeId);
}
