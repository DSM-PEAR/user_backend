package com.dsmpear.main.service.mypage.mypageReportList;

import com.dsmpear.main.payload.response.ProfileReportListResponse;
import org.springframework.data.domain.Pageable;

public interface MyPageReportListService {
    ProfileReportListResponse getReport(Pageable page);
}
