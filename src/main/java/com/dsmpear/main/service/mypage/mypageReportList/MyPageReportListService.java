package com.dsmpear.main.service.mypage.mypageReportList;

import com.dsmpear.main.payload.response.ApplicationListResponse;
import org.springframework.data.domain.Pageable;

public interface MyPageReportListService {
    ApplicationListResponse getReport(Pageable page);
}
