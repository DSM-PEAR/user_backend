package com.dsmpear.main.service.mypage.mypageReport;

import com.dsmpear.main.payload.response.ProfileReportListResponse;
import org.springframework.data.domain.Pageable;

public interface MyPageReportService {
    ProfileReportListResponse getReport(Pageable page);
}
