package com.dsmpear.main.service.notice;

import com.dsmpear.main.payload.response.ApplicationListResponse;
import com.dsmpear.main.payload.response.NoticeContentResponse;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
    ApplicationListResponse getNoticeList(Pageable page);
    NoticeContentResponse getNoticeContent(Integer noticeId);
}
